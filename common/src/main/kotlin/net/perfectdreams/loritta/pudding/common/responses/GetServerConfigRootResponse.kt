package net.perfectdreams.loritta.pudding.common.responses

import kotlinx.serialization.Serializable
import net.perfectdreams.loritta.pudding.common.data.ServerConfigRoot

@Serializable
class GetServerConfigRootResponse(val serverConfigRoot: ServerConfigRoot) : RPCResponse()