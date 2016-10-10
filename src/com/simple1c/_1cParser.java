package com.simple1c;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.simple1c.impl.Transport;
import com.simple1c.impl.dtos.ParseTreeNodeDto;
import org.jetbrains.annotations.NotNull;

public class _1cParser implements PsiParser {

    private Transport transport = new Transport();

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        ParseTreeNodeDto parsedTree = transport.invoke("parser", builder.getOriginalText().toString(), ParseTreeNodeDto.class);
        PsiBuilder.Marker rootMarker = builder.mark();
        Visit(parsedTree, builder);
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    private void Visit(ParseTreeNodeDto node, PsiBuilder builder) {
        if (node.Children != null)
            for (ParseTreeNodeDto child : node.Children) {
                Visit(child, builder);
            }
        if (node.Token != null) {
            while (true) {
                IElementType tokenType = builder.getTokenType();
                if (tokenType == null)
                    break;

                PsiBuilder.Marker mark = builder.mark();
                CharSequence tokenStr = builder.getTokenText();
                builder.advanceLexer();
                mark.done(tokenType);
                if (tokenStr == node.Token.Value)
                    break;
            }
        }

    }
}
