package com.order.infraestructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
public class CategoryEntity {

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    public CategoryEntity() {}

    public CategoryEntity(UUID id, String name, CategoryEntity parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }
}
