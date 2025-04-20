package net.minecraftforge.fml.relauncher;



import java.util.Map;



public interface IFMLLoadingPlugin {
    String[] getASMTransformerClass();
    String getModContainerClass();
    String getSetupClass();
    void injectData(Map<String, Object> map);
    String getAccessTransformerClass();

    @interface Name {
        String value();
    }
}
