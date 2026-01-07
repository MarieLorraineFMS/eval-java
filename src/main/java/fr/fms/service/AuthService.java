package fr.fms.service;

import fr.fms.dao.UserAccountDao;
import fr.fms.exception.AuthenticationException;
import fr.fms.model.UserAccount;
import fr.fms.utils.PasswordHasher;

import static fr.fms.utils.Helpers.isNullOrEmpty;

public class AuthService {
    private final UserAccountDao userDao;

    public AuthService(UserAccountDao userDao) {
        this.userDao = userDao;
    }

    public UserAccount login(String login, String password) {
        // Simple validation
        if (isNullOrEmpty(login) || isNullOrEmpty(password)) {
            throw new AuthenticationException("Identifiant/mot de passe obligatoires.");
        }

        String cleanLogin = login.trim();
        String inputHash = PasswordHasher.sha256(password); // Hash input password

        return userDao.findByLogin(cleanLogin)
                .filter(u -> inputHash.equals(u.getPasswordHash())) // Hash compare
                .orElseThrow(() -> new AuthenticationException("Identifiant ou mot de passe invalide."));
    }

    public UserAccount register(String login, String password) {
        if (isNullOrEmpty(login) || isNullOrEmpty(password)) {
            throw new AuthenticationException("Identifiant/mot de passe obligatoires.");
        }

        String cleanLogin = login.trim();

        // Prevent duplicate login
        if (userDao.findByLogin(cleanLogin).isPresent()) {
            throw new AuthenticationException("L'identifiant existe déjà.");
        }

        String pwdHash = PasswordHasher.sha256(password); // Hash before storing

        int id = userDao.create(new UserAccount(cleanLogin, pwdHash));
        if (id <= 0) {
            throw new AuthenticationException("Une erreur est survenue lors de la création de l'utilisateur.");
        }

        return userDao.findById(id)
                .orElseThrow(() -> new AuthenticationException(
                        "Une erreur est survenue lors de la récupération de l'utilisateur."));
    }

}
