package backend.drivor.base.domain.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final LoggerUtil instance = new LoggerUtil();


    /**
     * Info log
     *
     * @param tag
     * @param log
     */
    public static void i(String tag, String log) {
        instance.logger.info(String.format("%s: %s", tag, log));
    }

    /**
     * Debug log
     *
     * @param tag
     * @param log
     */
    public static void d(String tag, String log) {
        instance.logger.debug(String.format("%s: %s", tag, log));
    }

    /**
     * Error log
     *
     * @param tag
     * @param log
     */
    public static void e(String tag, String log) {
        instance.logger.error(String.format("%s: %s", tag, log));
    }

    /**
     * Warning log
     *
     * @param tag
     * @param log
     */
    public static void w(String tag, String log) {
        instance.logger.warn(String.format("%s: %s", tag, log));
    }

    /**
     * handle exception
     *
     * @param tag
     * @param ex
     */
    public static void exception(String tag, Exception ex) {
        if (ex != null) {
            e(tag, ex.getMessage());
        }
    }

}
