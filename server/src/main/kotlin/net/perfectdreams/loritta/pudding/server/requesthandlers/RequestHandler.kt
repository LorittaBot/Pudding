package net.perfectdreams.loritta.pudding.server.requesthandlers

import io.ktor.application.*
import net.perfectdreams.loritta.pudding.server.Pudding

interface RequestHandler<T> {
    suspend fun handle(m: Pudding, call: ApplicationCall, request: T)
}