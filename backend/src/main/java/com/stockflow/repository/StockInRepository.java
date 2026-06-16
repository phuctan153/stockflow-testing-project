package com.stockflow.repository;

import com.stockflow.entity.StockIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInRepository extends JpaRepository<StockIn, Long>, JpaSpecificationExecutor<StockIn> {
    boolean existsByProductId(Long productId);
}
