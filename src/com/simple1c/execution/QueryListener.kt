package com.simple1c.execution

import com.intellij.util.messages.Topic
import com.simple1c.remote.QueryResult

interface QueryListener {
    fun log(query: String)
    fun errorOccurred(message: String)
    fun endExecute(columns: List<QueryResult.Column>)
    fun rowFetched(values: List<Any>)
    fun queryCancelled()

    companion object {

        val Topic: Topic<QueryListener> = com.intellij.util.messages.Topic.create("1C.ExecuteQuery",
                QueryListener::class.java, com.intellij.util.messages.Topic.BroadcastDirection.NONE)
    }


}