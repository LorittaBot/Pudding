package net.perfectdreams.loritta.pudding.common.requests

import kotlinx.serialization.Serializable

@Serializable
class GetUserProfileRequest(val id: Long, val createIfMissing: Boolean) : RPCRequest()