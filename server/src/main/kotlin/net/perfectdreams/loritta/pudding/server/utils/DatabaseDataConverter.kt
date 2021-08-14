package net.perfectdreams.loritta.pudding.server.utils

import kotlinx.datetime.Instant
import net.perfectdreams.loritta.pudding.common.data.Marriage
import net.perfectdreams.loritta.pudding.common.data.ShipEffect
import net.perfectdreams.loritta.pudding.common.data.UserProfile
import net.perfectdreams.loritta.pudding.server.database.tables.Marriages
import net.perfectdreams.loritta.pudding.server.database.tables.Profiles
import net.perfectdreams.loritta.pudding.server.database.tables.ShipEffects
import org.jetbrains.exposed.sql.ResultRow

fun UserProfile.Companion.fromRow(row: ResultRow) = UserProfile(
    row[Profiles.id].value,
    row[Profiles.money]
)

fun Marriage.Companion.fromRow(row: ResultRow) = Marriage(
    row[Marriages.id].value,
    row[Marriages.user1],
    row[Marriages.user2],
    Instant.fromEpochMilliseconds(row[Marriages.marriedSince])
)

fun ShipEffect.Companion.fromRow(row: ResultRow) = ShipEffect(
    row[ShipEffects.id].value,
    row[ShipEffects.buyerId],
    row[ShipEffects.user1Id],
    row[ShipEffects.user2Id],
    row[ShipEffects.editedShipValue],
    Instant.fromEpochMilliseconds(row[ShipEffects.expiresAt])
)