package com.simple1c.lang

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import generated.GeneratedTypes
import generated._QueryGrammarLexer

internal class SyntaxHighlighter : com.intellij.openapi.fileTypes.SyntaxHighlighter {

    private val Error = arrayOf(HighlighterColors.BAD_CHARACTER)
    private val Comment = arrayOf(DefaultLanguageHighlighterColors.LINE_COMMENT)

    private val KeywordHighlight = arrayOf(DefaultLanguageHighlighterColors.KEYWORD)
    private val KeywordsSet = TokenSet.create(GeneratedTypes.ALLKEYWORD,
            GeneratedTypes.ASCKEYWORD,
            GeneratedTypes.ASKEYWORD,
            GeneratedTypes.BYKEYWORD,
            GeneratedTypes.DESCKEYWORD,
            GeneratedTypes.DISTINCTKEYWORD,
            GeneratedTypes.FROMKEYWORD,
            GeneratedTypes.FULLKEYWORD,
            GeneratedTypes.GROUPKEYWORD,
            GeneratedTypes.HAVINGKEYWORD,
            GeneratedTypes.INNERKEYWORD,
            GeneratedTypes.JOINKEYWORD,
            GeneratedTypes.LEFTKEYWORD,
            GeneratedTypes.ONKEYWORD,
            GeneratedTypes.ORDERKEYWORD,
            GeneratedTypes.OUTERKEYWORD,
            GeneratedTypes.RIGHTKEYWORD,
            GeneratedTypes.SELECTKEYWORD,
            GeneratedTypes.TOPKEYWORD,
            GeneratedTypes.UNIONKEYWORD,
            GeneratedTypes.WHEREKEYWORD,
            GeneratedTypes.ПОKEYWORD)

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
        if (KeywordsSet.contains(tokenType))
            return KeywordHighlight
        return Default
    }
}

