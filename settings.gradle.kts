import kotlin.reflect.KProperty

rootProject.name = "DreamersDeluxe"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://maven.deftu.dev/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.deftu.dev/snapshots")
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}


plugins {
    id("dev.kikugie.stonecutter") version "0.10-alpha.6"
    id("dev.kikugie.loom-back-compat") version "0.2"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        fun registerProject(versionString: String, loader: String) {
            version("$versionString-$loader", versionString).buildscript("build.$loader.gradle.kts")
        }

        registerProject("1.8.9", "forge")
        registerProject("1.8.9", "ornithe")
        vcsVersion = "1.8.9-forge"
    }
}