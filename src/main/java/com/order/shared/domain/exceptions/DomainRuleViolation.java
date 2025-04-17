package com.order.shared.domain.exceptions;

import lombok.Getter;

@Getter
public class DomainRuleViolation extends RuntimeException{
    private final int status;

    public DomainRuleViolation(final String message, final int status){
        super(message);
        this.status = status;
    }
}
