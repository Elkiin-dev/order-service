package com.order.infraestructure.persistence.embeddables;

import com.order.domain.models.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PaymentDetailsEmbeddable {
    private final BigDecimal totalPrice;
    private final String cardToken;
    @Enumerated(EnumType.STRING)
    private final PaymentStatus paymentStatus;
    private final LocalDateTime localDateTime;
    private final String paymentGateway;

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
