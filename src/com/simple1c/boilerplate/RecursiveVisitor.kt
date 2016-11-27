package com.simple1c.boilerplate

import generated.Visitor
import generated._1cElement

open class RecursiveVisitor : Visitor() {
    override fun visit_1cElement(o: _1cElement) {
        super.visit_1cElement(o)
        o.children.forEach { it.accept(this) }
    }
}