package net.perfectdreams.loritta.pudding.common.endpoints

abstract class Endpoints {
    abstract val version: String

    fun build(method: EndpointMethod, str: String) = Endpoint(this, method, str)

    open class Key(val identifier: String) {
        override fun toString(): String = "{$identifier}"
    }
}