package net.perfectdreams.loritta.pudding.common.responses

import kotlinx.serialization.Serializable

@Serializable
class ServerErrorResponse(val reason: String) : ErrorResponse()