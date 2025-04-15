package com.order.infraestructure.persistence;

import com.order.infraestructure.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, String> {
}
