package at.yedel.keyyyyyyyy.config;



public class KeyyyyyyyyConfig {
    private static final KeyyyyyyyyConfig INSTANCE = new KeyyyyyyyyConfig();

    public static KeyyyyyyyyConfig getInstance() {
        return INSTANCE;
    }

    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
