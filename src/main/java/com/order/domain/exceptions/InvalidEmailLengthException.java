package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

public class InvalidEmailLengthException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "The email must not be empty ";

    public InvalidEmailLengthException() {
        super(String.format(MESSAGE_PATTER), HTTP_STATUS);
    }
}
