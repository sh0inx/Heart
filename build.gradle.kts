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
    maven("https://jitpack.io")
}

dependencies {

    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")
    implementation("org.yaml:snakeyaml:2.0")

    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("com.iridium:IridiumColorAPI:1.0.6")
    implementation("de.tr7zw:item-nbt-api:2.12.0")

    compileOnly("org.projectlombok:lombok:1.18.26")
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")

    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

tasks {
    jar {
        dependsOn("shadowJar")
        enabled = false
    }

    compileJava {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }

    processResources {
        filesMatching("**/plugin.yml") {
            expand(rootProject.project.properties)
        }

        outputs.upToDateWhen { false }
    }

    shadowJar {
        fun relocate(origin: String) =
                relocate(origin, "io.github.sh0inx.dependencies${origin.substring(origin.lastIndexOf('.'))}")

        archiveClassifier.set("")

        relocate("org.bstats")
        relocate("com.iridium")
        relocate("de.tr7zw.changeme.nbtapi")

        minimize()
    }
}