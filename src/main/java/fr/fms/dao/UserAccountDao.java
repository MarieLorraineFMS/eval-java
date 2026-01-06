package fr.fms.dao;

import fr.fms.model.UserAccount;

import java.util.Optional;

public interface UserAccountDao {
    Optional<UserAccount> readByLogin(String login);

    int create(UserAccount user);
}
