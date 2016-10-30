package com.simple1c.dataSources

import coreUtils.isEquivalentTo

class DataSourceStorage {
    private val listeners = arrayListOf<UpdateListener>()

    private var dataSources = listOf(DataSource("Database 1"), DataSource("Database 2"))
    fun getAll(): Iterable<DataSource> {
        return dataSources
    }

    fun addUpdateListener(listener: UpdateListener) {
        listeners.add(listener)
    }

    fun addOrUpdate(dataSource: DataSource) {
        val newValues = (dataSources + dataSource).distinctBy { it.id }
        if (dataSources.isEquivalentTo(newValues))
            return
        dataSources = newValues
        listeners.forEach { it.run(newValues) }
    }

    fun newTemplate(): DataSource {
        val nameTemplate = "New Data Source "
        var counter = 1
        while (dataSources.any { it.getName() == nameTemplate + counter })
            counter++
        return DataSource(nameTemplate + counter)
    }
    interface UpdateListener {
        fun run(values: List<DataSource>)

    }

}