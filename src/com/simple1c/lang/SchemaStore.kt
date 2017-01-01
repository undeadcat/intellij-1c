package com.simple1c.lang

import com.intellij.psi.PsiFile
import com.simple1c.dataSources.DataSourceAssociations
import com.simple1c.remote.*
import com.simple1c.remote.AnalysisHostProcess

class SchemaStore(private val analysisHost: AnalysisHostProcess,
                  private val dataSourceAssociations: DataSourceAssociations) : ISchemaStore {

    override fun getTables(file: PsiFile): List<String> {
        if (!analysisHost.isAvailable())
            return emptyList()
        val dataSource = dataSourceAssociations.getOrNull(file)
        if (dataSource == null)
            return emptyList()
        val request = TableListRequest(dataSource.connectionString.format())
        val result: TableListResult? = analysisHost.getTransport()
                .invoke("listTables", request, TableListResult::class.java)
        return result ?: emptyList()
    }

    override fun getSchemaOrNull(file: PsiFile, tableName: String): TableSchema? {
        if (tableName.isEmpty())
            throw RuntimeException("Assertion failure. Should not query for empty table name")
        if (!analysisHost.isAvailable())
            return null
        val dataSource = dataSourceAssociations.getOrNull(file)
        if (dataSource == null)
            return null
        val request = TableSchemaRequest(dataSource.connectionString.format(), tableName)
        val result: TableMappingDto? = analysisHost.getTransport()
                .invoke("tableMapping", request, TableMappingDto::class.java)
        if (result == null)
            return null
        return TableSchema(tableName, result.properties.map { PropertyInfo(it.name, it.tables) })
    }
}

class TableSchema(val name: String, val properties: List<PropertyInfo>)

