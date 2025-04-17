package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

public class ProductAlreadyExist extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "The product %s already exist";

    public ProductAlreadyExist(final String productName) {
        super(String.format(MESSAGE_PATTER, productName), HTTP_STATUS);
    }
}
