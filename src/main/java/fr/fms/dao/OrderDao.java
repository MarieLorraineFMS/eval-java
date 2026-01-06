// src/main/java/fr/fms/dao/OrderDao.java
package fr.fms.dao;

import fr.fms.model.Order;

public interface OrderDao {
    int create(Order order);
}
