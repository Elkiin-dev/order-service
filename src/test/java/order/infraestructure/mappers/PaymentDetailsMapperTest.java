package order.infraestructure.mappers;

import com.order.domain.models.PaymentDetails;
import com.order.domain.models.PaymentStatus;
import com.order.infraestructure.persistence.embeddables.PaymentDetailsEmbeddable;
import com.order.infraestructure.persistence.mappers.PaymentDetailsMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentDetailsMapperTest {

    @Test
    void testToDomain() {
        PaymentDetailsEmbeddable embeddable = new PaymentDetailsEmbeddable(
                BigDecimal.valueOf(100.0), "token123", PaymentStatus.PAID, LocalDateTime.now(), "PayPal"
        );
        PaymentDetails paymentDetails = PaymentDetailsMapper.toDomain(embeddable);

        assertEquals(BigDecimal.valueOf(100.0), paymentDetails.getTotalPrice());
        assertEquals("token123", paymentDetails.getCardToken());
        assertEquals(PaymentStatus.PAID, paymentDetails.getPaymentStatus());
        assertEquals("PayPal", paymentDetails.getPaymentGateway());
    }

    @Test
    void testToEmbeddable() {
        PaymentDetails paymentDetails = new PaymentDetails(
                BigDecimal.valueOf(100.0), "token123", PaymentStatus.PAID, LocalDateTime.now(), "PayPal"
        );
        PaymentDetailsEmbeddable embeddable = PaymentDetailsMapper.toEmbeddable(paymentDetails);

        assertEquals(BigDecimal.valueOf(100.0), embeddable.getTotalPrice());
        assertEquals("token123", embeddable.getCardToken());
        assertEquals(PaymentStatus.PAID, embeddable.getPaymentStatus());
        assertEquals("PayPal", embeddable.getPaymentGateway());
    }
}
