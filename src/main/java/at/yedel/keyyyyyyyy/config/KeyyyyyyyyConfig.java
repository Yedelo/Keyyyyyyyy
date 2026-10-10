package at.yedel.keyyyyyyyy.config;



public class KeyyyyyyyyConfig {
    private static final KeyyyyyyyyConfig INSTANCE = new KeyyyyyyyyConfig();

    public static KeyyyyyyyyConfig getInstance() {
        return INSTANCE;
    }

    private final KeyyyyyyyyConfigImplementation implementation = new BasicImplementation();

    public boolean isEnabled() {
        return implementation.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        implementation.setEnabled(enabled);
    }
}
