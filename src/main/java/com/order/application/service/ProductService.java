package com.order.application.service;

import com.order.domain.models.CategoryId;
import com.order.domain.models.Product;
import com.order.domain.models.ProductId;
import com.order.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> findById(ProductId id) {
        return productRepository.findById(id.getValue());
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(CategoryId categoryId) {
        return productRepository.findByCategory(categoryId.getValue());
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void updateStock(ProductId productId, int newStock) {
        productRepository.updateStock(productId.getValue(), newStock);
    }

    public void delete(ProductId productId) {
        productRepository.delete(productId.getValue());
    }
    public boolean existsByNameAndCategoryId(String productName, UUID categoryId) {
        return productRepository.existsByNameAndCategoryId(productName, categoryId);
    }
}
