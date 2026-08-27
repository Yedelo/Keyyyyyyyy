import dev.deftu.gradle.utils.GameSide
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.java

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://repo.spongepowered.org/repository/maven-public")
}

plugins {
    java
    val dgt = "2.73.0"
    id("dev.deftu.gradle.tools") version dgt
    for (tool in listOf(
        "java",
        "minecraft.loom",
        "bloom",
        "resources",
        "shadow"
    )) id("dev.deftu.gradle.tools.$tool") version dgt
}

dependencies {

}

toolkitLoomHelper {
    disableRunConfigs(GameSide.SERVER)

    useTweaker("at.yedel.keyyyyyyyy.launch.KeyyyyyyyyTweaker")
    useForgeMixin("legacy.keyyyyyyyy")
    useMixinRefMap("legacy.keyyyyyyyy.refmap")

    useDevAuth(sc.properties.getAs<String>("versions.devauth"))
    useArgument("--version", "Keyyyyyyyy", GameSide.BOTH)
    val resourcePackDir: String? = System.getenv("minecraft.resourcePackDir")
    if (!resourcePackDir.isNullOrBlank()) {
        println("Using resource pack directory $resourcePackDir from environment variable minecraft.resourcePackDir")
        useArgument("--resourcePackDir", resourcePackDir, GameSide.BOTH)
    }
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, value: String) {
            inputs.property(key, value)
            set(key, value)
        }
        exclude("fabric.mod.json")
        exclude("mixins.modern.keyyyyyyyy.json")
        filesMatching("mixins.legacy.keyyyyyyyy.json") { expand("mixinJava" to "JAVA_8", "mixinMin" to "0.7.11") }

        outputs.upToDateWhen { false }
    }

    register<Copy>("buildAndCollect") {
        group = "build"

        from(remapJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs"))
        dependsOn("build")
    }

    jar {
        archiveFileName = "Keyyyyyyyy-$version+${mcData}.jar"
        manifest.attributes(
            mapOf(
                "ModSide" to "CLIENT",
            )
        )
    }
}