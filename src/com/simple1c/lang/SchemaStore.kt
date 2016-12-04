package com.simple1c.lang

import com.intellij.psi.PsiFile
import com.simple1c.dataSources.DataSourceAssociations
import com.simple1c.remote.*
import com.simple1c.remote.AnalysisHostProcess

class SchemaStore(private val analysisHost: AnalysisHostProcess,
                  private val dataSourceAssociations: DataSourceAssociations) : ISchemaStore {

    override fun getTables(file: PsiFile): List<String> {
        val dataSource = dataSourceAssociations.getOrNull(file)
        if (dataSource == null)
            return emptyList()
        val request = TableListRequest(dataSource.connectionString.format())
        return analysisHost.getTransportOrNull()!!
                .invoke("listTables", request, TableListResult::class.java)
    }

    override fun getSchemaOrNull(file: PsiFile, tableName: String): TableSchema? {
        val dataSource = dataSourceAssociations.getOrNull(file)
        if (dataSource == null)
            return null
        val request = TableSchemaRequest(dataSource.connectionString.format(), tableName)
        val props = analysisHost.getTransportOrNull()!!
                .invoke("tableMapping", request, TableMappingDto::class.java)
                .properties.map { PropertyInfo(it.name, it.tables) }
        return TableSchema(tableName, props)
    }
}

class TableSchema(val name: String, val properties: List<PropertyInfo>)

