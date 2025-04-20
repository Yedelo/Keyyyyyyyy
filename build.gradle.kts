import dev.deftu.gradle.utils.GameSide
import dev.deftu.gradle.utils.version.MinecraftVersions

val fabricAsmVersion: String by project
val devAuthVersion: String by project

plugins {
    for (tool in listOf(
        "multiversion",
        "tools",
        "tools.java",
        "tools.minecraft.loom",
        "tools.ducks",
        "tools.bloom",
        "tools.resources"
    )) {
        id("dev.deftu.gradle.$tool")
    }
}

dependencies {
    // legacy fabric does not incldue this by default. courtesy of deftu
    // TODO make this not constant. these two versions work fine but it would be nice to be able to get the log4j version
    if (mcData.isLegacyFabric) {
        implementation("org.apache.logging.log4j:log4j-core:${
            if (mcData.version >= MinecraftVersions.VERSION_1_12) "2.0-beta9" else "2.8.1"
        }")
    }
    // This looks scary, why are we including Fabric-ASM in forge versions too?
    // Fabric-ASM doesn't make any breaking changes to the forge versions, the included jar is not touched by forge, and it is needed to compile
    listOf("modImplementation", "include").forEach {it("com.github.Chocohead:Fabric-ASM:$fabricAsmVersion")}
}

toolkitLoomHelper {
    disableRunConfigs(GameSide.SERVER)
    // This also looks scary, but is only a system property and manifest attribute. Also not touched by Fabric.
    useCoreMod("at.yedel.keyyyyyyyy.forge.launch.KeyyyyyyyyLoadingPlugin")

    useDevAuth(devAuthVersion)
    useArgument("--version", mcData.version.toString(), GameSide.CLIENT)
    val resourcePackDir: String? = System.getenv("minecraft.resourcePackDir")
    if (!resourcePackDir.isNullOrBlank()) {
        println("Using resource pack directory $resourcePackDir from environment variable minecraft.resourcePackDir")
        useArgument("--resourcePackDir", resourcePackDir, GameSide.CLIENT)
    }
}

toolkitMultiversion {
    // build/versions/
    moveBuildsToRootProject.set(true)
}

tasks {
    jar {
        manifest.attributes(mapOf(
            "ModSide" to "CLIENT"
        ))
    }
}