package com.order.domain.exceptions;

import com.order.domain.models.OrderStatus;
import com.order.shared.domain.exceptions.DomainRuleViolation;

public class InvalidCancelOrderException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "Can not cancel order with status %s";

    public InvalidCancelOrderException(OrderStatus status) {
        super(String.format(MESSAGE_PATTER,  status), HTTP_STATUS);
    }
}
