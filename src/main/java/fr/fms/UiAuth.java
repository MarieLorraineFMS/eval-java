package fr.fms;

import fr.fms.exception.AuthenticationException;
import fr.fms.model.UserAccount;
import fr.fms.service.AuthService;

import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

public final class UiAuth {

    public static UserAccount login(AuthService authService, Scanner sc) {
        title("CONNEXION");

        System.out.print("Identifiant : ");
        String login = sc.nextLine();

        System.out.print("Mot de passe : ");
        String pwd = sc.nextLine();

        try {
            UserAccount user = authService.login(login, pwd);
            printlnColor(GREEN, "Connecté ✅ " + user.getLogin());
            return user;
        } catch (AuthenticationException e) {
            printlnColor(RED, e.getMessage());
            return null;
        }
    }

    public static UserAccount register(AuthService authService, Scanner sc) {
        title("INSCRIPTION");

        System.out.print("Identifiant : ");
        String login = sc.nextLine();

        System.out.print("Mot de passe : ");
        String pwd = sc.nextLine();

        try {
            UserAccount user = authService.register(login, pwd);
            printlnColor(GREEN, "Compte créé ✅ " + user.getLogin());
            return user;
        } catch (AuthenticationException e) {
            printlnColor(RED, e.getMessage());
            return null;
        }
    }
}
