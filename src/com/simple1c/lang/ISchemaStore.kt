package com.simple1c.lang

import com.intellij.psi.PsiFile

interface ISchemaStore {
    fun getSchemaOrNull(file: PsiFile, tableName: String): TableSchema?
    fun getTables(file:PsiFile): List<String>
}

class PropertyInfo(val name: String, val referencedTables: List<String>)