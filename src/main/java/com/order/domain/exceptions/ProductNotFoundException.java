package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

import java.util.UUID;

public class ProductNotFoundException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 404;
    public static final String MESSAGE_PATTER = "The product with id %s was not found";

    public ProductNotFoundException(final UUID id) {
        super(String.format(MESSAGE_PATTER, id), HTTP_STATUS);
    }
}
