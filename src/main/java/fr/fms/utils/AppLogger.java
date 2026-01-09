package fr.fms.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple console logger.
 *
 * Provides a few log levels with timestamps & colors:
 * - info
 * - error
 * - ok
 * - rocket (because why not 🚀)
 *
 * This is intentionally lightweight: no frameworks, no configuration,
 * just logs that help you understand what you do.
 */
public final class AppLogger {

    /** Prevent instantiation. */
    private AppLogger() {
    }

    /** Time format for log prefix. */
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    /** Verbose mode flag. */
    private static boolean verbose = Boolean.parseBoolean(System.getProperty("verbose", "false"));

    /**
     * Enables or disables verbose logging.
     *
     * @param enabled true to enable verbose logs, false to disable
     */
    public static void setVerbose(boolean enabled) {
        verbose = enabled;
    }

    /**
     * @return true if verbose logs are enabled
     */
    public static boolean isVerbose() {
        return verbose;
    }

    /**
     * Prints an INFO message to stdout.
     *
     * @param msg message to log
     */
    public static void info(String msg) {
        if (!verbose)
            return;
        System.out.println(
                "[" + LocalTime.now().format(FMT) + "] " + Helpers.CYAN + "INFO " + Helpers.RESET + " : " + msg);
    }

    /**
     * Prints an ERROR message to stderr.
     *
     * @param msg message to log
     */
    public static void error(String msg) {
        System.err.println(
                "[" + LocalTime.now().format(FMT) + "] " + Helpers.RED + "ERROR" + Helpers.RESET + " : " + msg);
    }

    /**
     * Prints an INFO message.
     *
     * @param msg message to log
     */
    public static void rocket(String msg) {
        if (!verbose)
            return;
        info("🚀 " + msg);
    }

    /**
     * Prints an OK message to stdout.
     *
     * @param msg message to log
     */
    public static void ok(String msg) {
        if (!verbose)
            return;
        System.out.println(
                "[" + LocalTime.now().format(FMT) + "] " + Helpers.GREEN + "✅" + Helpers.RESET + " : " + msg);
    }

    /**
     * Logs an exception with its type and message.
     * To keeps console output readable while still useful.
     *
     * @param msg context message
     * @param e   exception to log
     */
    public static void exception(String msg, Exception e) {
        error(msg + " (" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")");
    }
}
