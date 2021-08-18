package net.perfectdreams.pudding.client.services

import io.ktor.client.*
import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.common.requests.GetSonhosRankPositionBySonhosRequest
import net.perfectdreams.loritta.pudding.common.requests.GetUserProfileRequest
import net.perfectdreams.loritta.pudding.common.responses.GetSonhosRankPositionBySonhosResponse
import net.perfectdreams.loritta.pudding.common.responses.GetUserProfileResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse

class SonhosService(puddingUrl: String, authorization: String, http: HttpClient) : Service(puddingUrl, authorization, http) {
    suspend fun getSonhosRankPositionBySonhos(sonhos: Long): Long {
        val response = callAndDecode(GetSonhosRankPositionBySonhosRequest(sonhos))
        require(response is GetSonhosRankPositionBySonhosResponse)
        return response.position
    }
}