package com.simple1c.impl.dtos;

import java.util.List;

public class ParseTreeNodeDto {
    public String AstNodeType;
    public List<ParseTreeNodeDto> Children;
    public TokenDto Token;
    public String DebugName;

    @Override
    public String toString() {
        return "ParseTreeNodeDto{" +
                "AstNodeType='" + AstNodeType + '\'' +
                ", Children=" + Children +
                ", Token=" + Token +
                ", DebugName='" + DebugName + '\'' +
                '}';
    }
}
