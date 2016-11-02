package com.simple1c.execution

import com.intellij.util.messages.Topic

interface QueryListener {
    fun beginExecute(query: String)
    fun errorOccurred(exception: Exception)
    fun endExecute(columns: List<String>)
    fun rowFetched(values: List<Any>)

    companion object {

        val Topic: Topic<QueryListener> = com.intellij.util.messages.Topic.create("1C.ExecuteQuery",
                QueryListener::class.java, com.intellij.util.messages.Topic.BroadcastDirection.NONE)
    }
}