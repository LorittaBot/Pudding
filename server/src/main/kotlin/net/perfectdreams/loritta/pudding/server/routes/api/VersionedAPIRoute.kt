package net.perfectdreams.loritta.pudding.server.routes.api

import net.perfectdreams.loritta.pudding.common.endpoints.Endpoints
import net.perfectdreams.sequins.ktor.BaseRoute

abstract class VersionedAPIRoute(endpoint: Endpoints, path: String) : BaseRoute(
    "/api/${endpoint.version}$path"
)