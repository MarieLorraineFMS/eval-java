package fr.fms;

import fr.fms.model.Client;
import fr.fms.model.Order;
import fr.fms.model.OrderLine;
import fr.fms.service.OrderService;

import java.util.List;
import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

public final class UiOrder {
    private UiOrder() {
    }

    // Selecting existing client or creating new one
    public static Client askClient(Scanner sc, OrderService orderService) {
        title("CLIENT");

        System.out.println("1) Choisir un client existant");
        System.out.println("2) Créer un nouveau client");
        System.out.println("0) Annuler");
        spacer();

        System.out.print("Choix : ");
        String choice = sc.nextLine().trim();

        return switch (choice) {
            case "1" -> selectExistingClient(sc, orderService);
            case "2" -> orderService.createOrReuseClient(askClientFields(sc));
            case "0" -> null;
            default -> {
                printlnColor(YELLOW, "Choix invalide.");
                yield null;
            }
        };
    }

    // Existing client selection flow
    private static Client selectExistingClient(Scanner sc, OrderService orderService) {
        title("CHOISIR UN CLIENT");

        System.out.println("1) Rechercher par nom ou email");
        System.out.println("2) Afficher les 10 derniers clients");
        System.out.println("3) Saisir une id client");
        System.out.println("4) Afficher tous les clients");
        System.out.println("0) Retour");
        spacer();

        System.out.print("Choix : ");
        String c = sc.nextLine().trim();

        List<Client> candidates = null;
        Integer selectedId = null;

        switch (c) {
            case "1" -> {
                System.out.print("Recherche : ");
                String q = sc.nextLine().trim().toLowerCase();

                candidates = orderService.listClients().stream()
                        .filter(cl -> (cl.getFirstName() + " " + cl.getLastName()).toLowerCase().contains(q)
                                || cl.getEmail().toLowerCase().contains(q))
                        .toList();
            }

            case "2" -> {
                candidates = orderService.listClients().stream()
                        .sorted((a, b) -> Integer.compare(b.getId(), a.getId()))
                        .limit(10)
                        .toList();
            }

            case "3" -> {
                selectedId = askIdOrBack(sc, "Id client (0 pour retour) : ");
            }

            case "4" -> {
                List<Client> all = orderService.listClients();

                selectedId = paginateWithSelection(
                        sc,
                        "LISTE DES CLIENTS",
                        all,
                        10,
                        UiOrder::printClientsTable,
                        id -> all.stream().anyMatch(cl -> cl.getId() == id),
                        true);
            }

            case "0" -> {
                return null;
            }

            default -> {
                printlnColor(YELLOW, "Choix invalide.");
                return null;
            }
        }

        // Cases 1 & 2 : select or go back
        if (candidates != null) {
            if (candidates.isEmpty()) {
                printlnColor(YELLOW, "Aucun client trouvé.");
                return null;
            }

            printClientsTable(candidates);
            selectedId = askIdOrBack(sc, "Id client (0 pour retour) : ");
        }

        // Go back
        if (selectedId == null) {
            return null;
        }

        return orderService.getClientById(selectedId).orElseGet(() -> {
            printlnColor(RED, "Client introuvable.");
            return null;
        });
    }

    private static void printClientsTable(List<Client> clients) {
        spacer();
        printlnColor(CYAN, "ID  | Nom                    | Email");
        printlnColor(CYAN, "----+-------------------------+-------------------------------");

        clients.forEach(c -> {
            String fullName = (c.getFirstName() + " " + c.getLastName()).trim();
            System.out.printf("%-3d | %-23s | %s%n", c.getId(), truncate(fullName, 23), c.getEmail());
        });

        spacer();
    }

    // Truncate for tables
    private static String truncate(String s, int max) {
        if (s == null)
            return "";
        if (s.length() <= max)
            return s;
        return s.substring(0, Math.max(0, max - 1)) + "…";
    }

    private static Client askClientFields(Scanner sc) {
        title("NOUVEAU CLIENT");

        System.out.print("Prénom : ");
        String fn = sc.nextLine();

        System.out.print("Nom : ");
        String ln = sc.nextLine();

        System.out.print("Email : ");
        String email = sc.nextLine();

        System.out.print("Adresse : ");
        String addr = sc.nextLine();

        System.out.print("Téléphone : ");
        String phone = sc.nextLine();

        return new Client(fn, ln, email, addr, phone);
    }

    public static void listMyOrders(OrderService orderService, int userId) {
        title("MES COMMANDES");

        List<Order> orders = orderService.listByUserId(userId);
        if (orders.isEmpty()) {
            printlnColor(YELLOW, "Aucune commande pour le moment.");
            return;
        }

        orders.forEach(UiOrder::printOrder);
    }

    private static void printOrder(Order o) {
        printlnColor(CYAN, "----------------------------------------");
        printlnColor(CYAN, "Commande n° " + o.getId());

        System.out.println("Date        : " + o.getCreatedAt());
        System.out.println("Client      : "
                + o.getClient().getFirstName() + " "
                + o.getClient().getLastName()
                + " (" + o.getClient().getEmail() + ")");
        System.out.println("Statut      : " + o.getStatus());
        spacer();

        printlnColor(YELLOW, "Détails :");
        o.getLines().forEach(UiOrder::printOrderLine);

        spacer();
        printlnColor(GREEN, "TOTAL COMMANDE : " + o.getTotal() + " €");
        printlnColor(CYAN, "----------------------------------------");
        spacer();
    }

    private static void printOrderLine(OrderLine line) {
        System.out.println(
                " - " + line.getTraining().getName()
                        + " | Qté : " + line.getQuantity()
                        + " | PU : " + String.format("%.2f", line.getUnitPrice()) + " €"
                        + " | Ligne : " + String.format("%.2f", line.getLineTotal()) + " €");
    }
}
