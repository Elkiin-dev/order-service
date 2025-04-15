package com.order.infraestructure.persistence.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class BuyerDetailsEmbeddable {
    private final String buyerEmail;
    private final String seatLetter;
    private final Integer seatNumber;

    public BuyerDetailsEmbeddable(final String buyerEmail, final String seatLetter, final Integer seatNumber) {
        this.buyerEmail = buyerEmail;
        this.seatLetter = seatLetter;
        this.seatNumber = seatNumber;
    }
}
