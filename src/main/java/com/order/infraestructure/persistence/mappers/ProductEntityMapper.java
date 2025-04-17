package com.order.infraestructure.persistence.mappers;

import com.order.domain.models.Product;
import com.order.domain.models.ProductId;
import com.order.infraestructure.persistence.entities.ProductEntity;

public class ProductEntityMapper {

    private ProductEntityMapper () {}

    public static Product toDomain(ProductEntity entity) {
        return new Product(
                new ProductId(entity.getId()),
                entity.getName(),
                entity.getPrice(),
                CategoryEntityMapper.toDomain(entity.getCategory()),
                entity.getImage(),
                entity.getStock()
        );
    }

    public static ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getProductId().getValue(),
                product.getName(),
                product.getPrice(),
                product.getImage(),
                product.getStock(),
                CategoryEntityMapper.toEntity(product.getCategory())
        );
    }
}
