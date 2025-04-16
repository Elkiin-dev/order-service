package com.order.infraestructure.persistence.embeddables;

import com.order.domain.models.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PaymentDetailsEmbeddable {
    private  BigDecimal totalPrice;
    private  String cardToken;
    @Enumerated(EnumType.STRING)
    private  PaymentStatus paymentStatus;
    private  LocalDateTime localDateTime;
    private  String paymentGateway;

    private PaymentDetailsEmbeddable (){}

    public PaymentDetailsEmbeddable(
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
