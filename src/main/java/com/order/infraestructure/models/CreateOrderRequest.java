package com.order.infraestructure.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderRequest {
    private final String buyerEmail;
    private final String seatLetter;
    private final Integer seatNumber;

}
