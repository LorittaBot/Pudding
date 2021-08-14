package net.perfectdreams.loritta.pudding.common.data

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ShipEffect(
    val id: Long,
    val buyerId: Long,
    val user1: Long,
    val user2: Long,
    val editedShipValue: Int,
    val expiresAt: Instant
)