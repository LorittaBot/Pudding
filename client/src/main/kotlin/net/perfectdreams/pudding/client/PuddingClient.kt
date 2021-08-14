package net.perfectdreams.pudding.client

import io.ktor.client.*
import net.perfectdreams.pudding.client.services.MarriagesService
import net.perfectdreams.pudding.client.services.ShipEffectsService
import net.perfectdreams.pudding.client.services.UserService

class PuddingClient(
    puddingUrl: String,
    authorization: String,
    http: HttpClient
) {
    val users = UserService(puddingUrl, authorization, http)
    val marriages = MarriagesService(puddingUrl, authorization, http)
    val shipEffects = ShipEffectsService(puddingUrl, authorization, http)
}