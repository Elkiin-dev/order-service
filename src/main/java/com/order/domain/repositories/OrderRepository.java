package com.order.domain.repositories;


import com.order.domain.models.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> findById(UUID orderId);
    void save(Order order);
    void update(Order order);
}
