package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

import java.util.UUID;

public class CategoryNotFoundException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 404;
    public static final String MESSAGE_PATTER = "Category with id %s not found";

    public CategoryNotFoundException(final UUID parentId) {
        super(String.format(MESSAGE_PATTER, parentId), HTTP_STATUS);
    }
}
