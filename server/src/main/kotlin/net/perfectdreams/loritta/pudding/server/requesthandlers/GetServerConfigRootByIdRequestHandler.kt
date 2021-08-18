package net.perfectdreams.loritta.pudding.server.requesthandlers

import io.ktor.application.*
import io.ktor.http.*
import net.perfectdreams.loritta.pudding.common.data.ServerConfigRoot
import net.perfectdreams.loritta.pudding.common.requests.GetServerConfigRootByIdRequest
import net.perfectdreams.loritta.pudding.common.responses.GetServerConfigRootResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse
import net.perfectdreams.loritta.pudding.common.responses.RPCResponse
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.database.tables.ServerConfigs
import net.perfectdreams.loritta.pudding.server.utils.extensions.respondJson
import net.perfectdreams.loritta.pudding.server.utils.fromRow
import org.jetbrains.exposed.sql.select

object GetServerConfigRootByIdRequestHandler : RequestHandler<GetServerConfigRootByIdRequest> {
    override suspend fun handle(m: Pudding, call: ApplicationCall, request: GetServerConfigRootByIdRequest) {
        val id = request.id

        val serverConfigRoot = m.database.transaction {
            ServerConfigs.select {
                ServerConfigs.id eq id
            }.firstOrNull()
        }

        if (serverConfigRoot == null) {
            // If the profile is null, we will just return a "NotFound" error
            call.respondJson<RPCResponse>(NotFoundErrorResponse(), HttpStatusCode.NotFound)
            return
        }

        call.respondJson<RPCResponse>(GetServerConfigRootResponse(ServerConfigRoot.fromRow(serverConfigRoot)))
    }
}