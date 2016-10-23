package com.simple1c.impl

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import generated.GeneratedTypes

class TestCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withAncestor(10, PlatformPatterns.psiElement(GeneratedTypes.SELECTION_LIST)),
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

}
