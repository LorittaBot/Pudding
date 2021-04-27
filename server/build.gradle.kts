import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version Versions.KOTLIN
    kotlin("plugin.serialization") version Versions.KOTLIN
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))

    // Ktor
    implementation("io.ktor:ktor-server-netty:${Versions.KTOR}")

    // Sequins
    api("net.perfectdreams.sequins.ktor:base-route:1.0.2")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:${Versions.EXPOSED}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${Versions.EXPOSED}")

    // Database Drivers
    api("org.postgresql:postgresql:42.2.18")
    api("com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.6")
    api("org.xerial:sqlite-jdbc:3.32.3.2")
    api("com.zaxxer:HikariCP:4.0.3")

    // Kotlinx Serialization
    api("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.KOTLIN_SERIALIZATION}")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION}")
    api("org.jetbrains.kotlinx:kotlinx-serialization-hocon:${Versions.KOTLIN_SERIALIZATION}")

    // Async Appender is broken in alpha5
    // https://stackoverflow.com/questions/58742485/logback-error-no-attached-appenders-found
    api("ch.qos.logback:logback-classic:1.3.0-alpha4")
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "net.perfectdreams.loritta.pudding.server.PuddingLauncher"
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}