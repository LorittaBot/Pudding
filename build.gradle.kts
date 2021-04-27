plugins {
    `maven-publish`
}

group = "net.perfectdreams.loritta.pudding"
version = Versions.PUDDING

subprojects {
    apply<MavenPublishPlugin>()

    repositories {
        mavenCentral()
        maven("https://repo.perfectdreams.net/")
    }

    group = "net.perfectdreams.loritta.pudding"
    version = Versions.PUDDING

    publishing {
        repositories {
            maven {
                name = "PerfectDreams"
                url = uri("https://repo.perfectdreams.net/")

                credentials {
                    username = System.getProperty("USERNAME") ?: System.getenv("USERNAME")
                    password = System.getProperty("PASSWORD") ?: System.getenv("PASSWORD")
                }
            }
        }
    }
}