package com.order.application.service;

import com.order.domain.exceptions.InvalidBuyerDetailException;
import com.order.domain.exceptions.OrderNotFoundException;
import com.order.domain.exceptions.ProductNotFoundException;
import com.order.domain.models.*;
import com.order.domain.repositories.OrderRepository;
import com.order.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Order createOrder(final OrderId orderId, final BuyerDetails buyerDetails) {
        if (buyerDetails == null || buyerDetails.getSeat() == null) {
            throw new InvalidBuyerDetailException();
        }

        Order order = new Order(
                orderId,
                List.of(),
                new PaymentDetails(
                        BigDecimal.ZERO,
                        null,
                        null,
                        null,
                        null
                ),
                OrderStatus.OPEN,
                buyerDetails
        );

        orderRepository.save(order);
        return order;
    }

    public Optional<Order> getOrderById(final OrderId orderId) {
        return Optional.ofNullable(orderRepository.findById(orderId.getValue())
                .orElseThrow(() -> new OrderNotFoundException(orderId.getValue())));
    }

    public Order updateOrder(final OrderId orderId, final BuyerEmail newEmail, final List<UUID> productIds) {
        Order order = orderRepository.findById(orderId.getValue())
                .orElseThrow(() -> new OrderNotFoundException(orderId.getValue()));

        List<Product> products = productIds.stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException(orderId.getValue())))
                .toList();

        for (Product product : products) {
            if (product.getStock() < 1) {
                throw new IllegalStateException("Insufficient stock for product: " + product.getName());
            }
        }

        return order.updateBuyerAndProducts(newEmail, products);
    }

    public Order cancelOrder(final OrderId orderId) {
        Order order = orderRepository.findById(orderId.getValue())
                .orElseThrow(() -> new OrderNotFoundException(orderId.getValue()));

        return order.cancelOrder();
    }

    public Order finishOrder(
            final OrderId orderId,
            final PaymentStatus paymentStatus,
            final String cardToken,
            final String gateway
    ) {
        Order order = orderRepository.findById(orderId.getValue())
                .orElseThrow(() -> new OrderNotFoundException(orderId.getValue()));

        return order.finishOrder(paymentStatus, cardToken, gateway);
    }
}
