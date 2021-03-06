package net.perfectdreams.pudding.client

import io.ktor.client.*
import net.perfectdreams.pudding.client.services.MarriagesService
import net.perfectdreams.pudding.client.services.ServerConfigsService
import net.perfectdreams.pudding.client.services.ShipEffectsService
import net.perfectdreams.pudding.client.services.SonhosService
import net.perfectdreams.pudding.client.services.UserService

class PuddingClient(
    puddingUrl: String,
    authorization: String,
    http: HttpClient
) {
    val users = UserService(puddingUrl, authorization, http)
    val marriages = MarriagesService(puddingUrl, authorization, http)
    val shipEffects = ShipEffectsService(puddingUrl, authorization, http)
    val sonhos = SonhosService(puddingUrl, authorization, http)
    val serverConfigs = ServerConfigsService(puddingUrl, authorization, http)
}