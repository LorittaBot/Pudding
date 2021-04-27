package net.perfectdreams.loritta.pudding.common.endpoints

object EndpointsV1 : Endpoints() {
    override val version = "v1"

    val GET_USER = build(EndpointMethod.GET, "/users/$UserId")
    val GET_OR_CREATE_USER = build(EndpointMethod.POST, "/users/$UserId")

    // Inspired by Kord
    object UserId : Key("userId")
}