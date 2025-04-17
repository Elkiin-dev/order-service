package com.order.application.service;

import com.order.domain.models.Category;
import com.order.domain.models.CategoryId;
import com.order.domain.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<Category> findById(CategoryId id) {
        return categoryRepository.findById(id.getValue());
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void delete(CategoryId id) {
        categoryRepository.delete(id.getValue());
    }

    public boolean existsByName(String categoryName){
        return categoryRepository.existsByName(categoryName);
    }
}
