package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<Products> getProductById(Long productId) {
        return productsRepository.findById(productId);
    }

    public Products createProduct(Products product) {
        return productsRepository.save(product);
    }

    public Products updateProduct(Long productId, Products productDetails) {
        Optional<Products> productOptional = productsRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setBasePrice(productDetails.getBasePrice());
            product.setCategory(productDetails.getCategory());
            return productsRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long productId) {
        productsRepository.deleteById(productId);
    }
}