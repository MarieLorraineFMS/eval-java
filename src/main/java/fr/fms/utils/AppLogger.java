package fr.fms.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class AppLogger {
    private AppLogger() {
    }

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    // INFO LOG
    public static void info(String msg) {
        System.out.println(
                "[" + LocalTime.now().format(FMT) + "] " + Helpers.CYAN + "INFO " + Helpers.RESET + " : " + msg);
    }

    // ERROR LOG
    public static void error(String msg) {
        System.err.println(
                "[" + LocalTime.now().format(FMT) + "] " + Helpers.RED + "ERROR" + Helpers.RESET + " : " + msg);
    }

    // CONNECTION LOG
    public static void rocket(String msg) {
        info("🚀 " + msg);
    }

    // OK LOG
    public static void ok(String msg) {
        System.out.println(
                "[" + LocalTime.now().format(FMT) + "] " + Helpers.GREEN + "✅" + Helpers.RESET + " : " + msg);
    }

    // EXCEPTION LOG
    public static void exception(String msg, Exception e) {
        error(msg + " (" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")");
    }

}
