package net.perfectdreams.pudding.client

import io.ktor.client.*
import net.perfectdreams.pudding.client.services.UserService

class PuddingClient(
    puddingUrl: String,
    authorization: String,
    http: HttpClient
) {
    val users = UserService(puddingUrl, http)
}