package net.perfectdreams.loritta.pudding.server.requesthandlers

import io.ktor.application.*
import io.ktor.http.*
import kotlinx.datetime.Instant
import net.perfectdreams.loritta.pudding.common.data.Marriage
import net.perfectdreams.loritta.pudding.common.requests.GetMarriageByIdRequest
import net.perfectdreams.loritta.pudding.common.responses.GetMarriageResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse
import net.perfectdreams.loritta.pudding.common.responses.RPCResponse
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.database.tables.Marriages
import net.perfectdreams.loritta.pudding.server.utils.extensions.respondJson
import net.perfectdreams.loritta.pudding.server.utils.fromRow
import org.jetbrains.exposed.sql.select

object GetMarriageByIdRequestHandler : RequestHandler<GetMarriageByIdRequest> {
    override suspend fun handle(m: Pudding, call: ApplicationCall, request: GetMarriageByIdRequest) {
        val marriage = m.database.transaction {
            Marriages.select { Marriages.id eq request.id }
                .firstOrNull()
        }

        if (marriage == null) {
            // If it's null, we will just return a "NotFound" error
            call.respondJson<RPCResponse>(NotFoundErrorResponse(), HttpStatusCode.NotFound)
            return
        }

        call.respondJson<RPCResponse>(
            GetMarriageResponse(Marriage.fromRow(marriage)),
            HttpStatusCode.OK
        )
    }
}