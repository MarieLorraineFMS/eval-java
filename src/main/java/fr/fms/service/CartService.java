package fr.fms.service;

import java.math.BigDecimal;

import fr.fms.dao.CartDao;
import fr.fms.exception.CartEmptyException;
import fr.fms.exception.TrainingNotFoundException;
import fr.fms.model.Cart;
import fr.fms.model.Training;

public class CartService {
    private final CartDao cartDao;
    private final TrainingService trainingService;

    public CartService(CartDao cartDao, TrainingService trainingService) {
        this.cartDao = cartDao;
        this.trainingService = trainingService;
    }

    // Return a persisted cart
    public Cart getOrCreateCart(int userId) {
        int cartId = cartDao.getOrCreateCartId(userId);
        if (cartId <= 0) {
            throw new IllegalStateException("Une erreur est survenue lors de la création du panier.");
        }

        // Reload full cart (header & lines)
        return cartDao.findByUserId(userId)
                .orElseThrow(
                        () -> new IllegalStateException("Une erreur est survenue lors de la récupération du panier."));
    }

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

        return cartDao.findByUserId(userId)
                .orElseThrow(
                        () -> new IllegalStateException("Une erreur est survenue lors de la récupération du panier."));
    }

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
                .orElseThrow(
                        () -> new IllegalStateException("Une erreur est survenue lors de la récupération du panier."));
    }

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
                .orElseThrow(
                        () -> new IllegalStateException("Une erreur est survenue lors de la récupération du panier."));
    }

    public Cart clear(int userId) {
        int cartId = cartDao.getOrCreateCartId(userId);
        if (cartId <= 0) {
            throw new IllegalStateException("Impossible de créer ou récupérer le panier.");
        }

        cartDao.clear(cartId);

        return cartDao.findByUserId(userId)
                .orElseThrow(
                        () -> new IllegalStateException("Une erreur est survenue lors de la récupération du panier."));

    }

    ////////////// HELPER //////////////////

    public void assertNotEmpty(Cart cart) {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new CartEmptyException("Le panier est vide.");
        }
    }

    public Cart requireNonEmptyCart(int userId) {
        Cart cart = getOrCreateCart(userId);
        assertNotEmpty(cart);
        return cart;
    }
}
