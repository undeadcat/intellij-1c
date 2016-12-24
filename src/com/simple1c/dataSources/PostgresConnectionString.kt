package com.simple1c.dataSources

import coreUtils.equalsIgnoreCase

class PostgresConnectionString constructor(val host: String,
                                           val port: Int = 5432,
                                           val database: String,
                                           val user: String,
                                           val password: String) {

    fun format(): String {
        return "host=$host;" +
                "port=$port;" +
                "database=$database;" +
                "username=$user;" +
                "password=$password"
    }

    companion object {
        @JvmStatic
        fun tryParse(value: String): PostgresConnectionString? {

            val parts = value.split(";").map {
                val parts = it.split("=")
                if (parts.size > 1)
                    Pair(parts[0], parts[1])
                else null
            }.filterNotNull()

            fun tryGetPartValue(key: String) = parts
                    .firstOrNull { it.first.equalsIgnoreCase(key) }
                    ?.second

            val host = tryGetPartValue("host")
            val port = coreUtils.parseIntOrNull(tryGetPartValue("port") ?: "")
            val db = tryGetPartValue("database")
            val user = tryGetPartValue("username")
            val password = tryGetPartValue("password")
            if (host == null || port == null || db == null || user == null || password == null)
                return null
            return PostgresConnectionString(host, port, db, user, password)
        }
    }
}