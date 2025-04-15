package com.order.infraestructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    private UUID id;
    private String name;
    private BigDecimal price;
    private String image;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public ProductEntity(){}

    public ProductEntity(
            final UUID id,
            final String name,
            final BigDecimal price,
            final String image,
            final Integer stock,
            final CategoryEntity category
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.stock = stock;
        this.category = category;
    }
}
