package com.order.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Category {
    private final CategoryId categoryId;
    private final String name;
    private final Category parent;

    public Category(
            final CategoryId categoryId,
            final String name,
            final Category parent
    ) {
        this.categoryId = categoryId;
        this.name = name;
        this.parent = parent;
    }
}
