package com.simple1c.impl.dtos;

import com.google.gson.annotations.SerializedName;

public enum TokenType {
    @SerializedName("0")
    Comment,
    @SerializedName("1")
    Identifier,
    @SerializedName("2")
    Keyword,
    @SerializedName("3")
    Text,
    @SerializedName("4")
    Error,
    Whitespace
}
