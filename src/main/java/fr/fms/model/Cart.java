// src/main/java/fr/fms/model/Cart.java
package fr.fms.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private int userId;
    private LocalDateTime createdAt;
    private List<CartItem> items = new ArrayList<>();

    // getters/setters/constructors
}
