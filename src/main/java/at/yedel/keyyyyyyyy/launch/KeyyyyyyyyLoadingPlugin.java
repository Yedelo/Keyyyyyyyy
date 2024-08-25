package at.yedel.keyyyyyyyy.launch;



import java.lang.reflect.Field;
import java.util.Map;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Name("KeyyyyyyyyLoadingPlugin")
public class KeyyyyyyyyLoadingPlugin implements IFMLLoadingPlugin {
	public static final String version = "#version#";
	public static final Logger keyyyyyyyy = LogManager.getLogger("Keyyyyyyyy");

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
			MinecraftTransformer.class.getName()
		};
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
		// This mod compiles with Forge for 1.8.9 so the field ForgeVersion.mcVersion gets simplified to simply "1.8.9" when compiled
		// This does something to the string to make it get the field instead of a constant
		String mcVersion = "unknown version";
		try {
			Class $ForgeVersion = Class.<ForgeVersion>forName("net.minecraftforge.common.ForgeVersion");
			Field $mcVersion = $ForgeVersion.getField("mcVersion");
			mcVersion = (String) $mcVersion.get(null);
		} catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		keyyyyyyyy.info("Starting Keyyyyyyyy {} for Minecraft {}", version, mcVersion);
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
