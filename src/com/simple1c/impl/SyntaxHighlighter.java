package com.simple1c.impl;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.simple1c._1cTokenType;
import com.simple1c.impl._1cLexer;
import org.jetbrains.annotations.NotNull;

class SyntaxHighlighter implements com.intellij.openapi.fileTypes.SyntaxHighlighter {

    private static final TextAttributesKey[] Error =
            new TextAttributesKey[]{HighlighterColors.BAD_CHARACTER};
    private static final TextAttributesKey[] Comment =
            new TextAttributesKey[]{DefaultLanguageHighlighterColors.LINE_COMMENT};

    private static final TextAttributesKey[] Keyword =
            new TextAttributesKey[]{DefaultLanguageHighlighterColors.KEYWORD};

    private static final TextAttributesKey[] Identifier =
            new TextAttributesKey[]{DefaultLanguageHighlighterColors.IDENTIFIER};

    private static final TextAttributesKey[] Default = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new _1cLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(TokenType.BAD_CHARACTER))
            return Error;
        if (tokenType.equals(_1cTokenType.Comment))
            return Comment;
        if (tokenType.equals(_1cTokenType.Keyword))
            return Keyword;
        if (tokenType.equals(_1cTokenType.Identifier))
            return Identifier;
        return Default;
    }
}

