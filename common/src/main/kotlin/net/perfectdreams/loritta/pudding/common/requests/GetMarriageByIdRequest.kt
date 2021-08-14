package net.perfectdreams.loritta.pudding.common.requests

import kotlinx.serialization.Serializable

@Serializable
class GetMarriageByIdRequest(val id: Long) : RPCRequest()