package fr.fms;

import fr.fms.exception.TrainingNotFoundException;
import fr.fms.model.Cart;
import fr.fms.model.CartItem;
import fr.fms.model.Client;
import fr.fms.model.Order;
import fr.fms.model.UserAccount;
import fr.fms.service.CartService;
import fr.fms.service.OrderService;
import fr.fms.service.TrainingService;

import java.util.Optional;
import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

public final class UiCart {
    private UiCart() {
    }

    public static void menu(
            CartService cartService,
            TrainingService trainingService,
            OrderService orderService,
            Scanner sc,
            UserAccount currentUser) {
        if (currentUser == null) {
            printlnColor(YELLOW, "Tu dois te connecter pour utiliser le panier.");
            return;
        }

        boolean running = true;
        while (running) {
            title("PANIER");

            Cart cart = cartService.getOrCreateCart(currentUser.getId());
            printCart(cart);

            if (cart.getItems().isEmpty()) {
                // Empty cart => only display useful actions
                System.out.println("1) Ajouter une formation");
                System.out.println("0) Retour");
                spacer();

                System.out.print("Choix : ");
                String choice = sc.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> handleAddTraining(cartService, trainingService, sc, currentUser);
                        case "0" -> running = false;
                        default -> printlnColor(YELLOW, "Choix inconnu.");
                    }
                } catch (Exception e) {
                    printlnColor(RED, "Erreur panier : " + e.getMessage());
                }

                pause(250);
                continue;
            }

            // Non-empty cart => full menu
            System.out.println("1) Ajouter une formation");
            System.out.println("2) Modifier / retirer une formation");
            System.out.println("3) Vider le panier");
            System.out.println("4) Valider / Commander");
            System.out.println("0) Retour");
            spacer();

            System.out.print("Choix : ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> handleAddTraining(cartService, trainingService, sc, currentUser);
                    case "2" -> handleEditOrRemoveItem(cartService, sc, currentUser);
                    case "3" -> handleClearCart(cartService, sc, currentUser);
                    case "4" -> handleCheckout(cartService, orderService, sc, currentUser);
                    case "0" -> running = false;
                    default -> printlnColor(YELLOW, "Choix inconnu.");
                }
            } catch (TrainingNotFoundException e) {
                printlnColor(RED, e.getMessage());
            } catch (Exception e) {
                printlnColor(RED, "Erreur panier : " + e.getMessage());
            }

            pause(250);
        }
    }

    private static void printCart(Cart cart) {
        if (cart.getItems().isEmpty()) {
            printlnColor(YELLOW, "Panier vide.");
            return;
        }

        printlnColor(CYAN, "Contenu du panier :");
        cart.getItems().forEach(i -> System.out.println(formatCartLine(i)));
        spacer();
        printlnColor(GREEN, "TOTAL = " + cart.getTotal() + " €");
        spacer();
    }

    // CartItem line
    private static String formatCartLine(CartItem i) {
        return i.getTraining().getId()
                + " | " + i.getTraining().getName()
                + " | Qté: " + i.getQuantity()
                + " | PU: " + String.format("%.2f", i.getUnitPrice()) + "€"
                + " | Total: " + String.format("%.2f", i.getLineTotal()) + "€";
    }

    // Add training
    private static void handleAddTraining(
            CartService cartService,
            TrainingService trainingService,
            Scanner sc,
            UserAccount currentUser) {
        Integer trainingId = UiTraining.pickTrainingId(trainingService, sc);

        if (trainingId == null)
            return;

        int qty = askInt(sc, "Quantité : ");
        cartService.addTraining(currentUser.getId(), trainingId, qty);
        printlnColor(GREEN, "Ajouté ✅");
    }

    // Edit/remove training
    private static void handleEditOrRemoveItem(CartService cartService, Scanner sc, UserAccount currentUser) {
        Cart cart = cartService.getOrCreateCart(currentUser.getId());

        if (cart.getItems().isEmpty()) {
            printlnColor(YELLOW, "Panier vide.");
            return;
        }

        CartItem selected = selectCartItem(sc, cart);
        if (selected == null)
            return;

        int trainingId = selected.getTraining().getId();
        int qty = selected.getQuantity();

        title("MODIFIER / RETIRER");

        System.out.println("Formation : " + selected.getTraining().getName());
        System.out.println("Quantité actuelle : " + qty);
        spacer();

        if (qty <= 1) {
            System.out.println("1) Supprimer la ligne");
            System.out.println("0) Retour");
            spacer();

            System.out.print("Choix : ");
            String c = sc.nextLine().trim();

            if ("1".equals(c)) {
                if (confirm(sc, "Supprimer cette formation du panier ?")) {
                    cartService.removeTraining(currentUser.getId(), trainingId);
                    printlnColor(GREEN, "Ligne supprimée ✅");
                }
            }
            return;
        }

        System.out.println("1) Enlever 1");
        System.out.println("2) Enlever plusieurs");
        System.out.println("3) Supprimer tout");
        System.out.println("0) Retour");
        spacer();

        System.out.print("Choix : ");
        String c = sc.nextLine().trim();

        switch (c) {
            case "1" -> {
                cartService.decrementTraining(currentUser.getId(), trainingId, 1);
                printlnColor(GREEN, "Quantité mise à jour ✅");
            }
            case "2" -> {
                int delta = askInt(sc, "Quantité à déduire ? ");
                if (delta <= 0)
                    return;
                cartService.decrementTraining(currentUser.getId(), trainingId, delta);
                printlnColor(GREEN, "Quantité mise à jour ✅");
            }
            case "3" -> {
                if (confirm(sc, "Supprimer complètement cette formation ?")) {
                    cartService.removeTraining(currentUser.getId(), trainingId);
                    printlnColor(GREEN, "Formation supprimée ✅");
                }
            }
            default -> {
                // do nothing
            }
        }
    }

    // Confirm before clearing
    private static void handleClearCart(CartService cartService, Scanner sc, UserAccount currentUser) {
        if (!confirm(sc, "Vider le panier ?"))
            return;
        cartService.clear(currentUser.getId());
        printlnColor(GREEN, "Panier vidé ✅");
    }

    // Checkout
    private static void handleCheckout(CartService cartService, OrderService orderService, Scanner sc,
            UserAccount currentUser) {
        Cart cart = cartService.getOrCreateCart(currentUser.getId());
        if (cart.getItems().isEmpty()) {
            printlnColor(YELLOW, "Le panier est vide.");
            return;
        }

        Client client = UiOrder.askClient(sc, orderService);
        if (client == null)
            return;

        if (!confirm(sc, "Confirmer la commande ?"))
            return;

        Order order = orderService.checkout(currentUser.getId(), client);
        printlnColor(GREEN, "Commande créée ✅ id=" + order.getId() + " total=" + order.getTotal() + "€");
        printlnColor(YELLOW, "Le panier a été vidé.");
    }

    // Select an item or auto-select if only 1
    private static CartItem selectCartItem(Scanner sc, Cart cart) {
        if (cart.getItems().size() == 1) {
            return cart.getItems().get(0);
        }

        printlnColor(CYAN, "Choisis une formation :");
        cart.getItems().forEach(i -> System.out.println(formatCartLine(i)));
        spacer();

        int id = askIdOrBack(sc, "Id formation (0 pour retour) : ");

        Optional<CartItem> item = cart.getItems().stream()
                .filter(i -> i.getTraining().getId() == id)
                .findFirst();

        if (item.isEmpty()) {
            printlnColor(YELLOW, "Id non trouvé dans le panier.");
            return null;
        }

        return item.get();
    }

}
