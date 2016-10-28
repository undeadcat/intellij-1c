package com.simple1c.dataSources

class DataSourceStorage {
    private var dataSources = listOf(DataSource("Database 1"), DataSource("Database 2"))
    fun getAll(): Iterable<DataSource> {
        return dataSources
    }

    fun addOrUpdate(dataSource: DataSource) {
        dataSources = (dataSources + dataSource).distinctBy { it.id }
    }

}