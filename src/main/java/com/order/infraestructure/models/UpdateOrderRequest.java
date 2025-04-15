package com.order.infraestructure.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateOrderRequest {
    private String buyerEmail;
    private List<UUID> productIds;
}
