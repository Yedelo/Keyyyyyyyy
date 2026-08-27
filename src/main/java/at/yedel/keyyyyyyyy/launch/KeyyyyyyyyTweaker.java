/*? if forge {*/
package at.yedel.keyyyyyyyy.launch;



import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.List;



public class KeyyyyyyyyTweaker implements ITweaker {
    static {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.keyyyyyyyy.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}
    @Override public void injectIntoClassLoader(LaunchClassLoader classLoader) {}
    @Override public String getLaunchTarget() { return ""; }
    @Override public String[] getLaunchArguments() { return new String[0]; }
}
/*?}*/
