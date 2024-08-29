package com.example.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(String category, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (category != null && name != null) {
            return productRepository.findByCategoryAndName(category, name, pageable);
        } else if (category != null) {
            return productRepository.findByCategory(category, pageable);
        } else if (name != null) {
            return productRepository.findByName(name, pageable);
        } else {
            return productRepository.findAll(pageable).getContent();
        }
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsByResellPrice(double resellPrice) {
        return productRepository.findByResellPrice(resellPrice);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByResellPriceBetween(minPrice, maxPrice);
    }
}