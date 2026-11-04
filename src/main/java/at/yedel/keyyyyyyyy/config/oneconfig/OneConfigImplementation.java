//? if fabric {
package at.yedel.keyyyyyyyy.config.oneconfig;



import at.yedel.keyyyyyyyy.config.KeyyyyyyyyConfig;
import at.yedel.keyyyyyyyy.config.KeyyyyyyyyConfigImplementation;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch;



// we will not apologize for the length of this line
public class OneConfigImplementation extends Config implements KeyyyyyyyyConfigImplementation {
    public static final OneConfigImplementation INSTANCE = new OneConfigImplementation();

    public static OneConfigImplementation getInstance() {
        return INSTANCE;
    }

    public OneConfigImplementation() {
        super("keyyyyyyyy.json", "assets/keyyyyyyyy/keyyyyyyyy.png", "Keyyyyyyyy", Category.QOL);
        KeyyyyyyyyConfig.getInstance().setImplementation(this);
    }

    @Switch(
        title = "Enabled",
        description = "Global toggle for the mod."
    )
    public boolean enabled = true;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        save();
    }

    @Override
    public boolean shouldRepeat() {
        return true;
    }
}
//?}
