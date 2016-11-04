package com.simple1c.dataSources

import java.util.*

data class DataSource constructor(val id: UUID,
                                  private val displayName: String?,
                                  val connectionString: PostgresConnectionString) {
    constructor(displayName: String?,
                connectionString: PostgresConnectionString)
    : this(UUID.randomUUID(), displayName, connectionString)

    fun getName(): String {
        if (displayName != null)
            return displayName
        return connectionString.format()
    }
}

