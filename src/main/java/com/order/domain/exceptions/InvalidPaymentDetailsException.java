package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

public class InvalidPaymentDetailsException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "Payment information is required to finish the order.";

    public InvalidPaymentDetailsException() {
        super(String.format(MESSAGE_PATTER), HTTP_STATUS);
    }
}
