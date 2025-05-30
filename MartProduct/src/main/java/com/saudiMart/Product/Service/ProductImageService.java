package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    public ProductImage saveProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

    public Optional<ProductImage> getProductImageById(Long id) {
        return productImageRepository.findById(id);
    }

    public List<ProductImage> getProductImagesByProductId(Long productId) {
        return productImageRepository.findByProductId(productId);
    }

    public void deleteProductImage(Long id) {
        productImageRepository.deleteById(id);
    }

    public ProductImage updateProductImage(Long id, ProductImage productImageDetails) {
        Optional<ProductImage> existingProductImage = productImageRepository.findById(id);
        if (existingProductImage.isPresent()) {
            ProductImage updatedProductImage = existingProductImage.get();
            updatedProductImage.setImageUrl(productImageDetails.getImageUrl());
            updatedProductImage.setAltText(productImageDetails.getAltText());
            updatedProductImage.setDisplayOrder(productImageDetails.getDisplayOrder());
            updatedProductImage.setPrimary(productImageDetails.getPrimary());
            return productImageRepository.save(updatedProductImage);
        } else {
            throw new UnsupportedOperationException("Product image not found with id: " + id);
        }
    }

}