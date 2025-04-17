package com.order.infraestructure.persistence.mappers;

import com.order.domain.models.Category;
import com.order.domain.models.CategoryId;
import com.order.infraestructure.persistence.entities.CategoryEntity;

public class CategoryEntityMapper {

    private CategoryEntityMapper() {
    }

    public static Category toDomain(final CategoryEntity entity) {
        return new Category(
                new CategoryId(entity.getId()),
                entity.getName(),
                entity.getParent() != null ? toDomain(entity.getParent()) : null
        );
    }

    public static CategoryEntity toEntity(final Category category) {
        return new CategoryEntity(
                category.getCategoryId().getValue(),
                category.getName(),
                category.getParent() != null ? toEntity(category.getParent()) : null
        );
    }
}

