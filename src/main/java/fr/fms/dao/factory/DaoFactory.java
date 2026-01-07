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

public final class DaoFactory {

    // Singleton : one factory TO RULL THEM ALL......!!!
    private static final DaoFactory INSTANCE = new DaoFactory();

    // Hidden instances of DAO implementations ("slaves")
    private final TrainingDao trainingDao = new TrainingDaoJdbc();
    private final UserAccountDao userAccountDao = new UserAccountDaoJdbc();
    private final ClientDao clientDao = new ClientDaoJdbc();
    private final CartDao cartDao = new CartDaoJdbc();
    private final OrderDao orderDao = new OrderDaoJdbc();

    private DaoFactory() {
        // Private constructor to prevent external instantiation
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    // Give me the right slave
    public TrainingDao trainingDao() {
        return trainingDao;
    }

    public UserAccountDao userAccountDao() {
        return userAccountDao;
    }

    public ClientDao clientDao() {
        return clientDao;
    }

    public CartDao cartDao() {
        return cartDao;
    }

    public OrderDao orderDao() {
        return orderDao;
    }
}
