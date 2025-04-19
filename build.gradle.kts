import dev.deftu.gradle.utils.GameSide
import dev.deftu.gradle.utils.version.MinecraftVersions

val devAuthVersion: String by project

plugins {
    for (tool in listOf(
        "multiversion",
        "tools",
        "tools.java",
        "tools.minecraft.loom",
        "tools.bloom",
        "tools.resources"
    )) {
        id("dev.deftu.gradle.$tool")
    }
}

dependencies {
    if (mcData.isLegacyFabric) {
        implementation("org.apache.logging.log4j:log4j-core:${
            if (mcData.version >= MinecraftVersions.VERSION_1_12) "2.0-beta9" else "2.8.1"
        }")
    }
}

toolkitLoomHelper {
    disableRunConfigs(GameSide.SERVER)
    if (mcData.isLegacyForge) {
        useCoreMod("at.yedel.keyyyyyyyy.launch.forge.KeyyyyyyyyLoadingPlugin")
    }

    useDevAuth(devAuthVersion)
    useArgument("--version", "Keyyyyyyyy", GameSide.CLIENT)
    val resourcePackDir: String? = System.getenv("minecraft.resourcePackDir")
    if (!resourcePackDir.isNullOrBlank()) {
        println("Using resource pack directory $resourcePackDir from environment variable minecraft.resourcePackDir")
        useArgument("--resourcePackDir", resourcePackDir, GameSide.CLIENT)
    }
}

tasks {
    jar {
        manifest.attributes(mapOf(
            "ModSide" to "CLIENT"
        ))
    }
}