package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

import java.util.UUID;

public class OrderNotFoundException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 404;
    public static final String MESSAGE_PATTER = "The order with id %s was not found";

    public OrderNotFoundException(final UUID orderId) {
        super(String.format(MESSAGE_PATTER, orderId), HTTP_STATUS);
    }
}
