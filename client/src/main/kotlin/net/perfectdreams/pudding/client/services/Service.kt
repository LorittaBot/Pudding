package net.perfectdreams.pudding.client.services

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import net.perfectdreams.loritta.pudding.common.endpoints.Endpoint
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointMethod
import net.perfectdreams.loritta.pudding.common.endpoints.Endpoints

open class Service(private val puddingUrl: String, private val authorization: String, private val http: HttpClient) {
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