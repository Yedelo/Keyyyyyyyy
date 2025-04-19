import dev.deftu.gradle.utils.GameSide

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