package at.yedel.keyyyyyyyy.launch;



import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinTweaker;

import java.io.*;
import java.util.List;
import java.util.Objects;



public class KeyyyyyyyyTweaker implements ITweaker {
    public static final Logger keylogger = LogManager.getLogger("KeyyyyyyyyTweaker");
    @SuppressWarnings("unchecked")
    private static final List<String> TweakClasses = (List<String>) Launch.blackboard.get("TweakClasses");

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        queueTweaker("Mixin", MixinTweaker.class.getName());
    }

    private void queueTweaker(String name, String className) {
        // example: name = Mixin -> propertyName = "keyyyyyyyy.launch.mixin"
        String propertyName = "keyyyyyyyy.launch." + name.toLowerCase().replace(" ", "-");
        String propertyValue = System.getProperty(propertyName);
        if (!Objects.equals(propertyValue, "false")) {
            keylogger.info("Queueing {} tweaker {}", name, className);
            TweakClasses.add(className);
        }
        else {
            keylogger.warn("Skipping queueing of {} tweaker {}, property {} = false!", name, className, propertyName);
        }
    }

    @Override public void injectIntoClassLoader(LaunchClassLoader classLoader) {}
    @Override public String getLaunchTarget() { return ""; }
    @Override public String[] getLaunchArguments() { return new String[0]; }
}