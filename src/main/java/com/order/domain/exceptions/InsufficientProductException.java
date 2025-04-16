package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

public class InsufficientProductException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "Insufficient stock for product: %s.";

    public InsufficientProductException(final String name) {
        super(String.format(MESSAGE_PATTER, name), HTTP_STATUS);
    }
}
