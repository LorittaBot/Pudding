package net.perfectdreams.loritta.pudding.server.utils.config

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseConfig(
    val type: DatabaseType,
    val databaseName: String,
    val address: String,
    val username: String,
    val password: String,
    val maximumPoolSize: Int,
    val minimumIdle: Int,
    val updateTableSchema: Boolean
)