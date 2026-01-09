package fr.fms.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import fr.fms.dao.CartDao;
import fr.fms.dao.ClientDao;
import fr.fms.dao.OrderDao;
import fr.fms.exception.OrderException;
import fr.fms.model.Cart;
import fr.fms.model.Client;
import fr.fms.model.Order;
import fr.fms.model.OrderLine;
import fr.fms.model.OrderStatus;

import static fr.fms.utils.Helpers.isNullOrEmpty;

/**
 * Order service.
 *
 * Responsibilities:
 * - checkout: convert a cart into a confirmed order (with lines)
 * - create or reuse client data
 * - list orders for a user
 * - list/search clients for UI needs
 *
 * "commit" part of the app:
 * once checkout succeeds, the cart becomes an order
 */
public class OrderService {

    /** DAO used to persist & read orders */
    private final OrderDao orderDao;

    /** DAO used to persist & read clients */
    private final ClientDao clientDao;

    /** DAO used to clear cart lines after checkout */
    private final CartDao cartDao;

    /** Service used to ensure cart exists & is not empty */
    private final CartService cartService;

    /**
     * Builds OrderService with required dependencies.
     *
     * @param orderDao    DAO used for order persistence
     * @param clientDao   DAO used for client persistence
     * @param cartDao     DAO used for cart persistence
     * @param cartService service used to validate cart state
     */
    public OrderService(OrderDao orderDao, ClientDao clientDao, CartDao cartDao, CartService cartService) {
        this.orderDao = orderDao;
        this.clientDao = clientDao;
        this.cartDao = cartDao;
        this.cartService = cartService;
    }

    /**
     * Performs checkout:
     * - requires a non-empty cart
     * - creates or reuses a client
     * - converts cart items into order lines
     * - creates order + lines in one transaction
     * - clears the cart after success
     *
     * @param userId      identifier of the user doing the checkout
     * @param clientDraft client data provided by UI (may be reused if email already
     *                    exists)
     * @return created Order including its lines
     * @throws OrderException if checkout cannot be completed
     */
    public Order checkout(int userId, Client clientDraft) {
        // Cart must exist & must not be empty
        Cart cart = cartService.requireNonEmptyCart(userId);

        // Client is required
        Client client = getOrCreateClient(clientDraft);

        // Convert cart items => order lines
        List<OrderLine> lines = cart.getItems().stream()
                .map(i -> new OrderLine(
                        0,
                        i.getTraining(),
                        i.getQuantity(),
                        i.getUnitPrice()))
                .toList();

        // Total computed from cart snapshot
        BigDecimal total = cart.getTotal();

        // Create order & lines in a single transaction
        Order order = new Order(userId, client, LocalDateTime.now(), OrderStatus.CONFIRMED, total);
        int orderId = orderDao.createOrderWithLines(order, lines);

        if (orderId <= 0) {
            throw new OrderException("Checkout failed: cannot create order.");
        }

        // Clear cart after checkout
        int cartId = cartDao.getOrCreateCartId(userId);
        cartDao.clear(cartId);

        // Return full order fo CLI
        Order created = new Order(orderId, userId, client, order.getCreatedAt(), order.getStatus(), total);
        lines.forEach(created::addLine);

        return created;
    }

    /**
     * Creates a client if needed, or reuses an existing.
     *
     * Rules:
     * - draft must not be null
     * - firstName, lastName & email are required
     * - email is normalized (trim + lowercase)
     * - if email already exists => reuse existing client to avoid duplicates
     *
     * @param draft client data provided by UI
     * @return existing or new created client
     * @throws OrderException if draft is invalid or persistence fails
     */
    private Client getOrCreateClient(Client draft) {
        if (draft == null) {
            throw new OrderException("Client is required.");
        }

        // Minimal required fields
        if (isNullOrEmpty(draft.getFirstName()) || isNullOrEmpty(draft.getLastName())) {
            throw new OrderException("Client firstName/lastName required.");
        }
        if (isNullOrEmpty(draft.getEmail())) {
            throw new OrderException("Client email required.");
        }

        // Normalize email to keep db consistent
        String email = draft.getEmail().trim().toLowerCase();

        // Nice move: if email already exists => reuse it instead of duplicating clients
        return clientDao.findByEmail(email)
                .orElseGet(() -> {
                    int id = clientDao.create(new Client(
                            draft.getFirstName().trim(),
                            draft.getLastName().trim(),
                            email,
                            draft.getAddress(),
                            draft.getPhone()));

                    if (id <= 0) {
                        throw new OrderException("Cannot create client (DB error).");
                    }

                    // Reload from DB to return the official stored version
                    return clientDao.findById(id)
                            .orElseThrow(() -> new OrderException("Client created but not found (unexpected)."));
                });
    }

    /**
     * Lists all orders created by a user.
     *
     * @param userId identifier of the user
     * @return list of orders
     */
    public List<Order> listByUserId(int userId) {
        return orderDao.findByUserId(userId);
    }

    /**
     * Lists all clients.
     *
     * @return list of clients
     */
    public List<Client> listClients() {
        return clientDao.findAll();
    }

    /**
     * Retrieves a client by id.
     *
     * @param id client identifier
     * @return Optional containing the Client if found, otherwise Optional.empty()
     */
    public Optional<Client> getClientById(int id) {
        return clientDao.findById(id);
    }

    /**
     * Creates a client or reuses an existing.
     * Public wrapper around the internal helper.
     *
     * @param draft client data provided by UI
     * @return existing or newly created client
     */
    public Client createOrReuseClient(Client draft) {
        return getOrCreateClient(draft);
    }
}
