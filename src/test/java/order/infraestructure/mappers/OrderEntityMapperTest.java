package order.infraestructure.mappers;

import com.order.domain.models.*;
import com.order.infraestructure.persistence.embeddables.BuyerDetailsEmbeddable;
import com.order.infraestructure.persistence.embeddables.PaymentDetailsEmbeddable;
import com.order.infraestructure.persistence.entities.OrderEntity;
import com.order.infraestructure.persistence.mappers.OrderEntityMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderEntityMapperTest {

    @Test
    void testToDomain() {
        UUID id = new OrderId().getValue();
        OrderEntity entity = new OrderEntity(
                id,
                List.of(),
                new PaymentDetailsEmbeddable(BigDecimal.valueOf(100.0), "token", PaymentStatus.PAID, LocalDateTime.now(), "gateway"),
                OrderStatus.FINISHED,
                new BuyerDetailsEmbeddable("test@example.com", "A", 12)
        );

        Order order = OrderEntityMapper.toDomain(entity);

        assertEquals(id, order.getOrderId().getValue());
        assertEquals(OrderStatus.FINISHED, order.getStatus());
        assertEquals("test@example.com", order.getBuyerDetails().getBuyerEmail().getValue());
        assertEquals("A", order.getBuyerDetails().getSeat().getLetter());
        assertEquals(12, order.getBuyerDetails().getSeat().getNumber());
    }

    @Test
    void testToEntity() {
        OrderId id = new OrderId();

        Order order = new Order(
                id,
                List.of(),
                new PaymentDetails(BigDecimal.valueOf(100.0), "token", PaymentStatus.PAID, LocalDateTime.now(), "gateway"),
                OrderStatus.FINISHED,
                new BuyerDetails(new BuyerEmail("test@example.com"), new Seat("A", 12))
        );

        OrderEntity entity = OrderEntityMapper.toEntity(order);

        assertEquals(id.getValue(), entity.getOrderId());
        assertEquals(OrderStatus.FINISHED, entity.getStatus());
        assertEquals("test@example.com", entity.getBuyerDetails().getBuyerEmail());
        assertEquals("A", entity.getBuyerDetails().getSeatLetter());
        assertEquals(12, entity.getBuyerDetails().getSeatNumber());
    }
}
