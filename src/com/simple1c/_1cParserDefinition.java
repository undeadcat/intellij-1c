package com.simple1c;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.simple1c.boilerplate._1cFile;
import com.simple1c.boilerplate._1cLanguage;
import com.simple1c.impl._1cLexer;
import com.simple1c.impl.ast._1cExpression;
import org.jetbrains.annotations.NotNull;

public class _1cParserDefinition implements ParserDefinition {
    private static final TokenSet WhiteSpaces = TokenSet.create(com.intellij.psi.TokenType.WHITE_SPACE);
    private static final TokenSet Comments = TokenSet.create(_1cTokenType.Comment);
    public static final IFileElementType FileElementType =
            new IFileElementType(Language.findInstance(_1cLanguage.class));

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new _1cLexer();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new _1cParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FileElementType;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return WhiteSpaces;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return Comments;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return new _1cExpression(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new _1cFile(viewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MUST;
    }
}
