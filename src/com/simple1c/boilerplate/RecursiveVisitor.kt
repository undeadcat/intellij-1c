package com.simple1c.boilerplate

import com.intellij.psi.PsiElement
import generated.Visitor

open class RecursiveVisitor : Visitor() {
    override fun visitElement(element: PsiElement) {
        super.visitElement(element)
        element.children.forEach { it.accept(this) }
    }
}