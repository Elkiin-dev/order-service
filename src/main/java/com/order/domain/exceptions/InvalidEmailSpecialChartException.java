package com.order.domain.exceptions;


import com.order.shared.domain.exceptions.DomainRuleViolation;

public class InvalidEmailSpecialChartException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "The email %s must contain at least one %s character";

    public InvalidEmailSpecialChartException(final String specialChar, final String email) {
        super(String.format(MESSAGE_PATTER, email, specialChar), HTTP_STATUS);
    }
}
