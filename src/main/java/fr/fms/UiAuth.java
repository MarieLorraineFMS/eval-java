package fr.fms;

import fr.fms.exception.AuthenticationException;
import fr.fms.model.UserAccount;
import fr.fms.service.AuthService;

import java.util.Scanner;

import static fr.fms.utils.Helpers.*;

/**
 * CLI UI for authentication actions.
 *
 * Responsible for:
 * - prompting user input (login / password)
 * - calling AuthService
 * - displaying success or error messages
 *
 * It does NOT contain business logic AuthService is the brain.
 */
public final class UiAuth {

    /**
     * Prompts the user for credentials & attempts to log in.
     *
     * @param authService authentication service
     * @param sc          scanner used to read user input
     * @return authenticated UserAccount if login succeeds, otherwise null
     */
    public static UserAccount login(AuthService authService, Scanner sc) {
        title("CONNEXION");

        // Read login from CLI
        System.out.print("Identifiant : ");
        String login = sc.nextLine();

        // Read password from CLI
        System.out.print("Mot de passe : ");
        String pwd = sc.nextLine();

        try {
            UserAccount user = authService.login(login, pwd);
            printlnColor(GREEN, "Connecté ✅ " + user.getLogin());
            return user;
        } catch (AuthenticationException e) {
            // Friendly error message
            printlnColor(RED, e.getMessage());
            return null;
        }
    }

    /**
     * Prompts the user for credentials & attempts to register a new account.
     *
     * @param authService authentication service
     * @param sc          scanner used to read user input
     * @return created UserAccount if registration succeeds, otherwise null
     */
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
