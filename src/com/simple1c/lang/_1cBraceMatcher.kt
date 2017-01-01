package com.simple1c.lang

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import generated.GeneratedTypes

class _1cBraceMatcher : PairedBraceMatcher {
    private val bracePair = arrayOf(BracePair(GeneratedTypes.LPAREN, GeneratedTypes.RPAREN, false))
    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }

    override fun getPairs(): Array<out BracePair> {
        return bracePair
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

}