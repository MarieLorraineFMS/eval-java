package fr.fms;

import fr.fms.dao.CartDao;
import fr.fms.dao.ClientDao;
import fr.fms.dao.OrderDao;
import fr.fms.dao.TrainingDao;
import fr.fms.dao.UserAccountDao;
import fr.fms.dao.factory.DaoFactory;

import fr.fms.model.UserAccount;
import fr.fms.service.AuthService;
import fr.fms.service.CartService;
import fr.fms.service.OrderService;
import fr.fms.service.TrainingService;

import fr.fms.utils.AppLogger;

import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

public class App {

    private static UserAccount currentUser = null;

    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance();

        TrainingDao trainingDao = daoFactory.trainingDao();
        CartDao cartDao = daoFactory.cartDao();
        ClientDao clientDao = daoFactory.clientDao();
        OrderDao orderDao = daoFactory.orderDao();
        UserAccountDao userDao = daoFactory.userAccountDao();

        TrainingService trainingService = new TrainingService(trainingDao);
        CartService cartService = new CartService(cartDao, trainingService);
        AuthService authService = new AuthService(userDao);
        OrderService orderService = new OrderService(orderDao, clientDao, cartDao, cartService);

        ////////////// CLI /////////////////////////

        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                title("MYSDF FORMATIONS");

                printlnColor(GREEN, "Utilisateur : " + (currentUser == null ? "NON CONNECTÉ" : currentUser.getLogin()));
                spacer();

                if (currentUser == null) {
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

                System.out.print("Choix : ");
                String choice = sc.nextLine().trim();

                if (currentUser == null) {
                    switch (choice) {
                        case "1" -> UiTraining.listAll(trainingService, sc);
                        case "2" -> UiTraining.search(trainingService, sc);
                        case "3" -> UiTraining.filter(trainingService, sc);
                        case "4" -> currentUser = UiAuth.login(authService, sc);
                        case "5" -> currentUser = UiAuth.register(authService, sc);
                        case "0" -> running = false;
                        default -> printlnColor(YELLOW, "Choix inconnu.");
                    }
                } else {
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
                            printlnColor(YELLOW, "Déconnecté.");
                        }

                        case "0" -> running = false;
                        default -> printlnColor(YELLOW, "Choix inconnu.");
                    }
                }

                pause(400);
            }
        } catch (Exception e) {
            AppLogger.exception("Fatal error in app", e);
        }

        spacer();
        printlnColor(CYAN, "Bye 👋");
    }

}
