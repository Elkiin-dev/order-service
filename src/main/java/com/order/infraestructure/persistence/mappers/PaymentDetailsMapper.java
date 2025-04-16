package com.order.infraestructure.persistence.mappers;

import com.order.domain.models.PaymentDetails;
import com.order.infraestructure.persistence.embeddables.PaymentDetailsEmbeddable;
import jakarta.persistence.Embeddable;

@Embeddable
public class PaymentDetailsMapper {

    private PaymentDetailsMapper (){}

    public static PaymentDetails toDomain(PaymentDetailsEmbeddable embeddable) {
        return new PaymentDetails(
                embeddable.getTotalPrice(),
                embeddable.getCardToken(),
                embeddable.getPaymentStatus(),
                embeddable.getLocalDateTime(),
                embeddable.getPaymentGateway()
        );
    }

    public static PaymentDetailsEmbeddable toEmbeddable(PaymentDetails paymentDetails) {
        return new PaymentDetailsEmbeddable(
                paymentDetails.getTotalPrice(),
                paymentDetails.getCardToken(),
                paymentDetails.getPaymentStatus(),
                paymentDetails.getLocalDateTime(),
                paymentDetails.getPaymentGateway()
        );
    }
}

