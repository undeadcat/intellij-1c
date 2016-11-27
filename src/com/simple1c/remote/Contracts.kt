package com.simple1c.remote

import java.util.*


data class TranslationRequest(var connectionString: String, var query: String)

data class TranslationResult(var result: String?, var error: String?) {

}

data class ExecuteQueryRequest(var connectionString: String, var query: String)

data class QueryResult(var columns: List<Column>, var rows: List<Array<Any>>) {

    data class Column(var name: String, var maxLength: Int, var dataType: String)
}

data class TableListRequest(var connectionString: String)

class TableListResult : ArrayList<String>()

data class TableSchemaRequest(var connectionString: String, var tableName: String)

data class TableMappingDto(var name: String, var properties: List<PropertyDto>)

data class PropertyDto(var name: String, var tables: List<String>)
