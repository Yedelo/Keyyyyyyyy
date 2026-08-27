import dev.deftu.gradle.utils.GameSide
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.java
import org.jetbrains.kotlin.gradle.internal.builtins.StandardNames.FqNames.target
import kotlin.reflect.KProperty

// in stonecutter.gradle.kts
class CommonProperty<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = (rootProject.extra[sc.current.project] as Map<String, Any?>)[property.name] as T
}

val license: String by project
val javaVersion = JavaVersion.VERSION_25
val modName by CommonProperty<String>()
val modId by CommonProperty<String>()
val modDescription by CommonProperty<String>()
val modIcon by CommonProperty<String>()
val rangedVersion by CommonProperty<Boolean>()
val maxMc by CommonProperty<String>()
val minecraftTarget by CommonProperty<String>()
val finalFileName by CommonProperty<String>()

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://repo.essential.gg/repository/maven-public")
    maven("https://maven.deftu.dev/releases")
    maven("https://maven.fabricmc.net")
    maven("https://maven.architectury.dev")
    maven("https://maven.minecraftforge.net")
    maven("https://maven.deftu.dev/snapshots")
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
    implementation("org.spongepowered:mixin:0.7.11-SNAPSHOT")
    shadeNonTransitive("org.spongepowered:mixin:0.7.11-SNAPSHOT")
}

toolkitLoomHelper {
    disableRunConfigs(GameSide.SERVER)

    useTweaker("at.yedel.keyyyyyyyy.launch.KeyyyyyyyyTweaker")
    useForgeMixin(modId)
    useMixinRefMap("$modId.refmap")

    useDevAuth(sc.properties.getAs<String>("versions.devauth"))
    useArgument("--version", modName, GameSide.BOTH)
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
        val props = buildMap {
            register("modName", modName)
            register("modId", modId)
            register("modDescription", modDescription)
            register("modIcon", modIcon)
            register("version", version.toString())
        }
        filesMatching(listOf("mcmod.info", "mixins.$modId.json")) { expand(props) }

        outputs.upToDateWhen { false }
    }

    register<Copy>("buildAndCollect") {
        group = "build"

        from(remapJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs"))
        dependsOn("build")
    }

    remapJar {
        archiveFileName = finalFileName
        manifest.attributes(
            mapOf(
                "ModSide" to "CLIENT",
            )
        )
    }
}