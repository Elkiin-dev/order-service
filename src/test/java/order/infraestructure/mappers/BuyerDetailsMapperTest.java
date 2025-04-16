package order.infraestructure.mappers;

import com.order.domain.models.BuyerDetails;
import com.order.domain.models.BuyerEmail;
import com.order.domain.models.Seat;
import com.order.infraestructure.persistence.embeddables.BuyerDetailsEmbeddable;
import com.order.infraestructure.persistence.mappers.BuyerDetailsMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuyerDetailsMapperTest {

    @Test
    void testToDomain() {
        BuyerDetailsEmbeddable embeddable = new BuyerDetailsEmbeddable("test@example.com", "A", 12);
        BuyerDetails buyerDetails = BuyerDetailsMapper.toDomain(embeddable);

        assertEquals("test@example.com", buyerDetails.getBuyerEmail().getValue());
        assertEquals("A", buyerDetails.getSeat().getLetter());
        assertEquals(12, buyerDetails.getSeat().getNumber());
    }

    @Test
    void testToEmbeddable() {
        BuyerDetails buyerDetails = new BuyerDetails(new BuyerEmail("test@example.com"), new Seat("A", 12));
        BuyerDetailsEmbeddable embeddable = BuyerDetailsMapper.toEmbeddable(buyerDetails);

        assertEquals("test@example.com", embeddable.getBuyerEmail());
        assertEquals("A", embeddable.getSeatLetter());
        assertEquals(12, embeddable.getSeatNumber());
    }
}
