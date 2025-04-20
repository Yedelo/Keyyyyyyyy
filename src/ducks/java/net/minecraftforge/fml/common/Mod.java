package net.minecraftforge.fml.common;



public @interface Mod {
    String modid();
    String name();
    String version();
    boolean clientSideOnly();
}
