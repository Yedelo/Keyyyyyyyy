# Keyyyyyyyy

![keyyyyyyyy logo](src/main/resources/assets/keyyyyyyyy/logo.png)

![Badge](https://img.shields.io/badge/discord-yedel-blue)

![GitHub Release](https://img.shields.io/github/v/release/Yedelo/Keyyyyyyyy?include_prereleases&label=GitHub%20version)

![Modrinth Version](https://img.shields.io/modrinth/v/KMtOZMrU?label=Modrinth%20version)

Enables keyboard repeat events on versions before 1.13

## Supported Versions

| **Minecraft version & loader**             | **Tested** | **Should work** |
|--------------------------------------------|------------|-----------------|
| 1.12.2 (Forge & Fabric)                    | ✅          | ✅               |
| 1.11.2 (Forge & Fabric)                    | ✅          | ✅               |
| 1.10.2 (Forge & Fabric)                    | ✅          | ✅               |
| 1.9.4 (Forge)                              | ✅          | ✅               |
| 1.9.4 (Fabric)                             | ❌          | ✅               |
| 1.8.9 (Forge & Fabric)                     | ✅          | ✅               |
| Other versions 1.8-1.12.2 (Forge & Fabric) | ✅          | ✅               |
| Versions before 1.8 (Forge)                | ❌          | ❌               |
| Versions before 1.8 (Fabric)               | ❌          | ✅               |

## Developing

This mod is a pretty messy one-jar project.

### Ducks source set

This source set contains LaunchWrapper and Forge classes so that Fabric versions can compile.
We don't need this the other way around because the Fabric part of this mod doesn't use any Fabric classes.

### Packages

Within `at.yedel.keyyyyyyyy` are 3 packages: `common`, `fabric`, and `forge`. 
These are simply to organize the classes; there is no special logic for them.

### Transformations

The mod is done through 3 transformations to Keyboard:

- Transforming `void enableRepeatEvents(boolean enable)` so that `enable` is always true
- Transforming `boolean areRepeatEventsEnabled()` so that the result is always true
- Transforming `boolean isRepeatEvent` so that the result is always false

All three transformations are probably not needed (especially `areRepeatEventsEnabled`; not used in vanilla) but it is just better to do.
Transformations are applied with raw ASM instead of Mixins, as supporting mixins is somewhat complicated in legacy forge.
In Forge, ASM is used with a coremod / loading plugin, while in Fabric, ASM is used with Chocohead's [Fabric-ASM](https://github.com/Chocohead/Fabric-ASM/tree/master) which is bundled with the mod.

### Run configs / multiversion

Run configs are set up for the following versions of Forge and Fabric:
- 1.12.2
- 1.11.2
- 1.10.2
- 1.9.4
- 1.8.9

Other versions are supported, but versions below 1.8.9 don't work with loom, 
some forge/fabric versions don't exist and there are preprocessor graph issues.

For Forge you should use Java 8 and for Fabric you should use Java 17?/21.

As this mod has multiple projects, jars will be built for all versions. They appear almost identical, but they have key differences.
The published jar will always be from 1.8.9-fabric, although any fabric jar should work.

| Jar type   | Has mcmod.info | Has fabric.mod.json | Has Fabric-ASM properly bundled |
|------------|----------------|---------------------|---------------------------------|
| forge      | ✅              | ✅                   | ❌                               |
| forge-dev  | ✅              | ❌                   | ❌                               |
| **fabric** | ✅              | ✅                   | ✅                               |
| fabric-dev | ❌              | ✅                   | ❌                               |
