package net.perfectdreams.loritta.pudding.server

import com.typesafe.config.ConfigFactory
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import net.perfectdreams.loritta.pudding.server.utils.config.PuddingConfig
import java.io.File

object PuddingLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val puddingConfigFile = File("./pudding.conf")

        if (!puddingConfigFile.exists()) {
            println("Welcome to Pudding, Loritta Morenitta's REST API! :3")
            println("")
            println("Before we start, you will need to configure me.")
            println("Configurations files were created in the application folder, open it on your favorite text editor and change it!")
            println("")
            println("After configuring the file, run me again!")

            copyFromJar("/pudding.conf", "./pudding.conf")
            return
        }

        val config = Hocon.decodeFromConfig<PuddingConfig>(ConfigFactory.parseFile(puddingConfigFile))

        Pudding(config).start()
    }

    private fun copyFromJar(inputPath: String, outputPath: String) {
        val inputStream = PuddingLauncher::class.java.getResourceAsStream(inputPath)
        File(outputPath).writeBytes(inputStream.readAllBytes())
    }
}