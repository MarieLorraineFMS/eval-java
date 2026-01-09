package fr.fms.service;

import java.math.BigDecimal;
import java.util.List;

import fr.fms.dao.CartDao;
import fr.fms.exception.CartEmptyException;
import fr.fms.exception.TrainingNotFoundException;
import fr.fms.model.Cart;
import fr.fms.model.Training;

/**
 * Cart service.
 *
 * Responsibilities:
 * - get or create a persisted cart for a user
 * - add / decrement / remove trainings in the cart
 * - clear the cart
 * - provide training catalog helpers
 * - validate cart state (empty vs not empty)
 *
 * It coordinates DAOs & applies rules,
 * so the UI can stay simple and just display stuff
 */
public class CartService {

    /** DAO used to persist & load carts */
    private final CartDao cartDao;

    /** Service used to validate trainings & read prices from db */
    private final TrainingService trainingService;

    /**
     * Builds CartService.
     *
     * @param cartDao         DAO used to access cart persistence
     * @param trainingService used to access training catalog
     */
    public CartService(CartDao cartDao, TrainingService trainingService) {
        this.cartDao = cartDao;
        this.trainingService = trainingService;
    }

    /**
     * Returns a persisted cart for the user.
     * If the cart does not exist, it is created first.
     *
     * @param userId identifier of the user
     * @return persisted cart (header + items)
     * @throws IllegalStateException if cart creation or retrieval fails
     */
    public Cart getOrCreateCart(int userId) {
        int cartId = cartDao.getOrCreateCartId(userId);
        if (cartId <= 0) {
            throw new IllegalStateException("Une erreur est survenue lors de la création du panier.");
        }

        // Reload full cart (header & lines) to return an up-to-date snapshot
        return cartDao.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Une erreur est survenue lors de la récupération du panier."));
    }

    /**
     * Adds a training to the user's cart (or increments quantity if already
     * present).
     *
     * The unit price is taken from database (TrainingService),
     * to avoid trusting UI input (UI is cute, but not always trustworthy).
     *
     * @param userId     identifier of the user
     * @param trainingId identifier of the training
     * @param quantity   quantity to add (must be > 0)
     * @return updated cart
     * @throws IllegalArgumentException if quantity <= 0
     * @throws IllegalStateException    if cart creation/retrieval fails
     */
    public Cart addTraining(int userId, int trainingId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0.");
        }

        // Validate training exists & get the price from DB
        Training training = trainingService.getById(trainingId);
        BigDecimal unitPrice = training.getPrice();

        int cartId = cartDao.getOrCreateCartId(userId);
        if (cartId <= 0) {
            throw new IllegalStateException("Impossible de créer ou récupérer le panier.");
        }

        cartDao.addOrIncrement(cartId, trainingId, quantity, unitPrice);

        // Reload cart to return the latest persisted state
        return cartDao.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Une erreur est survenue lors de la récupération du panier."));
    }

    /**
     * Decrements quantity for a training in the cart.
     * If quantity becomes <= 0, the line is removed by the DAO.
     *
     * @param userId     identifier of the user
     * @param trainingId identifier of the training
     * @param delta      quantity to remove (must be > 0)
     * @return updated cart
     * @throws IllegalArgumentException  if delta <= 0
     * @throws TrainingNotFoundException if the training line does not exist in cart
     * @throws IllegalStateException     if cart creation/retrieval fails
     */
    public Cart decrementTraining(int userId, int trainingId, int delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("Le delta doit être supérieur à 0.");
        }

        int cartId = cartDao.getOrCreateCartId(userId);
        if (cartId <= 0) {
            throw new IllegalStateException("Impossible de créer ou récupérer le panier.");
        }

        boolean ok = cartDao.decrementOrRemove(cartId, trainingId, delta);
        if (!ok) {
            throw new TrainingNotFoundException("Formation introuvable : id=" + trainingId);
        }

        return cartDao.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Une erreur est survenue lors de la récupération du panier."));
    }

    /**
     * Removes a training line from the cart.
     *
     * @param userId     identifier of the user
     * @param trainingId identifier of the training
     * @return updated cart
     * @throws TrainingNotFoundException if the training line does not exist in cart
     * @throws IllegalStateException     if cart creation/retrieval fails
     */
    public Cart removeTraining(int userId, int trainingId) {
        int cartId = cartDao.getOrCreateCartId(userId);
        if (cartId <= 0) {
            throw new IllegalStateException("Impossible de créer ou récupérer le panier.");
        }

        boolean ok = cartDao.removeLine(cartId, trainingId);
        if (!ok) {
            throw new TrainingNotFoundException("Formation introuvable : id=" + trainingId);
        }

        return cartDao.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Une erreur est survenue lors de la récupération du panier."));
    }

    /**
     * Clears all cart lines for the user.
     *
     * @param userId identifier of the user
     * @return updated cart
     * @throws IllegalStateException if cart creation/retrieval fails
     */
    public Cart clear(int userId) {
        int cartId = cartDao.getOrCreateCartId(userId);
        if (cartId <= 0) {
            throw new IllegalStateException("Impossible de créer ou récupérer le panier.");
        }

        cartDao.clear(cartId);

        return cartDao.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Une erreur est survenue lors de la récupération du panier."));
    }

    /**
     * Lists all trainings from catalog.
     *
     * @return list of trainings
     */
    public List<Training> listTrainings() {
        return trainingService.listAll();
    }

    /**
     * Searches trainings by keyword (name/description).
     *
     * @param keyword keyword to search
     * @return list of matching trainings
     */
    public List<Training> searchTrainings(String keyword) {
        return trainingService.searchByKeyword(keyword);
    }

    /**
     * Filters trainings by onsite flag.
     *
     * @param onsite true for onsite trainings, false for remote
     * @return list of trainings matching the filter
     */
    public List<Training> filterTrainings(boolean onsite) {
        return trainingService.listByOnsite(onsite);
    }

    ////////////// HELPERS //////////////////

    /**
     * Throws an exception if the cart is null or empty.
     *
     * @param cart cart to validate
     * @throws CartEmptyException if cart is null or has no items
     */
    public void assertNotEmpty(Cart cart) {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new CartEmptyException("Le panier est vide.");
        }
    }

    /**
     * Loads the user's cart & checks it is not empty.
     *
     * @param userId identifier of the user
     * @return non-empty cart
     * @throws CartEmptyException    if cart is empty
     * @throws IllegalStateException if cart creation/retrieval fails
     */
    public Cart requireNonEmptyCart(int userId) {
        Cart cart = getOrCreateCart(userId);
        assertNotEmpty(cart);
        return cart;
    }
}
