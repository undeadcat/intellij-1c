package com.simple1c.lang

import com.intellij.codeInsight.AutoPopupController
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.lookup.LookupElementWeigher
import com.intellij.openapi.util.Key
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.simple1c.remote.RemoteException
import coreUtils.equalsIgnoreCase
import generated.*

//TODO. don't complete where column is unexpected.
class SchemaCompletionContributor(private val schemaStore: ISchemaStore,
                                  completionService: CompletionService) : CompletionContributor() {
    private val relevanceSorter = completionService.emptySorter().weigh(object : LookupElementWeigher("priority") {
        override fun weigh(element: LookupElement): Comparable<Int> {
            return -(element.getUserData(priorityKey) ?: 0)
        }
    })

    private val tableInsertHandler: InsertHandler<LookupElement> =
            InsertHandler { insertionContext, t ->
                insertionContext.document.insertString(insertionContext.selectionEndOffset, ".")
                insertionContext.editor.caretModel.moveCaretRelatively(1, 0, false, false, false)
                AutoPopupController.getInstance(insertionContext.project).autoPopupMemberLookup(insertionContext.editor, null)
            }

    private val priorityKey = Key<Int>("1c.completion.priority")

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        try {
            doCompletion(parameters, result)
        } catch (e: RemoteException) {
        }
    }

    private fun doCompletion(parameters: CompletionParameters, result: CompletionResultSet) {
        val tableDeclarationPattern = PlatformPatterns.psiElement()
                .inside(PlatformPatterns.psiElement(GeneratedTypes.TABLE_DECLARATION))
        val file = parameters.originalFile
        if (tableDeclarationPattern.accepts(parameters.position)) {
            result.addAllElements(schemaStore.getTables(file).map { toLookupElement(it) })
            return
        }

        val pathSegments = getPathSegments(result.prefixMatcher.prefix)
        val localPath = pathSegments.lastOrNull().orEmpty()
        val sortedResultSet = result.withRelevanceSorter(relevanceSorter).withPrefixMatcher(localPath)
        val sqlQuery = PsiTreeUtil.getParentOfType(parameters.position, SqlQuery::class.java)
        if (sqlQuery == null)
            return
        val context = QueryContext.createForQuery(sqlQuery, { schemaStore.getSchemaOrNull(file, it) })

        if (pathSegments.size > 1) {
            sortedResultSet.addAllElements(evaluatePath(file, schemaStore, context, pathSegments).map { toLookupElement(it) })
            return
        }
        if (context.getSources().any()) {
            fun getQualifier(querySource: QuerySource): String? {
                return when (querySource) {
                    is QuerySource.Table -> querySource.alias
                    is QuerySource.Subquery -> querySource.schema.name
                }
            }

            val partitioned = context.getSources().partition { getQualifier(it) != null }
            val aliasedSuggestions = partitioned.first.flatMap { source ->
                source.schema.properties.map { getQualifier(source) + "." + it.name }
            }
            val withoutAliases = partitioned.second
            val unqualifiedSuggestions = withoutAliases.flatMap { it.schema.properties }
            val qNameSuggestions = withoutAliases
                    .flatMap { table -> table.schema.properties.map { prop -> table.schema.name + "." + prop.name } }
            val aliases = context.getSources().map(::getQualifier)
            val tableNames = context.getSources().map {
                when (it) {
                    is QuerySource.Table -> it.schema.name
                    else -> null
                }
            }
            sortedResultSet.addAllElements(aliasedSuggestions.map { toLookupElement(it, Priority.ColumnWithAlias) })
            sortedResultSet.addAllElements(unqualifiedSuggestions.map { toLookupElement(it.name, Priority.Column) })
            sortedResultSet.addAllElements(qNameSuggestions.map { toLookupElement(it, Priority.ColumnWithQName) })
            sortedResultSet.addAllElements(aliases.plus(tableNames).filterNotNull()
                    .map { withPriority(LookupElementBuilder.create(it).withInsertHandler(tableInsertHandler), Priority.Table) })

        } else {
            val tables = schemaStore.getTables(file)
            val candidates = tables.asSequence()
                    .flatMap { (schemaStore.getSchemaOrNull(file, it)?.properties ?: emptyList()).asSequence() }
                    .map { it.name }
                    //TODO. this is useless because it doesn't show all columns starting with 'a' for select a<caret>.
                    //TODO. need to build an index.
                    .take(100)
                    .plus(tables)
            sortedResultSet.addAllElements(candidates.map { toLookupElement(it) }.asIterable())
        }
    }

    private fun evaluatePath(file: PsiFile, schemaStore: ISchemaStore, context: QueryContext, path: List<String>): List<String> {
        fun evalChildPath(segments: List<String>, schema: TableSchema): List<PropertyInfo> {
            if (segments.isEmpty())
                return emptyList()
            val first = segments[0]
            val remainingPath = segments.subList(1, segments.size)
            if (remainingPath.isEmpty())
                return schema.properties.filter { it.name.startsWith(first, true) }
            return schema.properties.filter { it.name.equalsIgnoreCase(first) }
                    .flatMap { it.referencedTables }
                    .flatMap { childTable ->
                        val childSchema = schemaStore.getSchemaOrNull(file, childTable)
                        if (childSchema != null)
                            evalChildPath(remainingPath, childSchema)
                        else emptyList()
                    }
        }

        if (path.isEmpty())
            throw RuntimeException("Expected non-empty path")

        val candidateNames = path.withIndex().map { path.take(it.index + 1) }
        val rootTable = candidateNames
                .map { Pair(it, context.resolve(it.joinToString("."))) }
                .filter { it.second != null }
                .firstOrNull()
        if (rootTable != null) {
            val rootSchema = rootTable.second!!
            val tablePath = path.subList(rootTable.first.size, path.size)
            return evalChildPath(tablePath, rootSchema).map { it.name }
        }
        return context.getSources()
                .flatMap { evalChildPath(path, it.schema) }
                .map { it.name }

    }

    private fun getPathSegments(prefix: String): List<String> {
        return prefix.split('.').map(String::trim)
    }

    private fun toLookupElement(name: String, priority: Priority? = null): LookupElementBuilder {
        val builder = LookupElementBuilder.create(name)
        if (priority != null)
            return withPriority(builder, priority)
        return builder
    }

    private fun withPriority(builder: LookupElementBuilder, priority: Priority): LookupElementBuilder {
        builder.putUserData(priorityKey, priority.value)
        return builder
    }

    private enum class Priority(val value: Int) {
        ColumnWithAlias(10),
        Column(9),
        ColumnWithQName(8),
        Table(7)
    }
}

