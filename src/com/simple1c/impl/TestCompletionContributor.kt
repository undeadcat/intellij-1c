package com.simple1c.impl

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import generated.GeneratedTypes

class TestCompletionContributor : CompletionContributor() {
    private var tableSource = TableSource()
    private var columnSource = ColumnSource()

    init {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(GeneratedTypes.SELECTION_LIST)),
                object : CompletionProvider<CompletionParameters>() {
                    public override fun addCompletions(parameters: CompletionParameters,
                                                       context: ProcessingContext,
                                                       resultSet: CompletionResultSet) {
                        resultSet.addElement(LookupElementBuilder.create("huj1"))
                        resultSet.addElement(LookupElementBuilder.create("huj2"))
                        resultSet.addElement(LookupElementBuilder.create("huj3"))
                    }
                })
    }

    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val tableDeclarationPattern = PlatformPatterns.psiElement()
                .inside(PlatformPatterns.psiElement(GeneratedTypes.TABLE_DECLARATION))
        if (tableDeclarationPattern.accepts(parameters.position)) {
            addStrings(tableSource.getAll(), result)
            return
        }
        addStrings(tableSource.getAll().union(columnSource.getAll(null)), result)
    }

    private fun addStrings(strings: Iterable<String>, result: CompletionResultSet) {
        result.addAllElements(strings.map { LookupElementBuilder.create(it) })
    }

}
