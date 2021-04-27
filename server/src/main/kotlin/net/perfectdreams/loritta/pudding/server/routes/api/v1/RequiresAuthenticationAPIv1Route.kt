package net.perfectdreams.loritta.pudding.server.routes.api.v1

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import net.perfectdreams.loritta.pudding.common.endpoints.Endpoint
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointMethod
import net.perfectdreams.loritta.pudding.common.endpoints.Endpoints
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointsV1
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.routes.api.VersionedAPIRoute

abstract class RequiresAuthenticationAPIv1Route(endpoint: Endpoint, val m: Pudding) : VersionedAPIRoute(EndpointsV1, endpoint.path) {
    override suspend fun onRequest(call: ApplicationCall) {
        val authorization = call.request.authorization()
        if (authorization in m.config.keys)
            onAuthenticatedRequest(call)
        else
            call.respondText("", ContentType.Text.Html, HttpStatusCode.Unauthorized)
    }

    abstract suspend fun onAuthenticatedRequest(call: ApplicationCall)
}