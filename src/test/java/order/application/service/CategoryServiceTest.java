package order.application.service;

import com.order.application.service.CategoryService;
import com.order.domain.models.Category;
import com.order.domain.models.CategoryId;
import com.order.domain.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);

        sampleCategory = new Category(
                new CategoryId(UUID.randomUUID()),
                "Bebidas",
                null
        );
    }

    @Test
    void shouldReturnCategoryById() {
        when(categoryRepository.findById(sampleCategory.getCategoryId().getValue()))
                .thenReturn(Optional.of(sampleCategory));

        Optional<Category> result = categoryService.findById(sampleCategory.getCategoryId());

        assertTrue(result.isPresent());
        assertEquals("Bebidas", result.get().getName());
        verify(categoryRepository).findById(sampleCategory.getCategoryId().getValue());
    }

    @Test
    void shouldReturnEmptyIfCategoryNotFound() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.findById(new CategoryId(id));

        assertTrue(result.isEmpty());
        verify(categoryRepository).findById(id);
    }

    @Test
    void shouldReturnAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(sampleCategory));

        List<Category> categories = categoryService.findAll();

        assertEquals(1, categories.size());
        assertEquals("Bebidas", categories.get(0).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldSaveCategory() {
        categoryService.save(sampleCategory);

        verify(categoryRepository).save(sampleCategory);
    }

    @Test
    void shouldDeleteCategoryById() {
        CategoryId id = sampleCategory.getCategoryId();

        categoryService.delete(id);

        verify(categoryRepository).delete(id.getValue());
    }

    @Test
    void shouldReturnTrueIfCategoryNameExists() {
        when(categoryRepository.existsByName("Bebidas")).thenReturn(true);

        assertTrue(categoryService.existsByName("Bebidas"));
        verify(categoryRepository).existsByName("Bebidas");
    }

    @Test
    void shouldReturnFalseIfCategoryNameDoesNotExist() {
        when(categoryRepository.existsByName("Postres")).thenReturn(false);

        assertFalse(categoryService.existsByName("Postres"));
        verify(categoryRepository).existsByName("Postres");
    }
}
