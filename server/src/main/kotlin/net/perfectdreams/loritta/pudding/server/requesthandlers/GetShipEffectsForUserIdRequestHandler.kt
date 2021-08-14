package net.perfectdreams.loritta.pudding.server.requesthandlers

import io.ktor.application.*
import io.ktor.http.*
import kotlinx.datetime.Instant
import net.perfectdreams.loritta.pudding.common.data.Marriage
import net.perfectdreams.loritta.pudding.common.data.ShipEffect
import net.perfectdreams.loritta.pudding.common.requests.GetMarriageByIdRequest
import net.perfectdreams.loritta.pudding.common.requests.GetShipEffectsForUserIdRequest
import net.perfectdreams.loritta.pudding.common.responses.GetMarriageResponse
import net.perfectdreams.loritta.pudding.common.responses.GetShipEffectsForUserIdResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse
import net.perfectdreams.loritta.pudding.common.responses.RPCResponse
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.database.tables.Marriages
import net.perfectdreams.loritta.pudding.server.database.tables.ShipEffects
import net.perfectdreams.loritta.pudding.server.utils.extensions.respondJson
import net.perfectdreams.loritta.pudding.server.utils.fromRow
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select

object GetShipEffectsForUserIdRequestHandler : RequestHandler<GetShipEffectsForUserIdRequest> {
    override suspend fun handle(m: Pudding, call: ApplicationCall, request: GetShipEffectsForUserIdRequest) {
        val userId = request.userId

        val effects = m.database.transaction {
            ShipEffects.select {
                (ShipEffects.user1Id eq userId) or (ShipEffects.user2Id eq userId)
            }.toList()
        }

        call.respondJson<RPCResponse>(
            GetShipEffectsForUserIdResponse(effects.map { ShipEffect.fromRow(it) }),
            HttpStatusCode.OK
        )
    }
}