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
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

class PostUserProfileRoute(m: Pudding) : RequiresAuthenticationAPIv1Route(EndpointsV1.GET_OR_CREATE_USER, m) {
    override suspend fun onAuthenticatedRequest(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!.toLong()

        var isNewProfile = false

        val profile = m.database.transaction {
            Profiles.select { Profiles.id eq userId }.firstOrNull() ?: run {
                isNewProfile = true

                val insertedNumber = Profiles.insertAndGetId {
                    it[id] = userId
                    it[money] = 0L
                }

                Profiles.select { Profiles.id eq insertedNumber }.first()
            }
        }

        call.respondText(
            ContentType.Application.Json,
            if (isNewProfile) HttpStatusCode.Created else HttpStatusCode.OK
        ) {
            Json.encodeToString(UserProfile.fromRow(profile))
        }
    }
}