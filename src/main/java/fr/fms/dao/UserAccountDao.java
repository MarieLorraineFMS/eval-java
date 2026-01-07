package fr.fms.dao;

import java.util.Optional;

import fr.fms.model.UserAccount;

public interface UserAccountDao {
    Optional<UserAccount> findById(int id);

    Optional<UserAccount> findByLogin(String login);

    int create(UserAccount user);

    // Auth is done in AuthService
}
