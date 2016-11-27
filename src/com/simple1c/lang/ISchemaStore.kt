package com.simple1c.lang

interface ISchemaStore {
    fun getSchema(tableName: String): List<PropertyInfo>
    fun getTables(): List<String>
}

class PropertyInfo(val name: String, val referencedTables: List<String>)