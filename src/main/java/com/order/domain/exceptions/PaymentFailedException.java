package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

import java.util.UUID;

public class PaymentFailedException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "Payment failed";

    public PaymentFailedException() {
        super(String.format(MESSAGE_PATTER), HTTP_STATUS);
    }
}
