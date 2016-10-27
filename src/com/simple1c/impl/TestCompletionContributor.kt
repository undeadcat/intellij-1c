package com.simple1c.impl

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import generated.GeneratedTypes
import generated.SqlQuery
import generated.TableDeclaration
import utils.toMap

class TestCompletionContributor(val schemaStore: ISchemaStore) : CompletionContributor() {

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val tableDeclarationPattern = PlatformPatterns.psiElement()
                .inside(PlatformPatterns.psiElement(GeneratedTypes.TABLE_DECLARATION))
        if (tableDeclarationPattern.accepts(parameters.position)) {
            addStrings(schemaStore.getTables(), result)
            return
        }

        val sqlQuery = PsiTreeUtil.getParentOfType(parameters.position, SqlQuery::class.java)

        val definedTables = PsiTreeUtil.findChildrenOfType(sqlQuery, TableDeclaration::class.java)
        val maybeAlias = getAliasOrNull(parameters.originalPosition)
        if (maybeAlias != null) {
            val definedAliases = definedTables
                    .filter { it.alias?.text != null }
                    .toMap({ it.alias?.text }, { it })
            val table = definedAliases[maybeAlias]
            if (table != null) {
                val localPrefix = parameters.originalPosition?.text?.removePrefix(maybeAlias) ?: ""
                val prefixedResults = result.withPrefixMatcher(localPrefix)
                addStrings(schemaStore.getColumns(table.tableName.text), prefixedResults)
            }
        } else if (definedTables.any()) {
            addStrings(definedTables.map { it.tableName.text }
                    .filter { it != null }
                    .flatMap { schemaStore.getColumns(it) }, result)
        } else
            addStrings(schemaStore.getColumns(null).union(schemaStore.getTables()), result)
    }

    private fun getAliasOrNull(originalText: PsiElement?): String? {
        if (originalText == null || originalText.text.isNullOrEmpty())
            return null
        val endOffset = originalText.textRange.endOffset
        //TODO. kill
        val theText = if (endOffset < originalText.containingFile.textLength)
            originalText.text + originalText.containingFile.text.substring(endOffset, endOffset + 1)
        else originalText.text
        val index = theText.indexOf('.')
        if (index < 0)
            return null
        return theText.substring(0, index)
    }

    private fun addStrings(strings: Iterable<String>, result: CompletionResultSet) {
        result.addAllElements(strings.map { LookupElementBuilder.create(it) })
    }

}

