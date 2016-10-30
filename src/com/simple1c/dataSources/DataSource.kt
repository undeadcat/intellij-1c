package com.simple1c.dataSources

import java.util.*

class DataSource(private val explicitName: String?,
                 val host: String? = null,
                 val port: Int? = null,
                 val database: String? = null,
                 val user: String? = null,
                 val password: String? = null) {
    val id: UUID = UUID.randomUUID()

    fun getName(): String {
        if (explicitName != null)
            return explicitName
        return "$user:$password@$host:$port/$database"
    }
}