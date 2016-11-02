package com.simple1c.execution

import com.intellij.util.messages.MessageBus
import java.util.concurrent.Executors
import java.util.concurrent.Future

class QueryExecutor(messageBus: MessageBus) {
    private val sync = Any()
    private var currentQuery: Future<*>? = null
    private val backgroundExecutor = Executors.newSingleThreadExecutor()
    private val publisher = messageBus.syncPublisher(QueryListener.Topic)

    fun executeQuery(query: String) {
        synchronized(sync, {
            if (currentQuery != null)
                throw Exception("Cannot start query while another one is running")
            val future = backgroundExecutor.submit(
                    {
                        try {
                            publisher.beginExecute(query)
                            Thread.sleep(500)
                            publisher.endExecute(listOf("Foo", "Bar"))
                            publisher.rowFetched(listOf("FooVal", "BarVal"))
                            synchronized(sync) {
                                currentQuery = null
                            }
                        } catch(e: Exception) {
                            publisher.errorOccurred(e)
                        }
                    })
            currentQuery = future
        })
    }

    fun hasQueryInProgress() =
            synchronized(sync, { currentQuery != null })

    fun cancelQuery() {
        synchronized(sync, {
            val myQuery = currentQuery
            if (myQuery == null)
                return
            myQuery.cancel(true)
            currentQuery = null
            publisher.queryCancelled()
        })
    }
}