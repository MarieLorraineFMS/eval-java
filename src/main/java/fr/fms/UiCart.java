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
            printlnColor(YELLOW, "Connexion nécessaire.");
            return;
        }

        boolean running = true;
        while (running) {
            title("PANIER");

            Cart cart = cartService.getOrCreateCart(currentUser.getId());
            printCart(cart);

            if (cart.getItems().isEmpty()) {
                // Empty cart => only useful actions
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

    // One cart line (no raw toString)
    private static String formatCartLine(CartItem i) {
        return i.getTraining().getId()
                + " | " + i.getTraining().getName()
                + " | Qté : " + i.getQuantity()
                + " | PU : " + String.format("%.2f", i.getUnitPrice()) + "€"
                + " | Total : " + String.format("%.2f", i.getLineTotal()) + "€";
    }

    private static void handleAddTraining(
            CartService cartService,
            TrainingService trainingService,
            Scanner sc,
            UserAccount currentUser) {
        // Training selection flow
        Integer trainingId = UiTraining.pickTrainingId(trainingService, sc);
        if (trainingId == null)
            return;

        int qty = askInt(sc, "Quantité : ");
        cartService.addTraining(currentUser.getId(), trainingId, qty);
        printlnColor(GREEN, "Ajouté ✅");
    }

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
            // Qty=1 => delete
            if (confirm(sc, "Supprimer la formation ?")) {
                cartService.removeTraining(currentUser.getId(), trainingId);
                printlnColor(GREEN, "Formation supprimée ✅");
            }
            return;
        }

        // Qty > 1
        System.out.println("1) Modifier la quantité");
        System.out.println("2) Supprimer la formation");
        System.out.println("0) Retour");
        spacer();

        System.out.print("Choix : ");
        String c = sc.nextLine().trim();

        switch (c) {
            case "1" -> {
                // Nbr to remove (0 => back)
                Integer toRemove = askIdOrBack(sc, "Quantité à retirer ? (0 pour retour) : ");
                if (toRemove == null)
                    return;

                if (toRemove <= 0) {
                    printlnColor(YELLOW, "Rien retiré.");
                    return;
                }

                if (toRemove > qty) {
                    printlnColor(YELLOW, "Impossible de retirer plus de " + qty + ".");
                    return;
                }

                // Delete line with confirmation
                if (toRemove == qty) {
                    if (!confirm(sc, "Supprimer la formation ?"))
                        return;
                    cartService.removeTraining(currentUser.getId(), trainingId);
                    printlnColor(GREEN, "Formation supprimée ✅");
                    return;
                }

                cartService.decrementTraining(currentUser.getId(), trainingId, toRemove);
                printlnColor(GREEN, "Quantité mise à jour ✅");
            }

            case "2" -> {
                if (confirm(sc, "Supprimer la formation ?")) {
                    cartService.removeTraining(currentUser.getId(), trainingId);
                    printlnColor(GREEN, "Formation supprimée ✅");
                }
            }

            default -> {
                // do nothing
            }
        }
    }

    private static void handleClearCart(CartService cartService, Scanner sc, UserAccount currentUser) {
        if (!confirm(sc, "Vider le panier ?"))
            return;
        cartService.clear(currentUser.getId());
        printlnColor(GREEN, "Panier vidé ✅");
    }

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

    // Select an item or auto select if only 1
    private static CartItem selectCartItem(Scanner sc, Cart cart) {
        if (cart.getItems().size() == 1) {
            return cart.getItems().get(0);
        }

        printlnColor(CYAN, "Choisir une formation :");
        cart.getItems().forEach(i -> System.out.println(formatCartLine(i)));
        spacer();

        Integer id = askIdOrBack(sc, "Id formation (0 pour retour) : ");
        if (id == null)
            return null;

        Optional<CartItem> item = cart.getItems().stream()
                .filter(i -> i.getTraining().getId() == id)
                .findFirst();

        if (item.isEmpty()) {
            printlnColor(YELLOW, "Formation non trouvée.");
            return null;
        }

        return item.get();
    }
}
