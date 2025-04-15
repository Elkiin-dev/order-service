package com.order.infraestructure.controllers;

import com.order.application.service.CategoryService;
import com.order.application.service.ProductService;
import com.order.domain.exceptions.CategoryNotFoundException;
import com.order.domain.exceptions.ProductAlreadyExist;
import com.order.domain.exceptions.ProductNotFoundException;
import com.order.domain.models.Category;
import com.order.domain.models.CategoryId;
import com.order.domain.models.Product;
import com.order.domain.models.ProductId;
import com.order.infraestructure.models.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable UUID id) {
        return productService.findById(new ProductId(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable UUID categoryId) {
        return productService.findByCategory(new CategoryId(categoryId));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateProductRequest request) {

        if (productService.existsByNameAndCategoryId(request.getName(), request.getCategoryId())) {
            throw  new ProductAlreadyExist(request.getName());
        }

        Category category = categoryService.findById(new CategoryId(request.getCategoryId()))
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        productService.save(
                new Product(
                        new ProductId(),
                        request.getName(),
                        request.getPrice(),
                        category,
                        request.getImage(),
                        request.getStock()
                )
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable UUID id, @RequestParam int stock) {
        productService.updateStock(new ProductId(id), stock);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(new ProductId(id));
        return ResponseEntity.noContent().build();
    }
}
