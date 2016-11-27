package com.simple1c.execution

import com.intellij.notification.Notification
import com.intellij.notification.Notifications
import com.intellij.openapi.application.Application
import com.intellij.openapi.progress.PerformInBackgroundOption
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.simple1c.dataSources.DataSource
import com.simple1c.remote.*
import java.util.concurrent.Executors

//todo. add timeout
//todo. handle cancellation. don't eat threads in case of hanging query.
class QueryExecutor(val application: Application,
                    val analysisHostProcess: AnalysisHostProcess,
                    val progressManager: ProgressManager) {
    init {
        if (counter != 0)
            throw Exception("You fucked up again")
        counter++
        if (true){

        }
    }

    private val sync = Any()
    private var currentQuery: Task.Backgroundable? = null
    private val backgroundExecutor = Executors.newSingleThreadExecutor()
    private val publisher = application.messageBus.syncPublisher(QueryListener.Topic)

    fun executeQuery(project: Project, query: String, dataSource: DataSource) {
        val transport = analysisHostProcess.getTransport()
        val task = object : Task.Backgroundable(project, "Executing query", true, PerformInBackgroundOption.ALWAYS_BACKGROUND) {
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                try {
                    publisher.log(query)
                    val request = TranslationRequest(dataSource.connectionString.format(), query)
                    val translated = transport.invoke("translate", request, TranslationResult::class.java)
                    val error = translated.error
                    if (error != null) {
                        publisher.errorOccurred(error)
                        return
                    }

                    val translatedQuery = translated.result.orEmpty()
                    publisher.log(translatedQuery)
                    val executionRequest = ExecuteQueryRequest(dataSource.connectionString.format(), translatedQuery)
                    val result = transport.invoke("executeQuery", executionRequest, QueryResult::class.java)
                    publisher.endExecute(result.columns)
                    for (row in result.rows)
                        publisher.rowFetched(row.toList())
                } catch(e: RemoteException) {
                    publisher.errorOccurred(e.message.orEmpty())
                } finally {
                    synchronized(sync) {
                        currentQuery = null
                    }
                }
            }
        }
        synchronized(sync, {
            if (currentQuery != null)
                throw Exception("Cannot start query while another one is running")
            currentQuery = task
            task.queue()
        })
    }

    fun hasQueryInProgress() =
            synchronized(sync, { currentQuery != null })

    fun cancelQuery() {
        synchronized(sync, {
            val myQuery = currentQuery
            if (myQuery == null)
                return
            //todo.
//            myQuery.cancel(true)
            currentQuery = null
            publisher.queryCancelled()
        })
    }

    companion object {
        var counter = 0
    }
}