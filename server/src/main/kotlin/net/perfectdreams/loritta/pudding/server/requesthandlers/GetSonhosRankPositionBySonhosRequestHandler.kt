package net.perfectdreams.loritta.pudding.server.requesthandlers

import io.ktor.application.*
import net.perfectdreams.loritta.pudding.common.requests.GetSonhosRankPositionBySonhosRequest
import net.perfectdreams.loritta.pudding.common.responses.GetSonhosRankPositionBySonhosResponse
import net.perfectdreams.loritta.pudding.common.responses.RPCResponse
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.database.tables.Profiles
import net.perfectdreams.loritta.pudding.server.utils.extensions.respondJson
import org.jetbrains.exposed.sql.select

object GetSonhosRankPositionBySonhosRequestHandler : RequestHandler<GetSonhosRankPositionBySonhosRequest> {
    override suspend fun handle(m: Pudding, call: ApplicationCall, request: GetSonhosRankPositionBySonhosRequest) {
        // TODO: This is not a *good* way to get an user's ranking if there are duplicates, maybe use DENSE_RANK? https://www.postgresqltutorial.com/postgresql-dense_rank-function/
        val position = m.database.transaction {
                Profiles.select { Profiles.money greaterEq request.sonhos }
                    .count()
        }

        call.respondJson<RPCResponse>(GetSonhosRankPositionBySonhosResponse(position))
    }
}