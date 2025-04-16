package com.order.infraestructure.services.impl;

import com.order.domain.models.PaymentDetails;
import com.order.domain.models.PaymentStatus;
import com.order.infraestructure.services.PaymentGatewayService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MockPaymentGatewayService implements PaymentGatewayService {

    private static final PaymentStatus[] STATUSES = PaymentStatus.values();
    private static final Random RANDOM = new Random();

    @Override
    public PaymentDetails processPayment(BigDecimal amount, String cardToken, String gateway) {
        PaymentStatus randomStatus = STATUSES[RANDOM.nextInt(STATUSES.length)];

        return new PaymentDetails(
                amount,
                cardToken,
                randomStatus,
                LocalDateTime.now(),
                gateway
        );
    }
}
