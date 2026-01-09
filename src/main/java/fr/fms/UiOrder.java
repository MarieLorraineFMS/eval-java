package fr.fms;

import fr.fms.model.Client;
import fr.fms.model.Order;
import fr.fms.model.OrderLine;
import fr.fms.service.OrderService;

import java.util.List;
import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

/**
 * CLI UI for orders & clients.
 *
 * Responsibilities:
 * - select or create a client
 * - list orders for the current user
 * - display order details & lines
 *
 * UI-only:
 * it asks questions, prints tables, & calls OrderService.
 */
public final class UiOrder {

    /** Prevent instantiation. */
    private UiOrder() {
    }

    /**
     * Asks the user to select an existing client or create a new one.
     *
     * @param sc           scanner used to read user input
     * @param orderService service used to load/create clients
     * @return selected/created Client, or null if the user cancels
     */
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
                uiWarn("Commande", "Choix invalide.");
                yield null;
            }
        };
    }

    /**
     * Client selection flow:
     * - search by name/email
     * - show last 10 clients
     * - pick client by id
     * - show all clients with pagination
     *
     * @param sc           scanner used to read user input
     * @param orderService service used to load clients
     * @return selected Client, or null if user cancels or selection fails
     */
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

                // In-memory filtering (good enough for a CLI app)
                candidates = orderService.listClients().stream()
                        .filter(cl -> (cl.getFirstName() + " " + cl.getLastName()).toLowerCase().contains(q)
                                || cl.getEmail().toLowerCase().contains(q))
                        .toList();
            }

            case "2" -> {
                // "Last 10" by id desc
                candidates = orderService.listClients().stream()
                        .sorted((a, b) -> Integer.compare(b.getId(), a.getId()))
                        .limit(10)
                        .toList();
            }

            case "3" -> {
                // Direct id input
                selectedId = askIntOrBack(sc, "Id client (0 pour retour) : ");
            }

            case "4" -> {
                // Full list with pagination & selection
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
                uiWarn("Commande", "Choix invalide.");
                return null;
            }
        }

        // Cases 1 & 2: show candidates then ask for an id
        if (candidates != null) {
            if (candidates.isEmpty()) {
                uiWarn("Commande", "Aucun client trouvé.");
                return null;
            }

            printClientsTable(candidates);
            selectedId = askIntOrBack(sc, "Id client (0 pour retour) : ");
        }

        // User chose "back"
        if (selectedId == null) {
            return null;
        }

        // Load client by id
        return orderService.getClientById(selectedId).orElseGet(() -> {
            uiError("Commande", "Client introuvable.");
            return null;
        });
    }

    /**
     * Prints a small client table (id / name / email).
     *
     * @param clients clients to display
     */
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

    /**
     * Truncates a string for table display.
     *
     * @param s   input string
     * @param max maximum length
     * @return truncated string (with ellipsis) or empty string if null
     */
    private static String truncate(String s, int max) {
        if (s == null)
            return "";
        if (s.length() <= max)
            return s;
        return s.substring(0, Math.max(0, max - 1)) + "…";
    }

    /**
     * Asks user for client fields.
     *
     * @param sc scanner used to read user input
     * @return Client draft object validated/created by OrderService
     */
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

        // Draft client: OrderService validate & normalize it
        return new Client(fn, ln, email, addr, phone);
    }

    /**
     * Lists all orders for the given user.
     *
     * @param orderService order service
     * @param userId       identifier of the user
     */
    public static void listMyOrders(OrderService orderService, int userId) {
        title("MES COMMANDES");

        List<Order> orders = orderService.listByUserId(userId);
        if (orders.isEmpty()) {
            uiWarn("Commande", "Aucune commande pour le moment.");
            return;
        }

        orders.forEach(UiOrder::printOrder);
    }

    /**
     * Prints one order (header + lines).
     *
     * @param o order to display
     */
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

        uiInfo("Commande", "Détails :");
        o.getLines().forEach(UiOrder::printOrderLine);

        spacer();
        printlnColor(GREEN, "TOTAL COMMANDE : " + formatMoney(o.getTotal()) + " €");
        printlnColor(CYAN, "----------------------------------------");
        spacer();
    }

    /**
     * Prints one order line.
     *
     * @param line order line to display
     */
    private static void printOrderLine(OrderLine line) {
        System.out.println(
                " - " + line.getTraining().getName()
                        + " | Qté : " + line.getQuantity()
                        + " | PU : " + formatMoney(line.getUnitPrice()) + " €"
                        + " | Ligne : " + formatMoney(line.getLineTotal()) + " €");
    }
}
