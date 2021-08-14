package net.perfectdreams.loritta.pudding.common.responses

import kotlinx.serialization.Serializable
import net.perfectdreams.loritta.pudding.common.data.ShipEffect
import net.perfectdreams.loritta.pudding.common.data.UserProfile

@Serializable
class GetShipEffectsForUserIdResponse(val shipEffects: List<ShipEffect>) : RPCResponse()