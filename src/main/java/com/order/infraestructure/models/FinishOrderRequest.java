package com.order.infraestructure.models;

import com.order.domain.models.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FinishOrderRequest {
    private PaymentStatus paymentStatus;
    private String cardToken;
    private String paymentGateway;
}
