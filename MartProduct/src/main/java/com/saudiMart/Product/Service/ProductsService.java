package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.saudiMart.Product.Utils.ProductException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public Products getProductById(Long productId) throws ProductException {
        return productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));
    }

    public Products createProduct(Products product) throws ProductException {
        if (product == null) {
            throw new ProductException("Product cannot be null");
        }
        return productsRepository.save(product);
    }

    public Products updateProduct(Long productId, Products productDetails) throws ProductException {
        if (productDetails == null) {
            throw new ProductException("Product details cannot be null");
        }
        Optional<Products> productOptional = productsRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setBasePrice(productDetails.getBasePrice());
            // Assuming Category object is not null in productDetails
            product.setCategory(productDetails.getCategory());
            return productsRepository.save(product);
        }
        throw new ProductException("Product with ID " + productId + " not found");
    }

    public void deleteProduct(Long productId) {
        productsRepository.deleteById(productId);
    }
}