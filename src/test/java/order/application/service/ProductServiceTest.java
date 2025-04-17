package order.application.service;

import com.order.application.service.ProductService;
import com.order.domain.models.Category;
import com.order.domain.models.CategoryId;
import com.order.domain.models.Product;
import com.order.domain.models.ProductId;
import com.order.domain.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    private Product sampleProduct;
    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        sampleCategory = new Category(
                new CategoryId(UUID.randomUUID()),
                "Snacks",
                null
        );

        sampleProduct = new Product(
                new ProductId(UUID.randomUUID()),
                "chips",
                BigDecimal.valueOf(1.2),
                sampleCategory,
                "image.jpg",
                10
        );
    }

    @Test
    void shouldFindProductById() {
        when(productRepository.findById(sampleProduct.getProductId().getValue()))
                .thenReturn(Optional.of(sampleProduct));

        Optional<Product> result = productService.findById(sampleProduct.getProductId());

        assertTrue(result.isPresent());
        assertEquals("chips", result.get().getName());
        verify(productRepository).findById(sampleProduct.getProductId().getValue());
    }

    @Test
    void shouldReturnEmptyIfProductNotFound() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = productService.findById(new ProductId(id));

        assertTrue(result.isEmpty());
        verify(productRepository).findById(id);
    }

    @Test
    void shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct));

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals("chips", result.get(0).getName());
        verify(productRepository).findAll();
    }

    @Test
    void shouldReturnProductsByCategory() {
        UUID categoryId = sampleCategory.getCategoryId().getValue();
        when(productRepository.findByCategory(categoryId)).thenReturn(List.of(sampleProduct));

        List<Product> result = productService.findByCategory(sampleCategory.getCategoryId());

        assertEquals(1, result.size());
        verify(productRepository).findByCategory(categoryId);
    }

    @Test
    void shouldSaveProduct() {
        productService.save(sampleProduct);

        verify(productRepository).save(sampleProduct);
    }

    @Test
    void shouldUpdateStock() {
        ProductId productId = sampleProduct.getProductId();
        productService.updateStock(productId, 5);

        verify(productRepository).updateStock(productId.getValue(), 5);
    }

    @Test
    void shouldDeleteProduct() {
        ProductId productId = sampleProduct.getProductId();
        productService.delete(productId);

        verify(productRepository).delete(productId.getValue());
    }

    @Test
    void shouldReturnTrueIfProductExistsByNameAndCategory() {
        String name = "chips";
        UUID categoryId = sampleCategory.getCategoryId().getValue();

        when(productRepository.existsByNameAndCategoryId(name, categoryId)).thenReturn(true);

        boolean exists = productService.existsByNameAndCategoryId(name, categoryId);

        assertTrue(exists);
        verify(productRepository).existsByNameAndCategoryId(name, categoryId);
    }

    @Test
    void shouldReturnFalseIfProductDoesNotExistByNameAndCategory() {
        String name = "cookies";
        UUID categoryId = sampleCategory.getCategoryId().getValue();

        when(productRepository.existsByNameAndCategoryId(name, categoryId)).thenReturn(false);

        boolean exists = productService.existsByNameAndCategoryId(name, categoryId);

        assertFalse(exists);
        verify(productRepository).existsByNameAndCategoryId(name, categoryId);
    }
}
