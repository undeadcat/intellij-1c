package com.simple1c.lang

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import coreUtils.equalsIgnoreCase
import generated.*

class SchemaCompletionContributor(private val schemaStore: ISchemaStore) : CompletionContributor() {
    private val limit = 100

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {

        val tableDeclarationPattern = PlatformPatterns.psiElement()
                .inside(PlatformPatterns.psiElement(GeneratedTypes.TABLE_DECLARATION))
        val file = parameters.originalFile
        if (tableDeclarationPattern.accepts(parameters.position)) {
            addStrings(schemaStore.getTables(file), result)
            return
        }

        val pathSegments = getPathSegments(result.prefixMatcher.prefix)
        val localPath = pathSegments.lastOrNull().orEmpty()
        val sqlQuery = PsiTreeUtil.getParentOfType(parameters.position, SqlQuery::class.java)
        if (sqlQuery == null)
            return
        val context = QueryContext.createForQuery(sqlQuery, { schemaStore.getSchemaOrNull(file, it) })

        if (pathSegments.size > 1) {
            addElements(evaluatePath(file, schemaStore, context, pathSegments).asSequence(), localPath, result)
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
            val qualifiedSuggestions = partitioned.first.flatMap { source ->
                source.schema.properties.map { getQualifier(source) + "." + it.name }
            }
            val unqualifiedSuggestions = partitioned.second.flatMap { it.schema.properties }.map { it.name }
            addElements(qualifiedSuggestions
                    .plus(unqualifiedSuggestions)
                    .plus(partitioned.first.map(::getQualifier).filterNotNull())
                    .asSequence(), localPath, result)

        } else {
            val tables = schemaStore.getTables(file)
            val candidates = getColumnNames(file, schemaStore, schemaStore.getTables(file)).plus(tables)
            addElements(candidates, localPath, result)
        }
    }

    private fun getColumnNames(file: PsiFile, schemaStore: ISchemaStore, tables: Iterable<String>): Sequence<String> {
        return tables.asSequence().flatMap { schemaStore.getSchemaOrNull(file, it)?.properties?.map { it.name }?.asSequence() ?: emptySequence() }
    }

    private fun addElements(columns: Sequence<String>, prefix: String, result: CompletionResultSet) {
        addStrings(columns.take(limit).toList(), result.withPrefixMatcher(prefix))
    }

    private fun evaluatePath(file: PsiFile, schemaStore: ISchemaStore, context: QueryContext, path: List<String>): List<String> {
        fun evalChildPath(segments: List<String>, schema: TableSchema): List<PropertyInfo> {
            val first = segments.firstOrNull().orEmpty()
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

    private fun addStrings(strings: Iterable<String>, result: CompletionResultSet) {
        result.addAllElements(strings.map { LookupElementBuilder.create(it) })
    }
}

