package net.perfectdreams.pudding.client.requests

import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointsV1
import net.perfectdreams.pudding.client.services.Service

class UserProfileRequest(private val id: Long, private val service: Service) {
    suspend fun retrieve(): UserProfile? {
        val response = service.call(EndpointsV1.GET_USER) {
            it[EndpointsV1.UserId] = id.toString()
        }

        if (response.status == HttpStatusCode.NotFound)
            return null

        return Json.decodeFromString(response.readText())
    }

    suspend fun retrieveOrCreate(): UserProfile {
        val response = service.call(EndpointsV1.GET_OR_CREATE_USER) {
            it[EndpointsV1.UserId] = id.toString()
        }

        return Json.decodeFromString(response.readText())
    }
}