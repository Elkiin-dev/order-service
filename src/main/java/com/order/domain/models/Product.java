package com.order.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class Product {
    private final ProductId productId;
    private final String name;
    private final BigDecimal price;
    private final Category category;
    private final String image;
    private final Integer stock;

    public Product(
            final ProductId productId,
            final String name,
            final BigDecimal price,
            final Category category,
            final String image,
            Integer stock
    ) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
        this.stock = stock;
    }
}
