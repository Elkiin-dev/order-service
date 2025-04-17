package com.order.application.service;

import com.order.domain.exceptions.*;
import com.order.domain.models.*;
import com.order.domain.repositories.OrderRepository;
import com.order.domain.repositories.ProductRepository;
import com.order.infraestructure.services.PaymentGatewayService;
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
    private final PaymentGatewayService paymentGatewayService;

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

        order.orderIsOpen();

        List<Product> products = productIds.stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException(orderId.getValue())))
                .toList();

        order.checkProductStock(products);

        Order orderUpdated = order.updateBuyerAndProducts(newEmail, products);

        orderRepository.save(orderUpdated);

        return orderUpdated;
    }

    public Order cancelOrder(final OrderId orderId) {
        Order order = orderRepository.findById(orderId.getValue())
                .orElseThrow(() -> new OrderNotFoundException(orderId.getValue()));

        order.orderIsOpen();

        Order orderCanceled = order.cancelOrder();

        orderRepository.update(orderCanceled);

        return orderCanceled;
    }

    public Order finishOrder(
            final OrderId orderId,
            final String cardToken,
            final String gateway
    ) {
        Order order = orderRepository.findById(orderId.getValue())
                .orElseThrow(() -> new OrderNotFoundException(orderId.getValue()));

        order.orderIsOpen();

        PaymentDetails payment = paymentGatewayService.processPayment(
                order.getPaymentDetails().getTotalPrice(),
                cardToken,
                gateway
        );

        recalculateStock(payment, order);

        Order orderFinished = order.finishOrder(payment);

        orderRepository.update(orderFinished);

        return orderFinished;
    }

    private void recalculateStock(PaymentDetails payment, Order order) {
        if (payment.getPaymentStatus() == PaymentStatus.PAID || payment.getPaymentStatus() == PaymentStatus.OFFLINE_PAYMENT) {
            for (Product product : order.getProducts()) {
                int newStock = product.getStock() - 1;
                productRepository.updateStock(product.getProductId().getValue(), newStock);
            }
        }
    }
}
