package fr.fms.utils;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

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

    // Wrap long text
    public static void printWrapped(String text, int maxWidth) {
        if (isNullOrEmpty(text))
            return;

        String cleaned = text.replace("\r", " ")
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();

        String[] words = cleaned.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 > maxWidth) {
                System.out.println(line);
                line.setLength(0);
            }
            if (!line.isEmpty())
                line.append(" ");
            line.append(word);
        }

        if (!line.isEmpty()) {
            System.out.println(line);
        }
    }

    // STRINGS
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isBlank();
    }

    public static boolean confirm(Scanner sc, String message) {
        System.out.print(message + " (o/n) : ");
        String ans = sc.nextLine().trim().toLowerCase();
        return ans.equals("o") || ans.equals("oui");
    }

    public static int askInt(Scanner sc, String label) {
        System.out.print(label);
        return Integer.parseInt(sc.nextLine().trim());
    }

    // Pagination
    public static <T> Integer paginateWithSelection(
            Scanner sc,
            String title,
            List<T> items,
            int pageSize,
            Consumer<List<T>> pagePrinter,
            IntPredicate idExists,
            Boolean showSelectHint) {
        if (items == null || items.isEmpty()) {
            printlnColor(YELLOW, "Aucun résultat.");
            return null;
        }

        int total = items.size();
        int page = 0;
        int pageCount = (total + pageSize - 1) / pageSize;

        while (true) {
            int from = page * pageSize;
            int to = Math.min(from + pageSize, total);

            Helpers.title(title + " (" + (page + 1) + "/" + pageCount + ")");

            pagePrinter.accept(items.subList(from, to));

            System.out.println("s) Page suivante");
            System.out.println("p) Page précédente");
            System.out.println("0) Retour");
            if (showSelectHint) {
                printlnColor(YELLOW, "Ou taper directement un ID pour sélectionner");
            }
            spacer();

            System.out.print("Choix : ");
            String input = sc.nextLine().trim().toLowerCase();

            if ("s".equals(input)) {
                if (page >= pageCount - 1) {
                    printlnColor(YELLOW, "Déjà à la dernière page.");
                    pause(300);
                } else {
                    page++;
                }
                continue;
            }

            if ("p".equals(input)) {
                if (page <= 0) {
                    printlnColor(YELLOW, "Déjà à la première page.");
                    pause(300);
                } else {
                    page--;
                }
                continue;
            }

            if ("0".equals(input)) {
                return null;
            }

            try {
                int id = Integer.parseInt(input);
                if (idExists.test(id)) {
                    return id;
                }
                printlnColor(YELLOW, "ID introuvable.");
                pause(300);
            } catch (NumberFormatException e) {
                printlnColor(YELLOW, "Choix invalide.");
                pause(300);
            }
        }
    }

    // Asks for id or going back
    public static Integer askIdOrBack(Scanner sc, String label) {
        System.out.print(label);
        String input = sc.nextLine().trim();

        if ("0".equals(input)) {
            return null;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            printlnColor(YELLOW, "Veuillez saisir un id valide.");
            return null;
        }
    }

}
