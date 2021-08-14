package net.perfectdreams.pudding.client.services

import io.ktor.client.*
import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.common.requests.GetUserProfileRequest
import net.perfectdreams.loritta.pudding.common.responses.GetUserProfileResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse

class UserService(puddingUrl: String, authorization: String, http: HttpClient) : Service(puddingUrl, authorization, http) {
    suspend fun getOrCreateUserProfileById(id: Long): UserProfile {
        val response = callAndDecode(GetUserProfileRequest(id, true))
        require(response is GetUserProfileResponse)
        return response.profile
    }

    suspend fun getUserProfileById(id: Long): UserProfile? {
        return when (val response = callAndDecode(GetUserProfileRequest(id, false))) {
            is NotFoundErrorResponse -> null
            is GetUserProfileResponse -> response.profile
            else -> error("I don't know how to handle a $response response!")
        }
    }
}