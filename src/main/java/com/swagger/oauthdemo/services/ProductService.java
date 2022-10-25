package com.swagger.oauthdemo.services;

import com.swagger.oauthdemo.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product saveProduct(Product product);
    void deleteProductById(Long id);
    Optional<Product> findProductById(Long id);
    List<Product> findAllProduct();
}
