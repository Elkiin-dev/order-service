package com.order.infraestructure.controllers;

import com.order.application.service.CategoryService;
import com.order.domain.exceptions.CategoryAlreadyExist;
import com.order.domain.exceptions.CategoryNotFoundException;
import com.order.domain.exceptions.ParentCategoryNotFoundException;
import com.order.domain.models.Category;
import com.order.domain.models.CategoryId;
import com.order.infraestructure.models.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable UUID id) {
        return categoryService.findById(new CategoryId(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated CreateCategoryRequest request) {
        Category parent = null;

        if (categoryService.existsByName(request.getName())) {
          throw  new CategoryAlreadyExist(request.getName());
        }

        if (request.getParentId() != null) {
            parent = categoryService.findById(new CategoryId(request.getParentId()))
                    .orElseThrow(() -> new ParentCategoryNotFoundException(request.getParentId()));
        }

        categoryService.save(
                new Category(
                        new CategoryId(),
                        request.getName(),
                        parent
                        )
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.delete(new CategoryId(id));
        return ResponseEntity.noContent().build();
    }
}
