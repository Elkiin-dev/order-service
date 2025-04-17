package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

public class InvalidSeatLengthException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "The seat number must be greater than 0";

    public InvalidSeatLengthException() {
        super(String.format(MESSAGE_PATTER), HTTP_STATUS);
    }
}
