package net.perfectdreams.loritta.pudding.common.data

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Marriage(
    val id: Long,
    val user1: Long,
    val user2: Long,
    val marriedSince: Instant
)