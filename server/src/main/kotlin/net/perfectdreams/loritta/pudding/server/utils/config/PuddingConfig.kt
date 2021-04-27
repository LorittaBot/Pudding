package net.perfectdreams.loritta.pudding.server.utils.config

import kotlinx.serialization.Serializable

@Serializable
data class PuddingConfig(
    val keys: List<String>,
    val database: DatabaseConfig
)