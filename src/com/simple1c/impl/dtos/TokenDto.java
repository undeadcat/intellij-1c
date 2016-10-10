package com.simple1c.impl.dtos;

public class TokenDto {
    public String Value;
    public TokenType Type;
    public SpanDto Span;

    @Override
    public String toString() {
        return "TokenDto{" +
                "Value='" + Value + '\'' +
                ", Type=" + Type +
                ", Span=" + Span +
                '}';
    }
}

