package at.yedel.keyyyyyyyy.config;



public class BasicImplementation implements KeyyyyyyyyConfigImplementation {
    private boolean enabled = true;

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
