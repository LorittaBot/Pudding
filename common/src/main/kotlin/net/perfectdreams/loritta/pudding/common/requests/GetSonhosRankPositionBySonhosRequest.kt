package net.perfectdreams.loritta.pudding.common.requests

import kotlinx.serialization.Serializable

@Serializable
class GetSonhosRankPositionBySonhosRequest(val sonhos: Long) : RPCRequest()