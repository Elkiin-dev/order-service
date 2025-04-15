package com.order.domain.models;

import lombok.Getter;

@Getter
public class BuyerDetails {
    private final BuyerEmail buyerEmail;
    private final Seat seat;

    public BuyerDetails(final BuyerEmail buyerEmail, Seat seat) {
        this.buyerEmail = buyerEmail;
        this.seat = seat;
    }
}
