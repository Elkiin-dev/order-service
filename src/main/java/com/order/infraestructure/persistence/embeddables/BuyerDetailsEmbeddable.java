package com.order.infraestructure.persistence.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class BuyerDetailsEmbeddable {
    private String buyerEmail;
    private String seatLetter;
    private Integer seatNumber;

    private BuyerDetailsEmbeddable () {}

    public BuyerDetailsEmbeddable(final String buyerEmail, final String seatLetter, final Integer seatNumber) {
        this.buyerEmail = buyerEmail;
        this.seatLetter = seatLetter;
        this.seatNumber = seatNumber;
    }
}
