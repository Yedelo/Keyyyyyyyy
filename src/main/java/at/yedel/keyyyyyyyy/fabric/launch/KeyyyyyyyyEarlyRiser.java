package at.yedel.keyyyyyyyy.fabric.launch;



import at.yedel.keyyyyyyyy.common.KeyyyyyyyyConstants;
import at.yedel.keyyyyyyyy.common.KeyyyyyyyyLogger;
import at.yedel.keyyyyyyyy.common.launch.KeyboardTransformer;
import com.chocohead.mm.api.ClassTinkerers;



public class KeyyyyyyyyEarlyRiser implements Runnable {
    @Override
    public void run() {
        KeyyyyyyyyLogger.setLoader(KeyyyyyyyyLogger.Loader.FABRIC);
        KeyyyyyyyyLogger.log("Starting Keyyyyyyyy " + KeyyyyyyyyConstants.MOD_VERSION + " (Fabric)");
        ClassTinkerers.addTransformation(KeyyyyyyyyConstants.KEYBOARD_CLASS, KeyboardTransformer.getInstance()::transform);
    }
}
