package com.simple1c.impl;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import com.simple1c._1cTokenType;
import com.simple1c.impl.dtos.SpanDto;
import com.simple1c.impl.dtos.TokenDto;
import com.simple1c.impl.dtos.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class _1cLexer extends LexerBase {
    private String input;
    private int endOffset;
    private int tokenIndex;
    private ArrayList<TokenDto> tokens;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        String newInput = buffer.toString();
        if (!Objects.equals(newInput, input)) {
            Transport transport = new Transport();
            this.tokens = insertWhitespace(newInput, transport.invoke("lexer", newInput, TokenDto[].class));
            this.tokenIndex = getFirstTokenIndex(startOffset, endOffset);
        }

        this.input = newInput;
        this.endOffset = Math.min(input.length(), endOffset);
    }


    @Override
    public int getState() {
        return 0;
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        if (tokenIndex == tokens.size())
            return null;
        return _1cTokenType.FromDto(tokens.get(tokenIndex).Type);
    }

    @Override
    public int getTokenStart() {
        return tokens.get(tokenIndex).Span.Start;
    }

    @Override
    public int getTokenEnd() {
        TokenDto token = tokens.get(tokenIndex);
        return token.Span.Start + token.Span.Length;
    }

    @Override
    public void advance() {
        this.tokenIndex++;
    }

    @NotNull
    @Override
    public CharSequence getBufferSequence() {
        return input;
    }

    @Override
    public int getBufferEnd() {
        return this.endOffset;
    }

    private int getFirstTokenIndex(int startOffset, int endOffset) {
        for (int i = 0; i < this.tokens.size(); i++) {
            TokenDto token = this.tokens.get(i);
            if (token.Span.Start >= startOffset
                    && token.Span.Start + token.Value.length() < endOffset) {
                return i;
            }
        }
        return 0;
    }

    private ArrayList<TokenDto> insertWhitespace(String input, TokenDto[] tokens) {
        ArrayList<TokenDto> results = new ArrayList<>();
        TokenDto previous = null;
        for (TokenDto token : tokens) {
            if (previous != null && token.Span.Start != previous.Span.getEnd())
                results.add(createWhitespaceToken(input, previous.Span.getEnd(), token.Span.Start));
            results.add(token);
            previous = token;
        }
        if (previous != null && previous.Span.getEnd() != input.length())
            results.add(createWhitespaceToken(input, previous.Span.getEnd(), input.length()));
        return results;
    }

    @NotNull
    private TokenDto createWhitespaceToken(String input, int start, int end) {
        String substring = end >= input.length() ? input.substring(start) : input.substring(start, end);
        TokenDto result = new TokenDto();
        result.Span = new SpanDto();
        result.Span.Start = start;
        result.Span.Length = end - start;
        result.Value = substring;
        result.Type = TokenType.Whitespace;
        return result;
    }


}
