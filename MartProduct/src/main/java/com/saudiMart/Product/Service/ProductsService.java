package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;
import java.sql.Timestamp;
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

    // Get a product by product ID
    public Optional<Products> findProductByProductId(Long productId) {
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

    // Get products by category ID
    public List<Products> findProductsByCategoryId(Long categoryId) {
        return productsRepository.findByCategoryId(categoryId);
    }

    // Get products by name
    public List<Products> findProductsByName(String name) {
        return productsRepository.findByName(name);
    }

    // Search products by name (case-insensitive)
    public List<Products> searchProductsByName(String name) {
        return productsRepository.findByNameContainingIgnoreCase(name);
    }

    // Get products by SKU
    public List<Products> findProductsBySku(String sku) {
        return productsRepository.findBySku(sku);
    }

    // Get products by availability
    public List<Products> findProductsByAvailable(Boolean available) {
        return productsRepository.findByAvailable(available);
    }

    // Get products by base price
    public List<Products> findProductsByBasePrice(BigDecimal basePrice) {
        return productsRepository.findByBasePrice(basePrice);
    }

    // Get products by bulk only status
    public List<Products> findProductsByIsBulkOnly(Boolean isBulkOnly) {
        return productsRepository.findByIsBulkOnly(isBulkOnly);
    }

    // Get products by minimum order quantity
    public List<Products> findProductsByMinimumOrderQuantity(Integer minimumOrderQuantity) {
        return productsRepository.findByMinimumOrderQuantity(minimumOrderQuantity);
    }

    // Get products by weight
    public List<Products> findProductsByWeight(BigDecimal weight) {
        return productsRepository.findByWeight(weight);
    }

    // Get products by weight unit
    public List<Products> findProductsByWeightUnit(String weightUnit) {
        return productsRepository.findByWeightUnit(weightUnit);
    }

    // Get products by dimensions
    public List<Products> findProductsByDimensions(String dimensions) {
        return productsRepository.findByDimensions(dimensions);
    }

    // Search products by description (case-insensitive)
    public List<Products> searchProductsByDescription(String description) {
        return productsRepository.findByDescriptionContainingIgnoreCase(description);
    }
}