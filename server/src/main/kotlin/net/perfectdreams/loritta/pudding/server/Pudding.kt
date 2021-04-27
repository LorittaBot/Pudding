package net.perfectdreams.loritta.pudding.server

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import net.perfectdreams.loritta.pudding.server.database.Database
import net.perfectdreams.loritta.pudding.server.routes.api.v1.users.GetUserProfileRoute
import net.perfectdreams.loritta.pudding.server.routes.api.v1.users.PostUserProfileRoute
import net.perfectdreams.loritta.pudding.server.utils.config.PuddingConfig

class Pudding(val config: PuddingConfig) {
    private val routes = listOf(
        GetUserProfileRoute(this),
        PostUserProfileRoute(this)
    )

    val database = Database(config.database)

    fun start() {
        runBlocking {
            database.createMissingTablesAndColumns()
        }

        val server = embeddedServer(Netty, 8080) {
            routing {
                for (route in routes) {
                    route.register(this)
                }

                get("/") {
                    call.respondText("Pudding!")
                }
            }
        }

        server.start(true)
    }
}