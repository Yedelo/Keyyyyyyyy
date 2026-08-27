import kotlin.reflect.KProperty
import kotlin.text.replace

plugins {
    id("dev.kikugie.stonecutter")
    id("me.modmuss50.mod-publish-plugin") version "2.1.1"
}

val modrinthLogoLink: String by project

stonecutter active "1.8.9-forge"

stonecutter parameters {
    val loader = current.project.split("-")[1]

    constants {
        match(loader, "forge", "ornithe")
    }

    replacements {

    }

    val shared = mutableMapOf<String, Any?>()
    extra[current.project] = shared

    class Declare<T>(private val value: T) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): Declare<T> {
            shared[property.name] = value
            return this
        }

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value
    }
    val fabricLoaderVersion by Declare(properties.getAs<String>("versions.fabricloader"))

    val modName by Declare(extra["mod.name"])
    val modId by Declare(extra["mod.id"])
    val modDescription by Declare(extra["mod.description"])
    val modIcon by Declare(extra["modIcon"])

    val rangedVersion by Declare(properties.getAs<String>("versioning") == "range")
    val maxMc by Declare(if (rangedVersion) properties.getAs<String>("mc.max") else null)

    val finalFileName by Declare("${modName}-$version+$loader.jar")

    val modrinthReadme by Declare(rootProject.file("README.md").readText()
        .replace("src/main/resources/${modIcon}", modrinthLogoLink)
    )
}