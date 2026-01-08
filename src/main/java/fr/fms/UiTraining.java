package fr.fms;

import fr.fms.model.Training;
import fr.fms.service.TrainingService;

import java.util.List;
import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

public final class UiTraining {
    private UiTraining() {
    }

    //////////////////////// CATALOG (READ ONLY) //////////////////////////////

    public static void listAll(TrainingService trainingService, Scanner sc) {
        List<Training> list = trainingService.listAll();
        showCatalog(sc, "TOUTES LES FORMATIONS", list);
    }

    public static void search(TrainingService trainingService, Scanner sc) {
        title("RECHERCHER UNE FORMATION");

        System.out.print("Mot-clé : ");
        String kw = sc.nextLine();

        List<Training> list = trainingService.searchByKeyword(kw);
        showCatalog(sc, "RÉSULTATS DE RECHERCHE", list);
    }

    public static void filter(TrainingService trainingService, Scanner sc) {
        title("FILTRER LES FORMATIONS");

        System.out.println("1) Présentiel");
        System.out.println("2) Distanciel");
        System.out.print("Choix : ");
        String c = sc.nextLine().trim();

        boolean onsite = "1".equals(c);

        List<Training> list = trainingService.listByOnsite(onsite);
        showCatalog(sc, "FORMATIONS FILTRÉES", list);
    }

    ////////////////////////////// SELECTION ///////////////////////////////////

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
                printlnColor(YELLOW, "Choix invalide.");
                return null;
            }
        }

        if (list.isEmpty()) {
            printlnColor(YELLOW, "Aucune formation trouvée.");
            return null;
        }

        return paginateWithSelection(
                sc,
                "CATALOGUE",
                list,
                5,
                UiTraining::printTrainingPage,
                id -> list.stream().anyMatch(t -> t.getId() == id),
                true // ⬅️ sélection AUTORISÉE ici
        );
    }

    /////////////////////////////// HELPERS ///////////////////////////////////

    private static void showCatalog(Scanner sc, String title, List<Training> list) {
        if (list.isEmpty()) {
            title(title);
            printlnColor(YELLOW, "Aucune formation trouvée.");
            return;
        }

        paginateWithSelection(
                sc,
                title,
                list,
                5,
                UiTraining::printTrainingPage,
                id -> false,
                false);
    }

    private static void printTrainingPage(List<Training> page) {
        spacer();
        page.forEach(UiTraining::printTrainingCard);
        spacer();
    }

    private static void printTrainingCard(Training t) {
        printlnColor(CYAN, "#" + t.getId() + " — " + t.getName());

        printlnColor(
                t.isOnsite() ? GREEN : YELLOW,
                "Mode : " + (t.isOnsite() ? "Présentiel" : "Distanciel"));

        System.out.println(
                "Durée : " + t.getDurationDays() + " jours"
                        + " | Prix : " + String.format("%.2f", t.getPrice()) + " €");

        printWrapped("Description : " + t.getDescription(), 90);
        spacer();
    }
}
