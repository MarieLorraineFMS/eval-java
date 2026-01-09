package fr.fms;

import fr.fms.model.Training;
import fr.fms.service.TrainingService;

import java.util.List;
import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

/**
 * CLI UI for the training catalog.
 *
 * Responsibilities:
 * - list trainings (read-only catalog)
 * - search trainings by keyword
 * - filter trainings by onsite/remote
 * - allow selecting a training id (used by UiCart)
 *
 * Like app store, without the ads
 */
public final class UiTraining {

    /** Prevent instantiation. */
    private UiTraining() {
    }

    //////////////////////// CATALOG (READ ONLY) //////////////////////////////

    /**
     * Displays all trainings with pagination.
     *
     * @param trainingService training catalog service
     * @param sc              scanner used to read user input
     */
    public static void listAll(TrainingService trainingService, Scanner sc) {
        List<Training> list = trainingService.listAll();
        showCatalog(sc, "TOUTES LES FORMATIONS", list);
    }

    /**
     * Asks a keyword & displays matching trainings with pagination.
     *
     * @param trainingService training catalog service
     * @param sc              scanner used to read user input
     */
    public static void search(TrainingService trainingService, Scanner sc) {
        title("RECHERCHER UNE FORMATION");

        System.out.print("Mot-clé : ");
        String kw = sc.nextLine();

        List<Training> list = trainingService.searchByKeyword(kw);
        showCatalog(sc, "RÉSULTATS DE RECHERCHE", list);
    }

    /**
     * Asks for onsite/remote filter & displays filtered trainings with pagination
     *
     * @param trainingService training catalog service
     * @param sc              scanner used to read user input
     */
    public static void filter(TrainingService trainingService, Scanner sc) {
        title("FILTRER LES FORMATIONS");

        System.out.println("1) Présentiel");
        System.out.println("2) Distanciel");
        System.out.print("Choix : ");
        String c = sc.nextLine().trim();

        // If user types something else than "1", consider it as remote(not a drama)
        boolean onsite = "1".equals(c);

        List<Training> list = trainingService.listByOnsite(onsite);
        showCatalog(sc, "FORMATIONS FILTRÉES", list);
    }

    ////////////////////////////// SELECTION ///////////////////////////////////

    /**
     * Selection flow used by other UI screens .
     * Allows user to browse the catalog & pick a training id.
     *
     * @param trainingService training catalog service
     * @param sc              scanner used to read user input
     * @return selected training id, or null if user cancels
     */
    public static Integer pickTrainingId(TrainingService trainingService, Scanner sc) {
        title("CHOISIR UNE FORMATION");

        System.out.println("1) Lister toutes les formations");
        System.out.println("2) Rechercher par mot-clé");
        System.out.println("3) Filtrer (présentiel/distanciel)");
        System.out.println("0) Annuler");
        spacer();

        System.out.print("Choix : ");
        String choice = sc.nextLine().trim();

        List<Training> list;

        switch (choice) {
            case "1" -> list = trainingService.listAll();

            case "2" -> {
                System.out.print("Mot-clé : ");
                list = trainingService.searchByKeyword(sc.nextLine());
            }

            case "3" -> {
                System.out.println("1) Présentiel");
                System.out.println("2) Distanciel");
                System.out.print("Choix : ");
                boolean onsite = "1".equals(sc.nextLine().trim());
                list = trainingService.listByOnsite(onsite);
            }

            case "0" -> {
                return null;
            }

            default -> {
                uiWarn("Formation", "Choix invalide.");
                return null;
            }
        }

        if (list.isEmpty()) {
            uiWarn("Formation", "Aucune formation trouvée.");
            return null;
        }

        // Selection is allowed: user can type an ID to select a training
        return paginateWithSelection(
                sc,
                "CATALOGUE",
                list,
                5,
                UiTraining::printTrainingPage,
                id -> list.stream().anyMatch(t -> t.getId() == id),
                true);
    }

    /////////////////////////////// HELPERS ///////////////////////////////////

    /**
     * Displays a training list as a read-only catalog.
     *
     * @param sc    scanner used to read user input
     * @param title title to display
     * @param list  trainings to display
     */
    private static void showCatalog(Scanner sc, String title, List<Training> list) {
        if (list.isEmpty()) {
            title(title);
            uiWarn("Formation", "Aucune formation trouvée.");
            return;
        }

        // Read-only pagination
        paginateWithSelection(
                sc,
                title,
                list,
                5,
                UiTraining::printTrainingPage,
                id -> false,
                false);
    }

    /**
     * Prints one page of trainings.
     *
     * @param page trainings on the current page
     */
    private static void printTrainingPage(List<Training> page) {
        spacer();
        page.forEach(UiTraining::printTrainingCard);
        spacer();
    }

    /**
     * Prints a "card" display for one training.
     *
     * @param t training to display
     */
    private static void printTrainingCard(Training t) {
        printlnColor(CYAN, "#" + t.getId() + " — " + t.getName());

        printlnColor(
                t.isOnsite() ? GREEN : YELLOW,
                "Mode : " + (t.isOnsite() ? "Présentiel" : "Distanciel"));

        System.out.println(
                "Durée : " + t.getDurationDays() + " jours"
                        + " | Prix : " + formatMoney(t.getPrice()) + " €");

        // Wrap long descriptions to keep the CLI readable
        printWrapped("Description : " + t.getDescription(), 90);
        spacer();
    }
}
