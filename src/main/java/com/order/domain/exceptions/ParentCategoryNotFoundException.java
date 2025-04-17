package com.order.domain.exceptions;

import com.order.shared.domain.exceptions.DomainRuleViolation;

import java.util.UUID;

public class ParentCategoryNotFoundException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 404;
    public static final String MESSAGE_PATTER = "Parent category with id %s not found";

    public ParentCategoryNotFoundException(final UUID parentId) {
        super(String.format(MESSAGE_PATTER, parentId), HTTP_STATUS);
    }
}
