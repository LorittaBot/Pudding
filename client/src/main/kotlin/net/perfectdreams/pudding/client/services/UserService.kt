package net.perfectdreams.pudding.client.services

import io.ktor.client.*
import net.perfectdreams.pudding.client.requests.UserProfileRequest

class UserService(puddingUrl: String, authorization: String, http: HttpClient) : Service(puddingUrl, authorization, http) {
    fun userProfile(id: Long) = UserProfileRequest(id, this)
}