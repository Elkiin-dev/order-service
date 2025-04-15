package com.order.infraestructure.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateProductRequest {
    private final String name;
    private final BigDecimal price;
    private final UUID categoryId;
    private String image;
    private Integer stock;
}
