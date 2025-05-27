package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    // Create a new product
    public Products createProduct(Products product) {
        return productsRepository.save(product);
    }

    // Get a product by ID
    public Optional<Products> getProductById(Long productId) {
        return productsRepository.findById(productId);
    }

    // Get all products
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    // Update a product
    public Products updateProduct(Long productId, Products updatedProduct) {
        if (productsRepository.existsById(productId)) {
            updatedProduct.setProductId(productId);
            return productsRepository.save(updatedProduct);
        }
        return null; // Or throw an exception
    }

    // Delete a product by ID
    public void deleteProduct(Long productId) {
        productsRepository.deleteById(productId);
    }

    // Get products by seller ID
    public List<Products> getProductsBySellerId(Long sellerId) {
        return productsRepository.findBySellerId(sellerId);
    }
}