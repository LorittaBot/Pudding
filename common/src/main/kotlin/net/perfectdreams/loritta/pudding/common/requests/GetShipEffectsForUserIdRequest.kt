package net.perfectdreams.loritta.pudding.common.requests

import kotlinx.serialization.Serializable

@Serializable
class GetShipEffectsForUserIdRequest(val userId: Long) : RPCRequest()