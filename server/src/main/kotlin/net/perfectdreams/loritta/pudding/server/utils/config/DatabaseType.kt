package net.perfectdreams.loritta.pudding.server.utils.config

import kotlinx.serialization.Serializable

@Serializable
enum class DatabaseType(val jdbcName: String) {
    POSTGRESQL("postgresql"),
    SQLITE("sqlite"),
    SQLITE_MEMORY("sqlite_memory")
}