package com.order.infraestructure.persistence.repositories;

import com.order.domain.models.Product;
import com.order.domain.repositories.ProductRepository;
import com.order.infraestructure.persistence.SpringDataProductRepository;
import com.order.infraestructure.persistence.mappers.ProductEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaProductRepository implements ProductRepository {

    private final SpringDataProductRepository repository;

    @Override
    public Optional<Product> findById(UUID productId) {
        return repository.findById(productId)
                .map(ProductEntityMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(ProductEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Product> findByCategory(UUID categoryId) {
        return repository.findByCategory_Id(categoryId).stream()
                .map(ProductEntityMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void save(Product product) {
        repository.save(ProductEntityMapper.toEntity(product));
    }

    @Override
    @Transactional
    public void updateStock(UUID productId, int newStock) {
        repository.updateStock(productId, newStock);
    }

    @Override
    @Transactional
    public void delete(UUID productId) {
        repository.deleteById(productId);
    }

    @Override
    public boolean existsByNameAndCategoryId(String productName, UUID categoryId){
        return repository.existsByNameAndCategoryId(productName, categoryId);
    }
}

