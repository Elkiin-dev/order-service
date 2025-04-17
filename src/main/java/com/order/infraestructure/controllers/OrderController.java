package com.order.infraestructure.controllers;

import com.order.application.service.OrderService;
import com.order.domain.models.*;
import com.order.infraestructure.models.CreateOrderRequest;
import com.order.infraestructure.models.FinishOrderRequest;
import com.order.infraestructure.models.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Validated final CreateOrderRequest request) {
        OrderId orderId = new OrderId();
        BuyerEmail email = new BuyerEmail(request.getBuyerEmail());
        Seat seat = new Seat(request.getSeatLetter(), request.getSeatNumber());
        BuyerDetails buyerDetails = new BuyerDetails(email, seat);

        Order order = service.createOrder(orderId, buyerDetails);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID orderId) {
        return service.getOrderById(new OrderId(orderId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable UUID orderId,
                                             @RequestBody @Validated final UpdateOrderRequest request) {
        BuyerEmail email = new BuyerEmail(request.getBuyerEmail());
        Order updated = service.updateOrder(new OrderId(orderId), email, request.getProductIds());
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{orderId}/finish")
    public ResponseEntity<Order> finishOrder(@PathVariable UUID orderId,
                                             @RequestBody @Validated final FinishOrderRequest request) {
        Order finished = service.finishOrder(
                new OrderId(orderId),
                request.getCardToken(),
                request.getPaymentGateway()
        );
        return ResponseEntity.ok(finished);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable UUID orderId) {
        Order canceled = service.cancelOrder(new OrderId(orderId));
        return ResponseEntity.ok(canceled);
    }
}
