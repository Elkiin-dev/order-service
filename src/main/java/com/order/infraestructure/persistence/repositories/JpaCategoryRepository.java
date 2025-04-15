package com.order.infraestructure.persistence.repositories;

import com.order.domain.models.Category;
import com.order.domain.repositories.CategoryRepository;
import com.order.infraestructure.persistence.SpringDataCategoryRepository;
import com.order.infraestructure.persistence.mappers.CategoryEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaCategoryRepository implements CategoryRepository {

    private final SpringDataCategoryRepository repository;

    @Override
    public Optional<Category> findById(UUID id) {
        return repository.findById(id)
                .map(CategoryEntityMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll().stream()
                .map(CategoryEntityMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void save(Category category) {
        repository.save(CategoryEntityMapper.toEntity(category));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByName(String categoryName) {
        return repository.existsByName(categoryName);
    }

}
