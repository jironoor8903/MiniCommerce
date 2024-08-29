package com.example.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.getProducts(category, name, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Product not found with id: " + id), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequest productRequest) {
        Product newProduct = new Product();
        newProduct.setName(productRequest.getName());
        newProduct.setCategory(productRequest.getCategory());
        newProduct.setDescription(productRequest.getDescription());
        newProduct.setOriginalPrice(productRequest.getOriginalPrice());
        newProduct.setResellPrice(productRequest.getResellPrice());

        newProduct.setSoldByUser(null);
        newProduct.setBoughtByUser(null);

        Product savedProduct = productService.addProduct(newProduct);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


    @GetMapping("/by-price")
    public List<Product> getProductsByPrice(@RequestParam double price) {
        return productService.getProductsByResellPrice(price);
    }

    @GetMapping("/by-price-range")
    public List<Product> getProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }
}
