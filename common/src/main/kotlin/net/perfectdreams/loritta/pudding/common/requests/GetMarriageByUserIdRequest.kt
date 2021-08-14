package net.perfectdreams.loritta.pudding.common.requests

import kotlinx.serialization.Serializable

@Serializable
class GetMarriageByUserIdRequest(val userId: Long) : RPCRequest()