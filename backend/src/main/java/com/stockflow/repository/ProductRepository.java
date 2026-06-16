package com.stockflow.repository;

import com.stockflow.entity.Product;
import com.stockflow.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Product> findByNameIgnoreCase(String name);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByStatus(ProductStatus status);

    List<Product> findByNameContainingIgnoreCaseAndCategoryIgnoreCaseAndStatus(
            String keyword,
            String category,
            ProductStatus status
    );

    List<Product> findByNameContainingIgnoreCaseAndCategoryIgnoreCase(
            String keyword,
            String category
    );

    List<Product> findByNameContainingIgnoreCaseAndStatus(
            String keyword,
            ProductStatus status
    );

    List<Product> findByCategoryIgnoreCaseAndStatus(
            String category,
            ProductStatus status
    );
}
