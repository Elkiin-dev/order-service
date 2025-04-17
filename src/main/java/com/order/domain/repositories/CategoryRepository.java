package com.order.domain.repositories;


import com.order.domain.models.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Optional<Category> findById(UUID id);
    List<Category> findAll();
    void save(Category category);
    void delete(UUID id);
    boolean existsByName(String name);
}
