package com.order.domain.repositories;


import com.order.domain.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Optional<Product> findById(UUID productId);
    List<Product> findAll();
    List<Product> findByCategory(UUID categoryId);
    void save(Product product);
    void updateStock(UUID productId, int newStock);
    void delete(UUID productId);
    boolean existsByNameAndCategoryId(String name, UUID categoryId);
}
