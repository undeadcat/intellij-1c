package com.simple1c;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.simple1c.boilerplate._1cLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class _1cTokenType extends IElementType {
    public static IElementType Keyword = new _1cTokenType("keyword");
    public static IElementType Comment = new _1cTokenType("comment");
    public static IElementType Identifier = new _1cTokenType("identifier");
    public static IElementType Text = new _1cTokenType("text");

    public _1cTokenType(@NotNull @NonNls String debugName) {
        super(debugName, _1cLanguage.INSTANCE);
    }

    public static IElementType FromDto(com.simple1c.impl.dtos.TokenType tokenType) {
        switch (tokenType) {

            case Comment:
                return _1cTokenType.Comment;
            case Identifier:
                return _1cTokenType.Identifier;
            case Keyword:
                return _1cTokenType.Keyword;
            case Text:
                return _1cTokenType.Text;
            case Error:
                return TokenType.BAD_CHARACTER;
            case Whitespace:
                return TokenType.WHITE_SPACE;
            default:
                throw new RuntimeException("Unhandled token type " + tokenType);
        }
    }

    @Override
    public String toString() {
        return "SimpleTokenType." + super.toString();
    }
}
