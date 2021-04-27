plugins {
    kotlin("jvm") version Versions.KOTLIN
    kotlin("plugin.serialization") version Versions.KOTLIN
}

repositories {
    mavenCentral()
    maven("https://repo.perfectdreams.net/")
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":common"))

    // Ktor
    implementation("io.ktor:ktor-client-cio:${Versions.KTOR}")

    // Kotlinx Serialization
    api("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.KOTLIN_SERIALIZATION}")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION}")

    // Async Appender is broken in alpha5
    // https://stackoverflow.com/questions/58742485/logback-error-no-attached-appenders-found
    api("ch.qos.logback:logback-classic:1.3.0-alpha4")
}

publishing {
    publications {
        register("PerfectDreams", MavenPublication::class.java) {
            from(components["java"])
        }
    }
}