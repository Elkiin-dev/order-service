package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

public class InvalidSeatLetterException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "Seat letter is required.";

    public InvalidSeatLetterException() {
        super(String.format(MESSAGE_PATTER), HTTP_STATUS);
    }
}
