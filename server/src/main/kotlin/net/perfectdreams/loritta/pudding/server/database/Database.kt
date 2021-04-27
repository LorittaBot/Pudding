package net.perfectdreams.loritta.pudding.server.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.util.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.perfectdreams.loritta.pudding.server.Pudding
import net.perfectdreams.loritta.pudding.server.database.tables.Profiles
import net.perfectdreams.loritta.pudding.server.utils.NetAddressUtils
import net.perfectdreams.loritta.pudding.server.utils.config.DatabaseConfig
import net.perfectdreams.loritta.pudding.server.utils.config.DatabaseType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.Connection

class Database(private val m: DatabaseConfig) {
    val lorittaDatabase: Database

    init {
        val hikariConfig = HikariConfig()

        val driverClassName: String
        val jdbcPath: String

        when (m.type) {
            DatabaseType.POSTGRESQL -> {
                driverClassName = "org.postgresql.Driver"
                jdbcPath = "postgresql://${NetAddressUtils.getWithPortIfMissing(m.address, 5432)}/${m.databaseName}"
            }
            DatabaseType.SQLITE -> {
                driverClassName = "org.sqlite.JDBC"
                val sqLiteDbFile = File("loritta.db")
                jdbcPath = "sqlite:${sqLiteDbFile.toPath()}"
            }
            DatabaseType.SQLITE_MEMORY -> {
                driverClassName = "org.sqlite.JDBC"
                jdbcPath = "sqlite:file:loritta?mode=memory&cache=shared"
            }
        }

        hikariConfig.jdbcUrl = "jdbc:$jdbcPath"
        hikariConfig.driverClassName = driverClassName
        hikariConfig.username = m.username
        hikariConfig.password = m.password

        // Exposed uses autoCommit = false, so we need to set this to false to avoid HikariCP resetting the connection to
        // autoCommit = true when the transaction goes back to the pool, because resetting this has a "big performance impact"
        // https://stackoverflow.com/a/41206003/7271796
        hikariConfig.isAutoCommit = false

        // Useful to check if a connection is not returning to the pool, will be shown in the log as "Apparent connection leak detected"
        hikariConfig.leakDetectionThreshold = 30 * 1000

        hikariConfig.transactionIsolation = if (m.type != DatabaseType.SQLITE && m.type != DatabaseType.SQLITE_MEMORY)
            "TRANSACTION_REPEATABLE_READ"
        else
            "TRANSACTION_SERIALIZABLE"

        val ds = HikariDataSource(hikariConfig)
        lorittaDatabase = Database.connect(ds)
    }

    suspend fun createMissingTablesAndColumns() {
        if (m.updateTableSchema)
            transaction {
                SchemaUtils.createMissingTablesAndColumns(
                    Profiles
                )
            }
    }

    suspend fun <T> transaction(repetitions: Int = 5, transactionIsolation: Int = Connection.TRANSACTION_REPEATABLE_READ, statement: Transaction.() -> T): T = withContext(Dispatchers.IO) {
        val transactionIsolation = sanitizeTransaction(transactionIsolation)

        transaction(transactionIsolation, repetitions, lorittaDatabase) {
            statement.invoke(this)
        }
    }

    private fun sanitizeTransaction(transactionIsolation: Int) = if (m.type != DatabaseType.SQLITE && m.type != DatabaseType.SQLITE_MEMORY)
        transactionIsolation
    else // SQLite does not support a lot of transaction isolations (only TRANSACTION_READ_UNCOMMITTED and TRANSACTION_SERIALIZABLE)
        Connection.TRANSACTION_SERIALIZABLE
}