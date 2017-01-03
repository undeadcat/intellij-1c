package com.simple1c.lang

import com.intellij.openapi.components.ServiceManager
import com.intellij.psi.PsiFile
import com.simple1c.configuration.ProjectService
import com.simple1c.dataSources.DataSource
import com.simple1c.dataSources.DataSourceAssociations
import com.simple1c.remote.*
import com.simple1c.remote.AnalysisHostProcess
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class SchemaStore(private val analysisHost: AnalysisHostProcess,
                  private val dataSourceAssociations: DataSourceAssociations) : ISchemaStore {

    override fun getTables(file: PsiFile): List<String> {
        if (!analysisHost.isAvailable())
            return emptyList()
        val dataSource = dataSourceAssociations.getOrNull(file)
        if (dataSource == null)
            return emptyList()
        return ServiceManager.getService(file.project, Cache::class.java)
                .getTables(dataSource.id, { getTablesFromRemote(dataSource) })
    }

    override fun getSchemaOrNull(file: PsiFile, tableName: String): TableSchema? {
        if (tableName.isEmpty())
            throw RuntimeException("Assertion failure. Should not query for empty table name")
        if (!analysisHost.isAvailable())
            return null
        val dataSource = dataSourceAssociations.getOrNull(file)
        if (dataSource == null)
            return null
        return ServiceManager.getService(file.project, Cache::class.java)
                .getSchema(dataSource.id, tableName, { getSchemaFromRemote(dataSource, tableName) })
    }

    private fun getTablesFromRemote(dataSource: DataSource): List<String> {
        val request = TableListRequest(dataSource.connectionString.format())
        val result: TableListResult? = analysisHost.getTransport()
                .invoke("listTables", request, TableListResult::class.java)
        return result ?: emptyList()
    }

    private fun getSchemaFromRemote(dataSource: DataSource, tableName: String): TableSchema? {
        val request = TableSchemaRequest(dataSource.connectionString.format(), tableName)
        val result: TableMappingDto? = analysisHost.getTransport()
                .invoke("tableMapping", request, TableMappingDto::class.java)
        if (result == null)
            return null
        return TableSchema(tableName, result.properties.map { PropertyInfo(it.name, it.tables) }, result.type!!)
    }

    @ProjectService
    class Cache() {
        private val tables = ConcurrentHashMap<UUID, List<String>>()
        private val schemas = ConcurrentHashMap<UUID, ConcurrentHashMap<String, Wrap<TableSchema?>>>()

        fun getSchema(dataSourceId: UUID, tableName: String, creator: () -> TableSchema?): TableSchema? {
            return schemas
                    .getOrPut(dataSourceId, { ConcurrentHashMap<String, Wrap<TableSchema?>>() })
                    .getOrPut(tableName, { Wrap(creator()) }).value
        }

        fun getTables(dataSourceId: UUID, creator: () -> List<String>): List<String> {
            return tables.getOrPut(dataSourceId, creator)
        }

        class Wrap<out T>(val value: T)
    }
}