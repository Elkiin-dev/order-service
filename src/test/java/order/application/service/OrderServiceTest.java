package order.application.service;

import com.order.application.service.OrderService;
import com.order.domain.exceptions.PaymentFailedException;
import com.order.domain.models.*;
import com.order.domain.repositories.OrderRepository;
import com.order.domain.repositories.ProductRepository;
import com.order.infraestructure.services.PaymentGatewayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

 class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    private PaymentGatewayService paymentGatewayService;

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    private Product sampleProduct;
    private BuyerDetails buyerDetails;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        paymentGatewayService = mock(PaymentGatewayService.class);

        orderService = new OrderService(productRepository, orderRepository, paymentGatewayService);

        sampleProduct = new Product(
                new ProductId(UUID.randomUUID()),
                "Coca-Cola",
                BigDecimal.valueOf(2.5),
                new Category(new CategoryId(UUID.randomUUID()), "Drinks", null),
                "img.jpg",
                10
        );

        buyerDetails = new BuyerDetails(
                new BuyerEmail("user@example.com"),
                new Seat("A", 5)
        );

    }

    @Test
    void shouldCreateOrderWithBuyer() {
        OrderId orderId = new OrderId();

        Order order = orderService.createOrder(orderId, buyerDetails);

        assertNotNull(order);
        assertEquals(OrderStatus.OPEN, order.getStatus());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldUpdateOrderWithValidProducts() {
        OrderId orderId = new OrderId();
        Order existing = new Order(
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

        when(orderRepository.findById(orderId.getValue())).thenReturn(Optional.of(existing));
        when(productRepository.findById(sampleProduct.getProductId().getValue())).thenReturn(Optional.of(sampleProduct));

        Order updated = orderService.updateOrder(orderId, new BuyerEmail("updated@mail.com"), List.of(sampleProduct.getProductId().getValue()));

        assertEquals("updated@mail.com", updated.getBuyerDetails().getBuyerEmail().getValue());
        assertEquals(1, updated.getProducts().size());
        verify(orderRepository).save(updated);
    }

    @Test
    void shouldCancelOrderIfOpen() {
        OrderId orderId = new OrderId();
        Order openOrder = new Order(
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

        when(orderRepository.findById(orderId.getValue())).thenReturn(Optional.of(openOrder));

        Order canceled = orderService.cancelOrder(orderId);

        assertEquals(OrderStatus.DROPPED, canceled.getStatus());
        verify(orderRepository).update(canceled);
    }

    @Test
    void shouldFinishOrderIfPaymentIsSuccessful() {
        OrderId orderId = new OrderId();
        Order openOrder = new Order(
                orderId,
                List.of(sampleProduct),
                new PaymentDetails(sampleProduct.getPrice(), null, null, null, null),
                OrderStatus.OPEN,
                buyerDetails
        );

        PaymentDetails payment = new PaymentDetails(
                sampleProduct.getPrice(),
                "card_123",
                PaymentStatus.PAID,
                LocalDateTime.now(),
                "MockGateway"
        );

        when(orderRepository.findById(orderId.getValue())).thenReturn(Optional.of(openOrder));
        when(paymentGatewayService.processPayment(any(), any(), any())).thenReturn(payment);

        Order finished = orderService.finishOrder(orderId, "card_123", "MockGateway");

        assertEquals(OrderStatus.FINISHED, finished.getStatus());
        verify(orderRepository).update(finished);
        verify(productRepository).updateStock(sampleProduct.getProductId().getValue(), 9);
    }

    @Test
    void shouldNotFinishOrderIfPaymentFails() {
        OrderId orderId = new OrderId();
        Order openOrder = new Order(
                orderId,
                List.of(sampleProduct),
                new PaymentDetails(sampleProduct.getPrice(), null, null, null, null),
                OrderStatus.OPEN,
                buyerDetails
        );

        PaymentDetails failedPayment = new PaymentDetails(
                sampleProduct.getPrice(),
                "card_123",
                PaymentStatus.PAYMENT_FAILED,
                LocalDateTime.now(),
                "MockGateway"
        );

        when(orderRepository.findById(orderId.getValue())).thenReturn(Optional.of(openOrder));
        when(paymentGatewayService.processPayment(any(), any(), any())).thenReturn(failedPayment);

        assertThrows(PaymentFailedException.class, () -> {
            orderService.finishOrder(orderId, "card_123", "MockGateway");
        });

        verify(orderRepository, never()).update(any());
        verify(productRepository, never()).updateStock(any(), anyInt());
    }
}
