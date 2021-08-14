package net.perfectdreams.loritta.pudding.server.routes.api.v1

import io.ktor.application.*
import io.ktor.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointsV1
import net.perfectdreams.loritta.pudding.common.requests.GetMarriageByIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetMarriageByUserIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetShipEffectsForUserIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetUserProfileRequest
import net.perfectdreams.loritta.pudding.common.requests.RPCRequest
import net.perfectdreams.loritta.pudding.common.responses.RPCResponse
import net.perfectdreams.loritta.pudding.common.responses.UnknownRequestErrorResponse
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.requesthandlers.GetMarriageByIdRequestHandler
import net.perfectdreams.loritta.pudding.server.requesthandlers.GetMarriageByUserIdRequestHandler
import net.perfectdreams.loritta.pudding.server.requesthandlers.GetShipEffectsForUserIdRequestHandler
import net.perfectdreams.loritta.pudding.server.requesthandlers.GetUserProfileByIdRequestHandler
import net.perfectdreams.loritta.pudding.server.utils.extensions.respondJson

class PostRPCRoute(m: Pudding) : RequiresAuthenticationAPIv1Route(EndpointsV1.RPC, m) {
    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override suspend fun onAuthenticatedRequest(call: ApplicationCall) {
        val payload = call.receiveText()

        when (val request = Json.decodeFromString<RPCRequest>(payload)) {
            // ===[ USER PROFILE ]===
            is GetUserProfileRequest -> GetUserProfileByIdRequestHandler.handle(m, call, request)

            // ===[ MARRIAGES ]===
            is GetMarriageByIdRequest -> GetMarriageByIdRequestHandler.handle(m, call, request)
            is GetMarriageByUserIdRequest -> GetMarriageByUserIdRequestHandler.handle(m, call, request)

            // ===[ SHIP EFFECTS ]===
            is GetShipEffectsForUserIdRequest -> GetShipEffectsForUserIdRequestHandler.handle(m, call, request)

            else -> {
                logger.warn { "I don't know how to handle a $request!" }
                call.respondJson<RPCResponse>(UnknownRequestErrorResponse())
            }
        }
    }
}