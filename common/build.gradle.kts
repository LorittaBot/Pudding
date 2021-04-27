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

    // Kotlinx Serialization
    api("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.KOTLIN_SERIALIZATION}")
}

publishing {
    publications {
        register("PerfectDreams", MavenPublication::class.java) {
            from(components["java"])
        }
    }
}