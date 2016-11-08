package com.simple1c.lang

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import com.intellij.psi.tree.TokenSet
import com.simple1c.boilerplate._1cLanguage
import generated.GeneratedTypes

class _1cFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(element: PsiElement, settings: CodeStyleSettings): FormattingModel {
        val block = createBlock(element, settings)
        return FormattingModelProvider.createFormattingModelForPsiFile(element.containingFile, block, settings)
    }

    override fun getRangeAffectingIndent(file: PsiFile?, offset: Int, elementAtOffset: ASTNode?): TextRange? {
        return null
    }

    class Simple1cBlock(node: ASTNode, wrap: Wrap?,
                        alignment: Alignment?,
                        val spacingBuilder: SpacingBuilder)
    : AbstractBlock(node, wrap, alignment) {

        override fun buildChildren(): List<Block> {
            return myNode.getChildren(null)
                    .filter { it.elementType != TokenType.WHITE_SPACE && it.textRange.length > 0 }
                    .map {
                        Simple1cBlock(it,
                                Wrap.createWrap(WrapType.NONE, false),
                                Alignment.createAlignment(),
                                spacingBuilder)
                    }
        }

        override fun getIndent(): Indent? {
            if (node.elementType == GeneratedTypes.SELECTION_EXPRESSION)
                return Indent.getNormalIndent()
            return Indent.getNoneIndent()
        }

        override fun getSpacing(child1: Block?, child2: Block): Spacing? {
            val myChild1 = child1 as? Simple1cBlock
            val myChild2 = child2 as? Simple1cBlock
            if (myChild2 != null && clauseStartTokens.contains(myChild2.node.elementType))
                return newline()
            if (myChild2 != null && myChild2.node.elementType == GeneratedTypes.SELECTION_EXPRESSION)
                return newline()
            if (myChild1 != null
                    && myChild1.node.elementType == GeneratedTypes.COMMA
                    && node.elementType == GeneratedTypes.SELECTION_LIST)
                return newline()
            val specificSpacing = spacingBuilder.getSpacing(this, child1, child2)
            if (specificSpacing != null)
                return specificSpacing
            return Spacing.createSpacing(1, 1, 0, true, 2)
        }

        private fun newline() = Spacing.createSpacing(0, 0, 1, true, 2)

        override fun isLeaf(): Boolean {
            return myNode.firstChildNode == null
        }
    }

    companion object {
        val clauseStartTokens = TokenSet.create(GeneratedTypes.FROMKEYWORD,
                GeneratedTypes.JOIN_ITEM,
                GeneratedTypes.WHEREKEYWORD,
                GeneratedTypes.GROUPKEYWORD,
                GeneratedTypes.ORDERKEYWORD,
                GeneratedTypes.HAVINGKEYWORD)

        fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
            return SpacingBuilder(settings, _1cLanguage.INSTANCE)
                    .around(GeneratedTypes.OP_EQ)
                    .spaceIf(settings.SPACE_AROUND_EQUALITY_OPERATORS)
                    .after(GeneratedTypes.COMMA)
                    .spaceIf(settings.SPACE_AFTER_COMMA)
                    .before(GeneratedTypes.COMMA)
                    .spaceIf(settings.SPACE_BEFORE_COMMA)
                    .after(GeneratedTypes.LPAREN)
                    .none()
                    .before(GeneratedTypes.RPAREN)
                    .none()
        }

        fun createBlock(element: PsiElement, settings: CodeStyleSettings): _1cFormattingModelBuilder.Simple1cBlock {
            return _1cFormattingModelBuilder.Simple1cBlock(element.node,
                    Wrap.createWrap(WrapType.NONE, false),
                    Alignment.createAlignment(),
                    createSpaceBuilder(settings))
        }
    }
}



