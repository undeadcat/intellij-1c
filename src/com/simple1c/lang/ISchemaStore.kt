package com.simple1c.lang

interface ISchemaStore {
    fun getColumns(tableName: String?): Iterable<String>
    fun getTables(): Iterable<String>
}