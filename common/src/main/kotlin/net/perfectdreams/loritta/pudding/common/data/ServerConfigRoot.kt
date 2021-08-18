package net.perfectdreams.loritta.pudding.common.data

import kotlinx.serialization.Serializable

@Serializable
data class ServerConfigRoot(
    val id: Long,
    val localeId: String
)