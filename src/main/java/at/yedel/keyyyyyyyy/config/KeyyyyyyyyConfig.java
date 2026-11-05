package at.yedel.keyyyyyyyy.config;



public class KeyyyyyyyyConfig {
    private static final KeyyyyyyyyConfig INSTANCE = new KeyyyyyyyyConfig();

    public static KeyyyyyyyyConfig getInstance() {
        return INSTANCE;
    }

    private KeyyyyyyyyConfigImplementation implementation = new BasicImplementation();

    public boolean isEnabled() {
        return implementation.isEnabled();
    }

    public boolean isTargetEnabled(InjectionTarget target) {
        return implementation.isTargetEnabled(target);
    }

    public boolean shouldRepeat() {
        return implementation.shouldRepeat();
    }

    public void setImplementation(KeyyyyyyyyConfigImplementation implementation) {
        this.implementation = implementation;
    }
}
