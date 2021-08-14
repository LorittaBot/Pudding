package net.perfectdreams.loritta.pudding.server.requesthandlers

import io.ktor.application.*
import io.ktor.http.*
import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.common.requests.GetUserProfileRequest
import net.perfectdreams.loritta.pudding.common.responses.GetUserProfileResponse
import net.perfectdreams.loritta.pudding.common.responses.NotFoundErrorResponse
import net.perfectdreams.loritta.pudding.common.responses.RPCResponse
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.database.tables.Profiles
import net.perfectdreams.loritta.pudding.server.utils.extensions.respondJson
import net.perfectdreams.loritta.pudding.server.utils.fromRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

object GetUserProfileByIdRequestHandler : RequestHandler<GetUserProfileRequest> {
    override suspend fun handle(m: Pudding, call: ApplicationCall, request: GetUserProfileRequest) {
        val userId = request.id
        val createIfMissing = request.createIfMissing
        var isNewProfile = false

        val profile = m.database.transaction {
            val profile = Profiles.select { Profiles.id eq userId }.firstOrNull()

            if (profile == null) {
                // If we shouldn't create a profile if it is missing, we will return null here
                if (!createIfMissing)
                    return@transaction null

                // Yay, a new profile, let's go!
                isNewProfile = true

                val insertedNumber = Profiles.insertAndGetId {
                    it[id] = userId
                    it[money] = 0L
                }

                Profiles.select { Profiles.id eq insertedNumber }.first()
            }

            Profiles.select { Profiles.id eq userId }.firstOrNull()
        }

        if (profile == null) {
            // If the profile is null, we will just return a "NotFound" error
            call.respondJson<RPCResponse>(NotFoundErrorResponse(), HttpStatusCode.NotFound)
            return
        }

        call.respondJson<RPCResponse>(GetUserProfileResponse(UserProfile.fromRow(profile)), if (isNewProfile) HttpStatusCode.Created else HttpStatusCode.OK)
    }
}