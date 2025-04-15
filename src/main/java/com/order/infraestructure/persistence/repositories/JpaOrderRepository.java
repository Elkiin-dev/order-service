package com.order.infraestructure.persistence.repositories;

import com.order.domain.models.Order;
import com.order.domain.repositories.OrderRepository;
import com.order.infraestructure.persistence.SpringDataOrderRepository;
import com.order.infraestructure.persistence.mappers.OrderEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaOrderRepository implements OrderRepository {

    private final SpringDataOrderRepository repository;


    @Override
    public Optional<Order> findById(UUID orderId) {
        return repository.findById(orderId.toString())
                .map(OrderEntityMapper::toDomain);
    }

    @Override
    @Transactional
    public void save(Order order) {
        repository.save(OrderEntityMapper.toEntity(order));
    }

    @Override
    @Transactional
    public void update(Order order) {
        repository.save(OrderEntityMapper.toEntity(order));
    }
}

