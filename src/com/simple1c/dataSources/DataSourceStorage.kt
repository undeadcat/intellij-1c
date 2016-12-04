package com.simple1c.dataSources

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.simple1c.configuration.ProjectService
import java.util.*

@ProjectService
@State(name = "1C.DataSourceStorage.State", storages = arrayOf(Storage(StoragePathMacros.WORKSPACE_FILE)))
class DataSourceStorage : PersistentStateComponent<DataSourceStorage.State> {
    private val listeners = arrayListOf<UpdateListener>()
    private var dataSources = listOf<DataSource>()

    override fun loadState(state: State?) {
        if (state == null)
            return
        dataSources = state.dataSources
                .map { fromState(it) }.toMutableList()

        triggerListeners()
    }

    override fun getState(): State? {
        return State(dataSources.map { toState(it) }.toMutableList())
    }

    fun getAll(): Iterable<DataSource> {
        return dataSources
    }

    fun getByIdOrNull(id: UUID): DataSource? {
        return dataSources.firstOrNull { it.id == id }
    }

    fun addUpdateListener(listener: UpdateListener) {
        listeners.add(listener)
    }

    fun addOrUpdate(newValue: DataSource) {
        dataSources = (dataSources.filter { it.id != newValue.id } + newValue).toList()
        triggerListeners()
    }

    fun delete(dataSource: DataSource) {
        dataSources = (dataSources.filter { it.id != dataSource.id }).toList()
        triggerListeners()
    }

    fun newTemplate(): DataSource {
        val nameTemplate = "New Data Source "
        var counter = 1
        while (dataSources.any { it.getName() == nameTemplate + counter })
            counter++
        return DataSource(nameTemplate + counter, PostgresConnectionString("localhost", 5432, "", "", ""))
    }

    private fun triggerListeners() {
        listeners.forEach { it.run(dataSources) }
    }

    private fun fromState(it: DataSourceState): DataSource {
        return DataSource(UUID.fromString(it.id), it.displayName,
                PostgresConnectionString.tryParse(it.connectionString.orEmpty())!!)
    }

    private fun toState(it: DataSource): DataSourceState {
        return DataSourceState(it.id.toString(), it.getName(), it.connectionString.format())
    }

    interface UpdateListener {
        fun run(values: List<DataSource>)
    }

    data class State(var dataSources: MutableList<DataSourceState>) {
        constructor() : this(ArrayList())
    }

    data class DataSourceState constructor(var id: String? = null,
                                           var displayName: String? = null,
                                           var connectionString: String? = null) {
        constructor() : this(null, null, null)
    }

    companion object {
        @JvmStatic
        fun instance(project: Project): DataSourceStorage
                = ServiceManager.getService(project, DataSourceStorage::class.java)
    }
}