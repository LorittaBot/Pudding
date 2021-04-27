package net.perfectdreams.loritta.pudding.server.routes.api.v1.users

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointsV1
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.database.tables.Profiles
import net.perfectdreams.loritta.pudding.server.routes.api.v1.APIv1Route
import net.perfectdreams.loritta.pudding.server.routes.api.v1.RequiresAuthenticationAPIv1Route
import net.perfectdreams.loritta.pudding.server.utils.fromRow
import org.jetbrains.exposed.sql.select

class GetUserProfileRoute(m: Pudding) : RequiresAuthenticationAPIv1Route(EndpointsV1.GET_USER, m) {
    override suspend fun onAuthenticatedRequest(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!.toLong()

        val profile = m.database.transaction {
            Profiles.select { Profiles.id eq userId }.firstOrNull()
        } ?: run {
            call.respondText("", ContentType.Text.Plain, HttpStatusCode.NotFound)
            return
        }

        call.respondText(
            ContentType.Application.Json,
            HttpStatusCode.OK
        ) {
            Json.encodeToString(UserProfile.fromRow(profile))
        }
    }
}