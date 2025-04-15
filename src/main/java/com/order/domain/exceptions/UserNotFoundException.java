package com.order.domain.exceptions;


import com.order.shared.domain.exceptions.DomainRuleViolation;

public class UserNotFoundException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 404;
    public static final String MESSAGE_PATTER = "There is not user with email %s";

    public UserNotFoundException(final String user) {
        super(String.format(MESSAGE_PATTER, user), HTTP_STATUS);
    }
}
