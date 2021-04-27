package net.perfectdreams.loritta.pudding.server.utils

import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.server.database.tables.Profiles
import org.jetbrains.exposed.sql.ResultRow

fun UserProfile.Companion.fromRow(row: ResultRow) = UserProfile(
    row[Profiles.id].value,
    row[Profiles.money]
)