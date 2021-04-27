package net.perfectdreams.loritta.pudding.common.data

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: Long,
    val money: Long
)