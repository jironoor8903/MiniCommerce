package com.example.demo.product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category, Pageable pageable);

    List<Product> findByName(String name, Pageable pageable);

    List<Product> findByCategoryAndName(String category, String name, Pageable pageable);

    List<Product> findByResellPrice(double resellPrice);

    List<Product> findByResellPriceBetween(double minPrice, double maxPrice);
}