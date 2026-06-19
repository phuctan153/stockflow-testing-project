package com.stockflow.repository;

import com.stockflow.entity.StockOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOutRepository extends JpaRepository<StockOut, Long>, JpaSpecificationExecutor<StockOut> {
    boolean existsByProductId(Long productId);
}
