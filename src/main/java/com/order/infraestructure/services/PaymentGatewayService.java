package com.order.infraestructure.services;

import com.order.domain.models.PaymentDetails;

import java.math.BigDecimal;

public interface PaymentGatewayService {
    PaymentDetails processPayment(BigDecimal amount, String cardToken, String gateway);
}
