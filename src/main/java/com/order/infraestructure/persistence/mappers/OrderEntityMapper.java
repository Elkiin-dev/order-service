package com.order.infraestructure.persistence.mappers;

import com.order.domain.models.Order;
import com.order.domain.models.OrderId;
import com.order.infraestructure.persistence.entities.OrderEntity;

import java.util.UUID;

public class OrderEntityMapper {

    public static Order toDomain(OrderEntity entity) {
        return new Order(
                new OrderId(UUID.fromString(String.valueOf(entity.getOrderId()))),
                entity.getProducts().stream()
                        .map(ProductEntityMapper::toDomain)
                        .toList(),
                PaymentDetailsMapper.toDomain(entity.getPaymentDetails()),
                entity.getStatus(),
                BuyerDetailsMapper.toDomain(entity.getBuyerDetails())
        );
    }

    public static OrderEntity toEntity(Order order) {
        return new OrderEntity(
                order.getOrderId().getValue(),
                order.getProducts().stream()
                        .map(ProductEntityMapper::toEntity)
                        .toList(),
                PaymentDetailsMapper.toEmbeddable(order.getPaymentDetails()),
                order.getStatus(),
                BuyerDetailsMapper.toEmbeddable(order.getBuyerDetails())
        );
    }
}
