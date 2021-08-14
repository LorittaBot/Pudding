package net.perfectdreams.pudding.client.services

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.perfectdreams.loritta.pudding.common.endpoints.Endpoint
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointMethod
import net.perfectdreams.loritta.pudding.common.endpoints.Endpoints
import net.perfectdreams.loritta.pudding.common.requests.RPCRequest
import net.perfectdreams.loritta.pudding.common.responses.RPCResponse

open class Service(val puddingUrl: String, val authorization: String, val http: HttpClient) {
    suspend inline fun <reified T : RPCRequest> callAndDecode(obj: T): RPCResponse {
        val response = call(obj)
        return Json.decodeFromString<RPCResponse>(response.readText().also { println(it) })
    }

    suspend inline fun <reified T : RPCRequest> call(obj: T) = http.post<HttpResponse>(
        "$puddingUrl/api/v1/rpc"
    ) {
        userAgent("PuddingClient")
        header("Authorization", authorization)

        body = Json.encodeToString<RPCRequest>(obj)
    }

    suspend fun call(endpoint: Endpoint, block: (a: MutableMap<Endpoints.Key, String>) -> (Unit)) = http.request<HttpResponse>(
        puddingUrl + endpoint.build(
            mutableMapOf<Endpoints.Key, String>().apply(block)
        )
    ) {
        this.method = when (endpoint.method) {
            EndpointMethod.GET -> HttpMethod.Get
            EndpointMethod.POST -> HttpMethod.Post
            else -> throw UnsupportedOperationException("Method not supported ${endpoint.method}!")
        }

        userAgent("PuddingClient")
        header("Authorization", authorization)
    }
}