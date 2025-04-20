package at.yedel.keyyyyyyyy.common;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public abstract class KeyyyyyyyyLogger {
    private static Loader loader;

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static void setLoader(Loader loader) {
        KeyyyyyyyyLogger.loader = loader;
    }

    private static final Logger logger = LogManager.getLogger("Keyyyyyyyy");

    public static void log(String message) {
        if (loader == Loader.FORGE) {
            logger.info(message);
        }
        else {
            System.out.println(getLogComponents("info") + message);
        }
    }

    public static void error(String message, Throwable throwable) {
        if (loader == Loader.FORGE) {
            logger.error(message, throwable);
        }
        else {
            System.err.println(getLogComponents("error") + message);
            throwable.printStackTrace();
        }
    }

    private static String getLogComponents(String channel) {
        return "[" + timeFormat.format(new Date()) + "]" + " [" + Thread.currentThread().getName() + "/" + channel.toUpperCase(Locale.ROOT) + "] [Keyyyyyyyy]: ";
    }

    public enum Loader {
        FORGE,
        FABRIC
    }
}
