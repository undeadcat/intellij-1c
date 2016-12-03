package com.simple1c.lang

import generated.GeneratedTypes
import generated.SelectionList


fun SelectionList.isSelectAll(): Boolean {
    return this.node.firstChildNode?.elementType == GeneratedTypes.ASTERISK
}
