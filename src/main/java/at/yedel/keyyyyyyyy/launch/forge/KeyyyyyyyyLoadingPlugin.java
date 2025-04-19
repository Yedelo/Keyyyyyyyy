package at.yedel.keyyyyyyyy.launch.forge;



import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import at.yedel.keyyyyyyyy.launch.KeyyyyyyyyConstants;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.filter.RegexFilter;

import static at.yedel.keyyyyyyyy.launch.KeyyyyyyyyConstants.keyyyyyyyy;



@Name("KeyyyyyyyyLoadingPlugin")
public class KeyyyyyyyyLoadingPlugin implements IFMLLoadingPlugin {
	static {
		unlockLwjgl();
	}

	// Taken from LWJGLTwoPointFive
	// https://github.com/DJtheRedstoner/LWJGLTwoPointFive/blob/master/src/main/java/me/djtheredstoner/lwjgl/plugin/LoadingPlugin.java#L13
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

		silenceLog4j();
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
			keyyyyyyyy.error("Failed to silence log4j!", e);
		}
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {KeyboardClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> map) {
		keyyyyyyyy.info("Starting Keyyyyyyyy {} (Forge)", KeyyyyyyyyConstants.MOD_VERSION);
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
