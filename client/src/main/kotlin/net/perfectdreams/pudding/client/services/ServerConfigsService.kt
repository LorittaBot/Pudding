package net.perfectdreams.pudding.client.services

import io.ktor.client.*
import net.perfectdreams.loritta.pudding.common.data.ServerConfigRoot
import net.perfectdreams.loritta.pudding.common.requests.GetServerConfigRootByIdRequest
import net.perfectdreams.loritta.pudding.common.responses.GetServerConfigRootResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse

class ServerConfigsService(puddingUrl: String, authorization: String, http: HttpClient) : Service(puddingUrl, authorization, http) {
    suspend fun getServerConfigRootById(id: Long): ServerConfigRoot? {
        return when (val response = callAndDecode(GetServerConfigRootByIdRequest(id))) {
            is NotFoundErrorResponse -> null
            is GetServerConfigRootResponse -> response.serverConfigRoot
            else -> error("I don't know how to handle a $response response!")
        }
    }
}