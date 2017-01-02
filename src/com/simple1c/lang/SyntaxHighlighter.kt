package com.simple1c.lang

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import generated.GeneratedTypes
import generated._QueryGrammarLexer

internal class SyntaxHighlighter : com.intellij.openapi.fileTypes.SyntaxHighlighter {
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
    private val wordOperatorsTokenSet = TokenSet.create(GeneratedTypes.OP_AND, GeneratedTypes.OP_IN, GeneratedTypes.OP_IS,
            GeneratedTypes.OP_LIKE, GeneratedTypes.OP_NOT, GeneratedTypes.OP_OR)

    private val errorAttributes = arrayOf(HighlighterColors.BAD_CHARACTER)
    private val commentAttributes = arrayOf(DefaultLanguageHighlighterColors.LINE_COMMENT)
    private val keywordAttributes = arrayOf(DefaultLanguageHighlighterColors.KEYWORD)
    private val identifierAttributes = arrayOf(DefaultLanguageHighlighterColors.IDENTIFIER)
    private val stringAttributes = arrayOf(DefaultLanguageHighlighterColors.STRING)
    private val numberAttributes = arrayOf(DefaultLanguageHighlighterColors.NUMBER)
    private val defaultAttributes = emptyArray<TextAttributesKey>()

    override fun getHighlightingLexer(): Lexer {
        println(true)
        return FlexAdapter(_QueryGrammarLexer())
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        if (tokenType == TokenType.BAD_CHARACTER)
            return errorAttributes
        if (tokenType == GeneratedTypes.LINE_COMMENT)
            return commentAttributes
        if (tokenType == GeneratedTypes.ID_TOKEN)
            return identifierAttributes
        if (tokenType == GeneratedTypes.STRING)
            return stringAttributes
        if (tokenType == GeneratedTypes.NUMBER)
            return numberAttributes
        if (KeywordsSet.contains(tokenType)
                || tokenType == GeneratedTypes.BOOL
                || wordOperatorsTokenSet.contains(tokenType))
            return keywordAttributes
        return defaultAttributes
    }
}

