package com.dev.shop.repositories;

import com.dev.shop.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByBrand(String brand);

    Long countByBrand(String brand);

    Long countByCategoryName(String categoryName);
}
