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

public class OrderService {
    private final OrderDao orderDao;
    private final ClientDao clientDao;
    private final CartDao cartDao;
    private final CartService cartService;

    public OrderService(OrderDao orderDao, ClientDao clientDao, CartDao cartDao, CartService cartService) {
        this.orderDao = orderDao;
        this.clientDao = clientDao;
        this.cartDao = cartDao;
        this.cartService = cartService;
    }

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

        // Clear cart after successful checkout
        int cartId = cartDao.getOrCreateCartId(userId);
        cartDao.clear(cartId);

        // Return full order for CLI
        Order created = new Order(orderId, userId, client, order.getCreatedAt(), order.getStatus(), total);
        lines.forEach(created::addLine);

        return created;
    }

    private Client getOrCreateClient(Client draft) {
        if (draft == null) {
            throw new OrderException("Client is required.");
        }

        if (isNullOrEmpty(draft.getFirstName()) || isNullOrEmpty(draft.getLastName())) {
            throw new OrderException("Client firstName/lastName required.");
        }
        if (isNullOrEmpty(draft.getEmail())) {
            throw new OrderException("Client email required.");
        }

        String email = draft.getEmail().trim().toLowerCase();

        // Pro: if email already exists => reuse the existing client instead of
        // duplicating
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

                    return clientDao.findById(id)
                            .orElseThrow(() -> new OrderException("Client created but not found (unexpected)."));
                });
    }

    public List<Order> listByUserId(int userId) {
        return orderDao.findByUserId(userId);
    }

    // list all clients
    public List<Client> listClients() {
        return clientDao.findAll();
    }

    // get a client by id
    public Optional<Client> getClientById(int id) {
        return clientDao.findById(id);
    }

    // Create or reuse a client
    public Client createOrReuseClient(Client draft) {
        return getOrCreateClient(draft);
    }

}
