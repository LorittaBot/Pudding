package net.perfectdreams.loritta.pudding.server.routes.api.v1

import io.ktor.http.*
import net.perfectdreams.loritta.pudding.common.endpoints.Endpoint
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointMethod
import net.perfectdreams.loritta.pudding.common.endpoints.Endpoints
import net.perfectdreams.loritta.pudding.common.endpoints.EndpointsV1
import net.perfectdreams.loritta.pudding.server.routes.api.VersionedAPIRoute

abstract class APIv1Route(val endpoint: Endpoint) : VersionedAPIRoute(EndpointsV1, endpoint.path) {
    override fun getMethod() = when (endpoint.method) {
        EndpointMethod.GET -> HttpMethod.Get
        EndpointMethod.POST -> HttpMethod.Post
        else -> throw UnsupportedOperationException("Method not supported ${endpoint.method}!")
    }
}