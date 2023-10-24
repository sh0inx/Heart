plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.github.sh0inx"
version = "1.0"
description = "Heart"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://hub.jeff-media.com/nexus/repository/jeff-media-public/")

}

dependencies {

    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("com.iridium:IridiumColorAPI:1.0.6")
    implementation("de.tr7zw:item-nbt-api:2.12.0")
    implementation("com.jeff_media:MorePersistentDataTypes:2.3.1")
    implementation("ch.jalu:configme:1.4.1")

    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
}

tasks {
    jar {
        dependsOn("shadowJar")
        enabled = false
    }

    shadowJar {
        fun relocate(origin: String) =
                relocate(origin, "io.github.sh0inx.dependencies${origin.substring(origin.lastIndexOf('.'))}")

        archiveClassifier.set("")

        relocate("org.bstats")
        relocate("de.tr7zw.changeme")

        minimize()
    }
}
