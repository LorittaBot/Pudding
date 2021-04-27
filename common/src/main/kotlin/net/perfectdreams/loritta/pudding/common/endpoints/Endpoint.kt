package net.perfectdreams.loritta.pudding.common.endpoints

/**
 * Represents a endpoint path, useful to build the endpoint URL with specific arguments
 */
class Endpoint(val endpoints: Endpoints, val method: EndpointMethod, val path: String) {
    fun build(vararg pairs: Pair<Endpoints.Key, String>) = build(mapOf(*pairs))

    fun build(keys: Map<Endpoints.Key, String>): String {
        var path = path
        for ((key, value) in keys) {
            path = path.replace(key.toString(), value)
        }
        return "/api/${endpoints.version}$path"
    }
}