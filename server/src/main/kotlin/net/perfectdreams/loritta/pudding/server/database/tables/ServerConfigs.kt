package net.perfectdreams.loritta.pudding.server.database.tables

object ServerConfigs : SnowflakeTable() {
    val localeId = text("locale_id").default("default")
}