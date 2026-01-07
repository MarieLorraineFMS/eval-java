package fr.fms.utils;

public class Helpers {

    // SLEEP / UX
    public static void pause(int ms) {
        if (ms <= 0)
            return;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // CLI
    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";

    public static void spacer() {
        System.out.println();
    }

    public static void title(String text) {
        spacer();
        printlnColor(CYAN, "//////////// " + text + " ///////////");
        spacer();
    }

    public static void printlnColor(String color, String text) {
        System.out.println(color + text + RESET);
    }

    // STRINGS
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isBlank();
    }
}
