package order.infraestructure.controllers;

import com.order.application.service.OrderService;
import com.order.domain.models.*;
import com.order.domain.repositories.OrderRepository;
import com.order.domain.repositories.ProductRepository;
import com.order.infraestructure.controllers.OrderController;
import com.order.infraestructure.models.CreateOrderRequest;
import com.order.infraestructure.models.FinishOrderRequest;
import com.order.infraestructure.models.UpdateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;

    private OrderId orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderId = new OrderId(UUID.randomUUID());
        BuyerEmail email = new BuyerEmail("test@example.com");
        Seat seat = new Seat("A", 12);
        BuyerDetails buyerDetails = new BuyerDetails(email, seat);

        order = new Order(orderId, List.of(), null, OrderStatus.FINISHED, buyerDetails);
    }

    @Test
    void testCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest("test@example.com", "A", 12);
        when(orderService.createOrder(any(OrderId.class), any(BuyerDetails.class))).thenReturn(order);

        ResponseEntity<Order> response = orderController.createOrder(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getBuyerDetails().getBuyerEmail().getValue());
        assertEquals("A", response.getBody().getBuyerDetails().getSeat().getLetter());
        assertEquals(12, response.getBody().getBuyerDetails().getSeat().getNumber());
    }

    @Test
    void testGetOrder() {
        when(orderService.getOrderById(any(OrderId.class))).thenReturn(java.util.Optional.of(order));

        ResponseEntity<Order> response = orderController.getOrder(orderId.getValue());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderId.getValue(), response.getBody().getOrderId().getValue());
    }

    @Test
    void testGetOrderNotFound() {
        when(orderService.getOrderById(any(OrderId.class))).thenReturn(java.util.Optional.empty());

        ResponseEntity<Order> response = orderController.getOrder(orderId.getValue());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateOrder() {
        Category category = new Category(new CategoryId(), "testCategory", null);
        ProductId productId = new ProductId();
        Product product = new Product(productId, "Test Product", BigDecimal.valueOf(10.00), category, "test.png", 10);

        List<UUID> productIds = List.of(productId.getValue());

        BuyerEmail newEmail = new BuyerEmail("test@example.com");
        Seat seat = new Seat("A", 12);
        BuyerDetails buyerDetails = new BuyerDetails(newEmail, seat);

        Order order = new Order(orderId, List.of(product), null, OrderStatus.FINISHED, buyerDetails);

        when(orderRepository.findById(orderId.getValue())).thenReturn(Optional.of(order));
        when(productRepository.findById(eq(productId.getValue()))).thenReturn(Optional.of(product));

        when(orderService.updateOrder(any(OrderId.class), any(BuyerEmail.class), anyList())).thenReturn(order); // Devuelve la orden actualizada

        UpdateOrderRequest request = new UpdateOrderRequest("test@example.com", productIds);

        ResponseEntity<Order> response = orderController.updateOrder(orderId.getValue(), request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getBuyerDetails().getBuyerEmail().getValue());
        assertNotNull(response.getBody().getProducts());
        assertFalse(response.getBody().getProducts().isEmpty());
        assertEquals(productId.getValue(), response.getBody().getProducts().get(0).getProductId().getValue());
    }


    @Test
    void testFinishOrder() {
        FinishOrderRequest request = new FinishOrderRequest(PaymentStatus.PAID, "token123", "PayPal");

        when(orderService.finishOrder(any(OrderId.class), eq("token123"), eq("PayPal"))).thenReturn(order);

        ResponseEntity<Order> response = orderController.finishOrder(orderId.getValue(), request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderStatus.FINISHED, response.getBody().getStatus());
    }


    @Test
    void testCancelOrder() {
        when(orderService.cancelOrder(any(OrderId.class))).thenReturn(order);

        ResponseEntity<Order> response = orderController.cancelOrder(orderId.getValue());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderStatus.FINISHED, response.getBody().getStatus());
    }
}
