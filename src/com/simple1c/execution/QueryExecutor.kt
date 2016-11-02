package com.simple1c.execution

import com.intellij.util.messages.MessageBus
import java.util.concurrent.Executors

class QueryExecutor(val messageBus: MessageBus) {
    private val backgroundExecutor = Executors.newCachedThreadPool()
    fun executeQuery(query: String) {
        val publisher = messageBus.syncPublisher(QueryListener.Topic)
        backgroundExecutor.submit {
            try {
                publisher.beginExecute(query)
                Thread.sleep(500)
                publisher.endExecute(listOf("Foo", "Bar"))
                publisher.rowFetched(listOf("FooVal", "BarVal"))
            } catch(e: Exception) {
                publisher.errorOccurred(e)
            }

        }

    }
}