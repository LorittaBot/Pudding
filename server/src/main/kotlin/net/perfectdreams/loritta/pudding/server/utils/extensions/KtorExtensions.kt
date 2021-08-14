package net.perfectdreams.loritta.pudding.server.utils.extensions

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend fun ApplicationCall.respondJson(json: String, status: HttpStatusCode? = null) = this.respondText(ContentType.Application.Json, status) {
    json
}

suspend inline fun <reified T> ApplicationCall.respondJson(obj: T, status: HttpStatusCode? = null) = this.respondText(ContentType.Application.Json, status) {
    Json.encodeToString<T>(obj)
}