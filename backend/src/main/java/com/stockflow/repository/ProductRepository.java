package com.stockflow.repository;

import com.stockflow.entity.Product;
import com.stockflow.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Product> findByNameIgnoreCase(String name);

    long countByStatus(ProductStatus status);

    long countByQuantityBetween(Integer minQuantity, Integer maxQuantity);

    long countByQuantity(Integer quantity);

    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Product p")
    Long sumTotalQuantity();
}
