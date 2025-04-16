package com.order.infraestructure.persistence.mappers;

import com.order.domain.models.BuyerDetails;
import com.order.domain.models.BuyerEmail;
import com.order.domain.models.Seat;
import com.order.infraestructure.persistence.embeddables.BuyerDetailsEmbeddable;
import jakarta.persistence.Embeddable;

@Embeddable
public class BuyerDetailsMapper {

    private BuyerDetailsMapper() {
    }

    public static BuyerDetails toDomain(BuyerDetailsEmbeddable embeddable) {
        return new BuyerDetails(
                new BuyerEmail(embeddable.getBuyerEmail()),
                new Seat(embeddable.getSeatLetter(), embeddable.getSeatNumber())
        );
    }

    public static BuyerDetailsEmbeddable toEmbeddable(BuyerDetails buyerDetails) {
        return  new BuyerDetailsEmbeddable(
                buyerDetails.getBuyerEmail().getValue(),
                buyerDetails.getSeat().getLetter(),
                buyerDetails.getSeat().getNumber());
    }
}
