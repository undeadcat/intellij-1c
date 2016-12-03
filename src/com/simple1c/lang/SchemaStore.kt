package com.simple1c.lang

import com.simple1c.dataSources.DataSourceStorage
import com.simple1c.remote.*

class SchemaStore(private val analysisHost: AnalysisHostProcess,
                  private val dataSourceStorage: DataSourceStorage) : ISchemaStore {

    override fun getTables(): List<String> {
        val dataSource = dataSourceStorage.getAll().first()
        val request = TableListRequest(dataSource.connectionString.format())
        return analysisHost.getTransport()
                .invoke("listTables", request, TableListResult::class.java)
    }

    override fun getSchema(tableName: String): TableSchema {
        val dataSource = dataSourceStorage.getAll().first()
        val request = TableSchemaRequest(dataSource.connectionString.format(), tableName)
        val props = analysisHost.getTransport()
                .invoke("tableMapping", request, TableMappingDto::class.java)
                .properties.map { PropertyInfo(it.name, it.tables) }
        return TableSchema(tableName, props)
    }
}

class TableSchema(val name: String, val properties: List<PropertyInfo>)

