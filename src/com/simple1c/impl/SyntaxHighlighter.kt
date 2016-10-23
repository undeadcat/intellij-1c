package com.simple1c.impl

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import generated.GeneratedTypes
import generated._QueryGrammarLexer

internal class SyntaxHighlighter : com.intellij.openapi.fileTypes.SyntaxHighlighter {

    private val Error = arrayOf(HighlighterColors.BAD_CHARACTER)
    private val Comment = arrayOf(DefaultLanguageHighlighterColors.LINE_COMMENT)

    private val Keyword = arrayOf(DefaultLanguageHighlighterColors.KEYWORD)

    private val Identifier = arrayOf(DefaultLanguageHighlighterColors.IDENTIFIER)

    private val Default = emptyArray<TextAttributesKey>()
    override fun getHighlightingLexer(): Lexer {
        return FlexAdapter(_QueryGrammarLexer())
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        if (tokenType == TokenType.BAD_CHARACTER)
            return Error
        if (tokenType == GeneratedTypes.LINE_COMMENT)
            return Comment
        if (tokenType == GeneratedTypes.IDENTIFIER)
            return Identifier
        return Default
    }

    class Factory : com.intellij.openapi.fileTypes.SyntaxHighlighterFactory() {

        override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): com.intellij.openapi.fileTypes.SyntaxHighlighter {
            return SyntaxHighlighter()
        }
    }

}

