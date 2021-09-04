package net.perfectdreams.loritta.pudding.server

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import net.perfectdreams.loritta.pudding.server.database.Database
import net.perfectdreams.loritta.pudding.server.routes.api.v1.PostRPCRoute
import net.perfectdreams.loritta.pudding.server.utils.config.PuddingConfig

class Pudding(val config: PuddingConfig) {
    companion object {
        private val logger = KotlinLogging.logger {}
        private val TimeToProcess = AttributeKey<Long>("TimeToProcess")
    }

    private val routes = listOf(
        PostRPCRoute(this)
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

            this.environment.monitor.subscribe(Routing.RoutingCallStarted) { call: RoutingApplicationCall ->
                call.attributes.put(TimeToProcess, System.currentTimeMillis())
                val userAgent = call.request.userAgent()
                val ip = call.request.local.remoteHost
                val queryString = "?" + call.request.queryString()
                val httpMethod = call.request.httpMethod.value

                logger.info { "$ip (${userAgent}): $httpMethod ${call.request.path()}${queryString}" }
            }

            this.environment.monitor.subscribe(Routing.RoutingCallFinished) { call: RoutingApplicationCall ->
                val originalStartTime = call.attributes[TimeToProcess]
                val userAgent = call.request.userAgent()
                val ip = call.request.local.remoteHost
                val queryString = "?" + call.request.queryString()
                val httpMethod = call.request.httpMethod.value

                logger.info { "$ip (${userAgent}): $httpMethod ${call.request.path()}${queryString} - OK! ${System.currentTimeMillis() - originalStartTime}ms" }
            }
        }

        server.start(true)
    }
}