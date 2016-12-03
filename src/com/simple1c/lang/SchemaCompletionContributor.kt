package com.simple1c.lang

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import coreUtils.equalsIgnoreCase
import generated.*

class SchemaCompletionContributor(val schemaStore: ISchemaStore) : CompletionContributor() {

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val tableDeclarationPattern = PlatformPatterns.psiElement()
                .inside(PlatformPatterns.psiElement(GeneratedTypes.TABLE_DECLARATION))
        if (tableDeclarationPattern.accepts(parameters.position)) {
            addStrings(schemaStore.getTables(), result)
            return
        }

        val path = getPath(parameters.originalPosition)
        val context = QueryContext.forElement(parameters.position, { schemaStore.getSchema(it) })

        if (!path.tableSegments.isEmpty()) {
            applyPrefixMatch(evaluatePath(context, path.tableSegments), path.localPath, result)
        } else if (context.getUsedTables().any() || context.getIntroducedNames().any()) {
            val candidates = context.getIntroducedNames().plus(getColumnNames(context.getUsedTables()))
            applyPrefixMatch(candidates, path.localPath, result)
        } else {
            val tables = schemaStore.getTables()
            val candidates = tables.plus(getColumnNames(tables))
            applyPrefixMatch(candidates, path.localPath, result)
        }
    }

    private fun getColumnNames(tables: Iterable<String>): List<String> {
        return tables.flatMap { schemaStore.getSchema(it).properties.map { it.name } }
    }

    private fun applyPrefixMatch(columns: List<String>, prefix: String, result: CompletionResultSet) {
        addStrings(columns, result.withPrefixMatcher(prefix))
    }

    private fun evaluatePath(context: QueryContext, tableSegments: List<String>): List<String> {
        fun evalChildPath(segments: List<String>, schema: TableSchema): List<PropertyInfo> {
            val first = segments.firstOrNull()
            if (first == null)
                return schema.properties
            val remaining = segments.subList(1, segments.size)
            return schema.properties.filter { it.name.equalsIgnoreCase(first) }
                    .flatMap {
                        it.referencedTables.flatMap { evalChildPath(remaining, schemaStore.getSchema(it)) }
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

    private fun getPath(originalText: PsiElement?): Path {
        if (originalText == null || originalText.text.isNullOrEmpty())
            return Path(emptyList(), "")
        val endOffset = originalText.textRange.endOffset
        val maybeDot = if (endOffset < originalText.containingFile.textLength)
            originalText.containingFile.text.substring(endOffset, endOffset + 1)
        else ""
        val withDot = if (maybeDot == "." && !originalText.text.endsWith("."))
            originalText.text + maybeDot
        else originalText.text
        val lastDot = withDot.lastIndexOf('.')
        if (lastDot < 0)
            return Path(emptyList(), withDot)
        val tableSegments = withDot.substring(0, lastDot)
        val localPath = if (lastDot < withDot.length - 1) withDot.substring(lastDot + 1) else ""
        return Path(tableSegments.split('.'), localPath)
    }

    private fun addStrings(strings: Iterable<String>, result: CompletionResultSet) {
        result.addAllElements(strings.map { LookupElementBuilder.create(it) })
    }

    private data class Path(val tableSegments: List<String>, val localPath: String)
}

