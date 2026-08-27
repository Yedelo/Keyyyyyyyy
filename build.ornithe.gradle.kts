import org.gradle.api.tasks.Copy
import org.gradle.internal.Actions.set
import org.gradle.kotlin.dsl.invoke

val modDescription: String = extra["mod.description"] as String
val license: String by project
val fabricLoaderVersion = sc.properties.getAs<String>("versions.fabricloader")

val loader = sc.current.project.split("-")[1]
val rangedVersion = sc.properties.getAs<String>("versioning") == "range"
val maxMc = if (rangedVersion) sc.properties.getAs<String>("mc.max") else null

repositories {
    fun scopedMaven(url: String, vararg groups: String, includeSubgroups: Boolean = false) = maven(url) {
        content { for (group in groups) if (!includeSubgroups) includeGroup(group) else includeGroupAndSubgroups(group) }
    }

    mavenCentral()
    gradlePluginPortal()
    google()
    maven("https://repo.polyfrost.org/releases")
    maven("https://repo.polyfrost.org/snapshots")
    maven("https://maven.terraformersmc.com/releases")
    maven("https://repo.hypixel.net/repository/Hypixel/")
    maven("https://maven.fabricmc.net/releases")
    scopedMaven("https://central.sonatype.com/repository/maven-snapshots/", "net.kyori")
    maven("https://api.modrinth.com/maven") {
        content { includeGroup("maven.modrinth") }
    }
}

plugins {
    id("net.fabricmc.fabric-loom") version "1.16-SNAPSHOT"
}

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
}

loom {
    runConfigs.remove(runConfigs["server"])

    runConfigs.all {
        runDir = "../../run"
        val resourcePackDir: String? = System.getenv("minecraft.resourcePackDir")
        if (!resourcePackDir.isNullOrBlank()) {
            println("Using resource pack directory $resourcePackDir from environment variable minecraft.resourcePackDir")
            programArgs("--resourcePackDir", resourcePackDir)
        }
    }
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, value: String) {
            inputs.property(key, value)
            set(key, value)
        }
        fun target(version: String) = ">=$version"

        exclude("mcmod.info")
        exclude("mixins.legacy.keyyyyyyyy.json")
        val props = buildMap {
            register("description", modDescription)
            register("license", license)
            register("version", version.toString())
            register("java", target(javaVersion.majorVersion))
            register("fabricLoader", target(fabricLoaderVersion))
            val minecraftDependency =
                if (rangedVersion) ">=${sc.current.version} <=${maxMc}" else sc.current.version
            register("minecraft", minecraftDependency)
        }
        filesMatching(listOf("fabric.mod.json")) { expand(props) }

        val mixinJava = "JAVA_${javaVersion.majorVersion}"
        filesMatching("mixins.modern.keyyyyyyyy.json") { expand("mixinJava" to mixinJava, "mixinMin" to "0.8") }

        outputs.upToDateWhen { false }
    }

    register<Copy>("buildAndCollect") {
        group = "build"

        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs"))
        dependsOn("build")
    }

    jar {
        val minecraftTarget = if (rangedVersion) "${sc.current.version}-$maxMc" else sc.current.version
        val finalFileName = "Keyyyyyyyy-$version+$minecraftTarget-$loader.jar"
        archiveFileName = finalFileName
        // manifest.attributes(mapOf())
    }
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}