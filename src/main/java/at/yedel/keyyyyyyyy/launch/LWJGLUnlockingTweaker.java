/*? if forge {*/
package at.yedel.keyyyyyyyy.launch;



import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.filter.RegexFilter;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;



public class LWJGLUnlockingTweaker implements ITweaker {
    static {
        unlockLwjgl();
        silenceLog4j();
    }
    // Taken from LWJGLTwoPointFive
    // https://github.com/DJtheRedstoner/LWJGLTwoPointFive/blob/master/src/main/java/me/djtheredstoner/lwjgl/plugin/LoadingPlugin.java#L13
    @SuppressWarnings("unchecked")
    private static void unlockLwjgl() {
        boolean successfullyRemoved;
        try {
            Field $classLoaderExceptions = LaunchClassLoader.class.getDeclaredField("classLoaderExceptions");
            $classLoaderExceptions.setAccessible(true);
            Object classLoaderExceptions = $classLoaderExceptions.get(Launch.classLoader);
            successfullyRemoved = ((Set<String>) classLoaderExceptions).remove("org.lwjgl.");
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Couldn't unlock LWJGL!", e);
        }
        if (!successfullyRemoved) {
            throw new RuntimeException("Couldn't unlock LWJGL!");
        }
    }

    // Taken from PolyPatcher
    // https://github.com/Polyfrost/PolyPatcher/blob/main/src/main/java/club/sk1er/patcher/tweaker/PatcherTweaker.java#L113
    private static void silenceLog4j() {
        try {
            org.apache.logging.log4j.core.Logger logger = ((LoggerContext) LogManager.getContext(Launch.class.getClassLoader(), false)).getLogger("LaunchWrapper");

            // because archloom updates log4j, we must support both log4j 2.0-beta9 and 2.8.1
            // yedelnote: this stays the same from 1.8 to 1.12.2, no in-between versions
            RegexFilter filter = null;
            for (Method method: RegexFilter.class.getMethods()) {
                if (Objects.equals(method.getName(), "createFilter")) {
                    if (method.getParameterCount() == 5) {
                        filter = (RegexFilter) method.invoke(null,
                            "The jar file .* has a security seal for path .*, but that path is defined and not secure",
                            null, false, Filter.Result.DENY, Filter.Result.NEUTRAL);
                    }
                    else if (method.getParameterCount() == 4) {
                        filter = (RegexFilter) method.invoke(null,
                            "The jar file .* has a security seal for path .*, but that path is defined and not secure",
                            "false", "deny", "neutral");
                    }
                    else {
                        throw new IllegalStateException("Unknown createFilter arity " + method);
                    }
                    break;
                }
            }

            if (filter == null) {
                throw new IllegalStateException("Couldn't find createFilter method");
            }

            logger.addFilter(filter);
        }
        catch (Exception e) {
            KeyyyyyyyyTweaker.keylogger.error("Failed to silence log4j!", e);
        }
    }

    @Override public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}
    @Override public void injectIntoClassLoader(LaunchClassLoader classLoader) {}
    @Override public String getLaunchTarget() { return ""; }
    @Override public String[] getLaunchArguments() { return new String[0]; }
}
/*?}*/