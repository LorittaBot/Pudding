package net.perfectdreams.pudding.client.services

import io.ktor.client.*
import net.perfectdreams.loritta.pudding.common.data.Marriage
import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.common.requests.GetMarriageByIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetMarriageByUserIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetUserProfileRequest
import net.perfectdreams.loritta.pudding.common.responses.GetMarriageResponse
import net.perfectdreams.loritta.pudding.common.responses.GetUserProfileResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse

class MarriagesService(puddingUrl: String, authorization: String, http: HttpClient) : Service(puddingUrl, authorization, http) {
    suspend fun getMarriageById(id: Long): Marriage? {
        return when (val response = callAndDecode(GetMarriageByIdRequest(id))) {
            is NotFoundErrorResponse -> null
            is GetMarriageResponse -> response.marriage
            else -> error("I don't know how to handle a $response response!")
        }
    }

    suspend fun getMarriageByUser(userId: Long): Marriage? {
        return when (val response = callAndDecode(GetMarriageByUserIdRequest(userId))) {
            is NotFoundErrorResponse -> null
            is GetMarriageResponse -> response.marriage
            else -> error("I don't know how to handle a $response response!")
        }
    }
}