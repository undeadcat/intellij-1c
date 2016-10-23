package com.simple1c

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.simple1c.boilerplate._1cFile
import com.simple1c.boilerplate._1cLanguage
import generated.GeneratedParser
import generated.GeneratedTypes
import generated._QueryGrammarLexer

class _1cParserDefinition : ParserDefinition {

    override fun createLexer(project: Project): Lexer {
        return FlexAdapter(_QueryGrammarLexer())
    }

    override fun createParser(project: Project): PsiParser {
        return PsiParser { root, builder -> GeneratedParser().parse(root, builder) }
    }

    override fun getFileNodeType(): IFileElementType {
        return FileElementType
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WhiteSpaces
    }

    override fun getCommentTokens(): TokenSet {
        return Comments
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createElement(node: ASTNode): PsiElement {
        return GeneratedTypes.Factory.createElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return _1cFile(viewProvider)
    }

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MUST
    }

    companion object {
        private val WhiteSpaces = TokenSet.create(com.intellij.psi.TokenType.WHITE_SPACE)
        private val Comments = TokenSet.create(GeneratedTypes.LINE_COMMENT)
        private val FileElementType = IFileElementType(_1cLanguage.INSTANCE)
    }
}
