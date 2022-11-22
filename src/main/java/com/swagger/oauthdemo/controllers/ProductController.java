package com.swagger.oauthdemo.controllers;

import com.swagger.oauthdemo.exceptions.ResourceNotFoundException;
import com.swagger.oauthdemo.models.Product;
import com.swagger.oauthdemo.services.ProductService;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/helloworld")
    public String helloWorld(){
        return "hello world";
    }

    @GetMapping("")
    public List<Product> getAllProduct(@RequestParam String consumerKey) {
        log.info("ConsumerKey: {}", consumerKey);
        return productService.findAllProduct();
    }
    @GetMapping("/get/{productId}")
    public Product getProductById(@PathVariable(value = "productId")Long productId) {
        return productService.findProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found."));
    }

    @PostMapping("/create")
    public String createProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return "Product added";
    }

    @PutMapping("/update/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public String updateProductById(@PathVariable(value = "productId")Long productId,
                                    @RequestBody Product product) {
        return productService.findProductById(productId).map(x -> {
            x.setName(product.getName());
            x.setPrice(product.getPrice());
            productService.saveProduct(x);
            return "Product updated successfully.";
        }).orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found."));
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProductById(@PathVariable(value = "productId")Long productId) {
        return productService.findProductById(productId).map(x -> {
            productService.deleteProductById(productId);
            return "product " + productId + "deleted successfully.";
        }).orElseThrow(() -> new ResourceNotFoundException("ProductId: " + productId + "not found."));
    }
}
