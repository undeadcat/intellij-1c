package com.simple1c.lang

import com.google.gson.annotations.SerializedName
import com.intellij.psi.PsiFile

interface ISchemaStore {
    fun getSchemaOrNull(file: PsiFile, tableName: String): TableSchema?
    fun getTables(file: PsiFile): List<String>
}

class PropertyInfo(val name: String, val referencedTables: List<String> = emptyList())
class TableSchema(val name: String, val properties: List<PropertyInfo>, val type: TableType)

enum class TableType() {
    @SerializedName("0")
    Main(),
    @SerializedName("1")
    TableSection()
}
