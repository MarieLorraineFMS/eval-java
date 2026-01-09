package fr.fms.dao.factory;

import fr.fms.dao.CartDao;
import fr.fms.dao.ClientDao;
import fr.fms.dao.OrderDao;
import fr.fms.dao.TrainingDao;
import fr.fms.dao.UserAccountDao;
import fr.fms.dao.jdbc.CartDaoJdbc;
import fr.fms.dao.jdbc.ClientDaoJdbc;
import fr.fms.dao.jdbc.OrderDaoJdbc;
import fr.fms.dao.jdbc.TrainingDaoJdbc;
import fr.fms.dao.jdbc.UserAccountDaoJdbc;

/**
 * Factory used to access DAO implementations.
 *
 * - follows the Singleton pattern
 * - hides JDBC implementations from the rest of the application
 * - provides a single access point to all DAOs
 *
 * In short: one factory to rule them all 🧙‍♂️
 */
public final class DaoFactory {

    /** Singleton instance */
    private static final DaoFactory INSTANCE = new DaoFactory();

    /**
     * Hidden DAO implementations.
     * Rest of app only sees interfaces, not JDBC details.
     */
    private final TrainingDao trainingDao = new TrainingDaoJdbc();
    private final UserAccountDao userAccountDao = new UserAccountDaoJdbc();
    private final ClientDao clientDao = new ClientDaoJdbc();
    private final CartDao cartDao = new CartDaoJdbc();
    private final OrderDao orderDao = new OrderDaoJdbc();

    /**
     * Private constructor.
     * Prevents external instantiation (classic Singleton move).
     */
    private DaoFactory() {
        // Nothing to do here
    }

    /**
     * Returns unique instance of the factory.
     *
     * @return singleton DaoFactory instance
     */
    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Provides access to TrainingDao.
     *
     * @return training DAO implementation
     */
    public TrainingDao trainingDao() {
        return trainingDao;
    }

    /**
     * Provides access to UserAccountDao.
     *
     * @return user account DAO implementation
     */
    public UserAccountDao userAccountDao() {
        return userAccountDao;
    }

    /**
     * Provides access to ClientDao.
     *
     * @return client DAO implementation
     */
    public ClientDao clientDao() {
        return clientDao;
    }

    /**
     * Provides access to CartDao.
     *
     * @return cart DAO implementation
     */
    public CartDao cartDao() {
        return cartDao;
    }

    /**
     * Provides access to OrderDao.
     *
     * @return order DAO implementation
     */
    public OrderDao orderDao() {
        return orderDao;
    }
}
