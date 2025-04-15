package com.order.shared.infraestructure.controllers;

import com.order.shared.domain.exceptions.DomainRuleViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(DomainRuleViolation.class)
    public ResponseEntity<String> domainRuleViolationHandler(final DomainRuleViolation domainRuleViolation){
        return ResponseEntity.status(domainRuleViolation.getStatus()).body(domainRuleViolation.getMessage());
    }
}
