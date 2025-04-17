package com.order.domain.models;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PaymentDetails {
    private final BigDecimal totalPrice;
    private final String cardToken;
    private final PaymentStatus paymentStatus;
    private final LocalDateTime localDateTime;
    private final String paymentGateway;

    public PaymentDetails(
            final BigDecimal totalPrice,
            final String cardToken,
            final PaymentStatus paymentStatus,
            final LocalDateTime localDateTime,
            final String paymentGateway
    ) {
        this.totalPrice = totalPrice;
        this.cardToken = cardToken;
        this.paymentStatus = paymentStatus;
        this.localDateTime = localDateTime;
        this.paymentGateway = paymentGateway;
    }
}
