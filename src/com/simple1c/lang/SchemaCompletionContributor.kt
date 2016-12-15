package com.simple1c.lang

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import coreUtils.equalsIgnoreCase
import generated.*

class SchemaCompletionContributor(private val schemaStore: ISchemaStore) : CompletionContributor() {

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {

        val tableDeclarationPattern = PlatformPatterns.psiElement()
                .inside(PlatformPatterns.psiElement(GeneratedTypes.TABLE_DECLARATION))
        val file = parameters.originalFile
        if (tableDeclarationPattern.accepts(parameters.position)) {
            addStrings(schemaStore.getTables(file), result)
            return
        }

        val path = getPath(result.prefixMatcher.prefix)
        val sqlQuery = PsiTreeUtil.getParentOfType(parameters.position, SqlQuery::class.java)
        if (sqlQuery == null)
            return
        val context = QueryContext.createForQuery(sqlQuery, { schemaStore.getSchemaOrNull(file, it) })

        if (!path.tableSegments.isEmpty()) {
            applyPrefixMatch(evaluatePath(file, schemaStore, context, path.tableSegments), path.localPath, result)
        } else if (context.getUsedTables().any() || context.getIntroducedNames().any()) {
            val candidates = context.getIntroducedNames().plus(getColumnNames(file, schemaStore, context.getUsedTables()))
            applyPrefixMatch(candidates, path.localPath, result)
        } else {
            val tables = schemaStore.getTables(file)
            val candidates = tables.plus(getColumnNames(file, schemaStore, tables))
            applyPrefixMatch(candidates, path.localPath, result)
        }
    }

    private fun getColumnNames(file: PsiFile, schemaStore: ISchemaStore, tables: Iterable<String>): List<String> {
        return tables.flatMap { schemaStore.getSchemaOrNull(file, it)?.properties?.map { it.name } ?: emptyList() }
    }

    private fun applyPrefixMatch(columns: List<String>, prefix: String, result: CompletionResultSet) {
        addStrings(columns, result.withPrefixMatcher(prefix))
    }

    private fun evaluatePath(file: PsiFile, schemaStore: ISchemaStore, context: QueryContext, tableSegments: List<String>): List<String> {
        fun evalChildPath(segments: List<String>, schema: TableSchema): List<PropertyInfo> {
            val first = segments.firstOrNull()
            if (first == null)
                return schema.properties
            val remaining = segments.subList(1, segments.size)
            return schema.properties.filter { it.name.equalsIgnoreCase(first) }
                    .flatMap {
                        it.referencedTables.flatMap {
                            val childSchema = schemaStore.getSchemaOrNull(file, it)
                            if (childSchema != null)
                                evalChildPath(remaining, childSchema)
                            else emptyList()
                        }
                    }
        }

        val rootTable = tableSegments.withIndex()
                .map { tableSegments.take(it.index + 1) }
                .map { Pair(it, context.resolve(it.joinToString("."))) }
                .filter { it.second != null }
                .firstOrNull()
        if (rootTable == null)
            return emptyList()
        val localPath = tableSegments.subList(rootTable.first.size, tableSegments.size)
        return evalChildPath(localPath, rootTable.second!!).map { it.name }
    }

    private fun getPath(prefix: String): Path {
        val lastDot = prefix.lastIndexOf('.')
        if (lastDot < 0)
            return Path(emptyList(), prefix)
        val tableSegments = prefix.substring(0, lastDot)
        val localPath = if (lastDot < prefix.length - 1) prefix.substring(lastDot + 1) else ""
        return Path(tableSegments.split('.'), localPath)
    }

    private fun addStrings(strings: Iterable<String>, result: CompletionResultSet) {
        result.addAllElements(strings.map { LookupElementBuilder.create(it) })
    }

    private data class Path(val tableSegments: List<String>, val localPath: String)
}

