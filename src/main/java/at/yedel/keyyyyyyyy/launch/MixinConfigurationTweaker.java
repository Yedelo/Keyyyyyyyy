/*? if forge {*/
package at.yedel.keyyyyyyyy.launch;



import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;



public class MixinConfigurationTweaker implements ITweaker {
    static {
        URL location = MixinConfigurationTweaker.class.getProtectionDomain().getCodeSource().getLocation();
        if (location != null && Objects.equals(location.getProtocol(), "file")) {
            try {
                MixinBootstrap.getPlatform().addContainer(location.toURI());
            }
            catch (URISyntaxException e) {
                KeyyyyyyyyTweaker.keylogger.fatal("Keyyyyyyyy could not add itself as a mixin container!", e);
            }
        }
    }

    @Override public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}
    @Override public void injectIntoClassLoader(LaunchClassLoader classLoader) {}
    @Override public String getLaunchTarget() { return ""; }
    @Override public String[] getLaunchArguments() { return new String[0]; }
}
/*?}*/
