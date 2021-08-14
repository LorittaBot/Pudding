package net.perfectdreams.pudding.client.services

import io.ktor.client.*
import net.perfectdreams.loritta.pudding.common.data.Marriage
import net.perfectdreams.loritta.pudding.common.data.ShipEffect
import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.common.requests.GetMarriageByIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetMarriageByUserIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetShipEffectsForUserIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetUserProfileRequest
import net.perfectdreams.loritta.pudding.common.responses.GetMarriageResponse
import net.perfectdreams.loritta.pudding.common.responses.GetShipEffectsForUserIdResponse
import net.perfectdreams.loritta.pudding.common.responses.GetUserProfileResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse

class ShipEffectsService(puddingUrl: String, authorization: String, http: HttpClient) : Service(puddingUrl, authorization, http) {
    suspend fun getShipEffectsForUser(userId: Long): List<ShipEffect> {
        return when (val response = callAndDecode(GetShipEffectsForUserIdRequest(userId))) {
            is GetShipEffectsForUserIdResponse -> response.shipEffects
            else -> error("I don't know how to handle a $response response!")
        }
    }
}