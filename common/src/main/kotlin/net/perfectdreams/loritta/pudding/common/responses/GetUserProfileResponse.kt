package net.perfectdreams.loritta.pudding.common.responses

import kotlinx.serialization.Serializable
import net.perfectdreams.loritta.pudding.common.data.UserProfile

@Serializable
class GetUserProfileResponse(val profile: UserProfile) : RPCResponse()