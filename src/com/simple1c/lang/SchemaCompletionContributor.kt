package com.simple1c.lang

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.simple1c.boilerplate.RecursiveVisitor
import coreUtils.equalsIgnoreCase
import coreUtils.toMap
import generated.*
import java.util.*

class SchemaCompletionContributor(val schemaStore: ISchemaStore) : CompletionContributor() {

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val tableDeclarationPattern = PlatformPatterns.psiElement()
                .inside(PlatformPatterns.psiElement(GeneratedTypes.TABLE_DECLARATION))
        if (tableDeclarationPattern.accepts(parameters.position)) {
            addStrings(schemaStore.getTables(), result)
            return
        }

        val path = getPath(parameters.originalPosition)
        val context = getDefinedTables(parameters.position)

        if (!path.tableSegments.isEmpty()) {
            applyPrefixMatch(evaluatePath(context, path.tableSegments), path.localPath, result)
        } else if (context.tables.any()) {
            val candidates = context.getAliases().plus(getColumnNames(context.tables))
            applyPrefixMatch(candidates, path.localPath, result)
        } else {
            val tables = schemaStore.getTables()
            val candidates = tables.plus(getColumnNames(tables))
            applyPrefixMatch(candidates, path.localPath, result)
        }
    }

    private fun getColumnNames(tables: Iterable<String>) = tables.flatMap { schemaStore.getSchema(it).map { it.name } }

    private fun applyPrefixMatch(columns: List<String>, prefix: String, result: CompletionResultSet) {
        addStrings(columns, result.withPrefixMatcher(prefix))
    }

    private fun getDefinedTables(position: PsiElement): TableContext {
        val definedTables = collectTableDeclarationsInScope(position)
        val aliases = definedTables
                .filter { it.alias?.text != null }
                .toMap({ it.alias?.text!! }, { it.tableName.text })
        return TableContext(definedTables.map({ it.tableName.text }).toSet(), aliases)
    }

    private fun evaluatePath(context: TableContext, tableSegments: List<String>): List<String> {
        fun evalChildPath(segments: List<String>, schema: List<PropertyInfo>): List<PropertyInfo> {
            val first = segments.firstOrNull()
            if (first == null)
                return schema
            val remaining = segments.subList(1, segments.size)
            return schema.filter { it.name.equalsIgnoreCase(first) }
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
        val rootSchema = schemaStore.getSchema(rootTable.second!!)
        val localPath = tableSegments.subList(rootTable.first.size, tableSegments.size)
        return evalChildPath(localPath, rootSchema).map { it.name }
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

    private fun collectTableDeclarationsInScope(element: PsiElement): List<TableDeclaration> {
        val _1cElement = PsiTreeUtil.getParentOfType(element, _1cElement::class.java)
        val rootQuery = PsiTreeUtil.getParentOfType(element, RootQuery::class.java)
        if (_1cElement == null || rootQuery == null)
            return emptyList()
        var result: List<TableDeclaration>? = null

        val visitor: RecursiveVisitor = object : RecursiveVisitor() {
            val contextStack = Stack<ArrayList<TableDeclaration>>()
            var elementFound = false
            override fun visitSqlQuery(o: SqlQuery) {
                contextStack.push(arrayListOf<TableDeclaration>())
                super.visitSqlQuery(o)
                if (elementFound && result == null)
                    result = contextStack.reversed().flatMap { it }
                else
                    contextStack.pop()

            }

            override fun visitTableDeclaration(o: TableDeclaration) {
                contextStack.peek().add(o)
                super.visitTableDeclaration(o)
            }

            override fun visit_1cElement(o: _1cElement) {
                if (elementFound)
                    return
                super.visit_1cElement(o)
                if (o == _1cElement)
                    elementFound = true
            }

        }
        visitor.visit_1cElement(rootQuery)
        return result ?: emptyList<TableDeclaration>()
    }

    private fun addStrings(strings: Iterable<String>, result: CompletionResultSet) {
        result.addAllElements(strings.map { LookupElementBuilder.create(it) })
    }

    private data class Path(val tableSegments: List<String>, val localPath: String)

    private data class TableContext(val tables: Set<String>, private val aliases: Map<String, String>) {
        fun resolve(name: String): String? {
            if (tables.contains(name))
                return name
            return aliases[name]
        }

        fun getAliases(): Iterable<String> {
            return aliases.keys
        }
    }

}

