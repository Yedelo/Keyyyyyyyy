# **3.0.0**

- Rewrote the mod entirely
  - Rewrote ASM to Mixins
  - Mod can now be toggled programatically with `at.yedel.keyyyyyyyy.config.KeyyyyyyyyConfig`
  - Mod should now work on Forge versions before 1.8 (cpw.mods)
  - New tweaker system: Mixin loading and LWJGL unlocking can now be disabled with system properties
    - `keyyyyyyyy.launch.mixin = false`
    - `keyyyyyyyy.launch.lwjgl-unlocking = false`
- Added support with Spice (LWJGL 3)