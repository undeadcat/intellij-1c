package com.simple1c.impl

interface ISchemaStore {
    fun getColumns(tableName: String?): Iterable<String>
    fun getTables(): Iterable<String>
}