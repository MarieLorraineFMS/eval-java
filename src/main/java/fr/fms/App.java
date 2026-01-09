package fr.fms;

import fr.fms.dao.CartDao;
import fr.fms.dao.ClientDao;
import fr.fms.dao.OrderDao;
import fr.fms.dao.TrainingDao;
import fr.fms.dao.UserAccountDao;
import fr.fms.dao.factory.DaoFactory;
import fr.fms.exception.AuthenticationException;
import fr.fms.exception.CartEmptyException;
import fr.fms.exception.DaoException;
import fr.fms.exception.OrderException;
import fr.fms.exception.TrainingNotFoundException;
import fr.fms.model.UserAccount;
import fr.fms.service.AuthService;
import fr.fms.service.CartService;
import fr.fms.service.OrderService;
import fr.fms.service.TrainingService;

import fr.fms.utils.AppLogger;

import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

/**
 * Main application.
 *
 * Responsibilities:
 * - create DAOs via DaoFactory
 * - build services (business layer)
 * - run the main CLI loop
 * - route user choices to the correct UI screens
 *
 * "router + bootstrap" of app.
 */
public class App {

    /** Current logged-in user (null means "not connected") */
    private static UserAccount currentUser = null;

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Build DAOs using the factory (hides JDBC implementations)
        DaoFactory daoFactory = DaoFactory.getInstance();

        TrainingDao trainingDao = daoFactory.trainingDao();
        CartDao cartDao = daoFactory.cartDao();
        ClientDao clientDao = daoFactory.clientDao();
        OrderDao orderDao = daoFactory.orderDao();
        UserAccountDao userDao = daoFactory.userAccountDao();

        // Build services (business layer)
        TrainingService trainingService = new TrainingService(trainingDao);
        CartService cartService = new CartService(cartDao, trainingService);
        AuthService authService = new AuthService(userDao);
        OrderService orderService = new OrderService(orderDao, clientDao, cartDao, cartService);

        ////////////// CLI /////////////////////////

        // Try-with-resources ensures Scanner is properly closed
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                title("MYSDF FORMATIONS");

                // Show current user state
                printlnColor(GREEN, "Utilisateur : " + (currentUser == null ? "NON CONNECTÉ" : currentUser.getLogin()));
                spacer();

                // Menu depends on authentication state
                boolean isLoggedIn = currentUser != null;
                printMainMenu(isLoggedIn);

                System.out.print("Choix : ");
                String choice = sc.nextLine().trim();

                try {
                    // "0" is handled here to control loop
                    if ("0".equals(choice)) {
                        running = false;
                    } else {
                        handleMainChoice(choice, sc, trainingService, cartService, authService, orderService);
                    }

                } catch (AuthenticationException
                        | TrainingNotFoundException
                        | CartEmptyException
                        | OrderException e) {
                    // Show business error message & keep running
                    uiError("Menu principal", e.getMessage());
                } catch (DaoException e) {
                    // DB errors: show message & keep running
                    uiError("Menu principal", "Db error: " + e.getMessage());
                }

                // Small pause to avoid "instant spam"
                pause(400);
            }
        } catch (Exception e) {
            // Log fatal errors instead of crashing silently)
            AppLogger.exception("Fatal error in app", e);
        }

        spacer();
        uiInfo("Menu principal", "Bye 👋");
    }

    /**
     * Prints main menu depending on authentication state.
     *
     * @param isLoggedIn true if user is logged in, false otherwise
     */
    private static void printMainMenu(boolean isLoggedIn) {
        if (!isLoggedIn) {
            System.out.println("1) Lister les formations");
            System.out.println("2) Rechercher une formation par mot-clé");
            System.out.println("3) Filtrer les formations par modalité (présentiel/distanciel)");
            System.out.println("4) Se connecter");
            System.out.println("5) Créer un compte");
            System.out.println("0) Quitter");
        } else {
            System.out.println("1) Lister les formations");
            System.out.println("2) Rechercher une formation par mot-clé");
            System.out.println("3) Filtrer les formations par modalité (présentiel/distanciel)");
            System.out.println("4) Mon panier");
            System.out.println("5) Mes commandes");
            System.out.println("6) Se déconnecter");
            System.out.println("0) Quitter");
        }
    }

    /**
     * Handles user choice from the main menu.
     *
     * @param choice          user input choice
     * @param sc              scanner
     * @param trainingService training catalog service
     * @param cartService     cart service
     * @param authService     auth service
     * @param orderService    order service
     */
    private static void handleMainChoice(
            String choice,
            Scanner sc,
            TrainingService trainingService,
            CartService cartService,
            AuthService authService,
            OrderService orderService) {

        boolean isLoggedIn = currentUser != null;

        if (!isLoggedIn) {
            switch (choice) {
                case "1" -> UiTraining.listAll(trainingService, sc);
                case "2" -> UiTraining.search(trainingService, sc);
                case "3" -> UiTraining.filter(trainingService, sc);
                case "4" -> currentUser = UiAuth.login(authService, sc);
                case "5" -> currentUser = UiAuth.register(authService, sc);

                default -> uiWarn("Menu principal", "Choix inconnu.");
            }
            return;
        }

        // Logged-in menu
        switch (choice) {
            case "1" -> UiTraining.listAll(trainingService, sc);
            case "2" -> UiTraining.search(trainingService, sc);
            case "3" -> UiTraining.filter(trainingService, sc);

            case "4" -> UiCart.menu(
                    cartService,
                    trainingService,
                    orderService,
                    sc,
                    currentUser);

            case "5" -> UiOrder.listMyOrders(orderService, currentUser.getId());

            case "6" -> {
                currentUser = null;
                uiWarn("Menu principal", "Déconnecté.");
            }

            default -> uiWarn("Menu principal", "Choix inconnu.");
        }
    }

}
