package com.datamify.spring.boot.simple.data.repository;

import com.datamify.spring.boot.simple.data.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
