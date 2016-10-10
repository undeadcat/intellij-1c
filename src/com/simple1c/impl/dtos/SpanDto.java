package com.simple1c.impl.dtos;

public class SpanDto {
    public int Start;
    public int Length;

    public int getEnd() {
        return Start + Length;
    }

    @Override
    public String toString() {
        return "SpanDto{" +
                "Start=" + Start +
                ", Length=" + Length +
                '}';
    }
}
