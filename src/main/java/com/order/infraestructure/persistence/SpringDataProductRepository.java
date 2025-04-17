package com.order.infraestructure.persistence;

import com.order.infraestructure.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByCategory_Id(UUID categoryId);
    boolean existsByNameAndCategoryId(String name, UUID categoryId);

    @Modifying
    @Query("UPDATE ProductEntity p SET p.stock = :stock WHERE p.id = :productId")
    void updateStock(@Param("productId") UUID productId, @Param("stock") int stock);
}
