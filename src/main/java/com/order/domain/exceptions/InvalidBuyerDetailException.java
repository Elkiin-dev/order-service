package com.order.domain.exceptions;


import com.order.shared.domain.exceptions.DomainRuleViolation;

public class InvalidBuyerDetailException extends DomainRuleViolation {
    public static final int HTTP_STATUS = 409;
    public static final String MESSAGE_PATTER = "Seat position is required to open an order.";

    public InvalidBuyerDetailException() {
        super(String.format(MESSAGE_PATTER), HTTP_STATUS);
    }
}
