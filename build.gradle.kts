import dev.architectury.pack200.java.Pack200Adapter

plugins {
    kotlin("jvm")
    id("gg.essential.loom")
    id("io.github.juuxel.loom-quiltflower")
    id("dev.architectury.architectury-pack200")
    id("com.github.johnrengelman.shadow")
    id("net.kyori.blossom") version "1.3.1"
}

version = properties["mod_version"]!!

repositories {
    maven("https://repo.essential.gg/repository/maven-public")
    maven("https://repo.spongepowered.org/repository/maven-public")
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")
}

loom {
    runConfigs {
        named("client") {
            ideConfigGenerated(true)
        }
    }

    launchConfigs {
        getByName("client") {
            property("fml.coreMods.load", "at.yedel.keyyyyyyyy.launch.KeyyyyyyyyLoadingPlugin")
        }
    }

    forge {
        pack200Provider.set(Pack200Adapter())
    }
}

blossom {
    replaceTokenIn("src/main/java/at/yedel/keyyyyyyyy/launch/KeyyyyyyyyLoadingPlugin.java")
    replaceTokenIn("src/main/java/at/yedel/keyyyyyyyy/Keyyyyyyyy.java")
    replaceToken("#version#", version)
}

tasks {
    jar {
        manifest.attributes(
            mapOf(
                "FMLCorePlugin" to "at.yedel.keyyyyyyyy.launch.KeyyyyyyyyLoadingPlugin",
                "FMLCorePluginContainsFMLMod" to "yes, true, correct, indeed, positive, 1",
                "ForceLoadAsMod" to "true",
                "ModSide" to "CLIENT"
            )
        )
    }

    processResources {
        filesMatching("mcmod.info") {
            expand("version" to version)
        }
    }

    withType<JavaCompile> {
        options.release.set(8)
    }
}