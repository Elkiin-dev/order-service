package com.order.domain.exceptions;

import com.order.domain.models.BuyerEmail;
import com.order.shared.domain.exceptions.DomainRuleViolation;

public class CategoryAlreadyExist extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "The category %s already exist";

    public CategoryAlreadyExist(final String categoryName) {
        super(String.format(MESSAGE_PATTER, categoryName), HTTP_STATUS);
    }
}
