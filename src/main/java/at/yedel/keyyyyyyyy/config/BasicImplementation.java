package at.yedel.keyyyyyyyy.config;



public class BasicImplementation implements KeyyyyyyyyConfigImplementation {
    private boolean enabled = true;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isTargetEnabled(InjectionTarget target) {
        return true;
    }

    @Override
    public boolean shouldRepeat() {
        return true;
    }
}
