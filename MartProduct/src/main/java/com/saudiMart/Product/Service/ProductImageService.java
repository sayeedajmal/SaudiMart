package com.saudiMart.Product.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ProductImageRepository;

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

    public ProductImage findProductImageByImageId(Long imageId) {
        return productImageRepository.findByImageId(imageId);
    }

    public List<ProductImage> findProductImagesByProduct(Products product) {
        return productImageRepository.findByProduct(product);
    }

    public List<ProductImage> findProductImagesByVariantId(Long variantId) {
        return productImageRepository.findByVariantId(variantId);
    }

    public List<ProductImage> findProductImagesByImageUrl(String imageUrl) {
        return productImageRepository.findByImageUrl(imageUrl);
    }

    public List<ProductImage> findProductImagesByAltText(String altText) {
        return productImageRepository.findByAltText(altText);
    }

    public List<ProductImage> findProductImagesByDisplayOrder(Integer displayOrder) {
        return productImageRepository.findByDisplayOrder(displayOrder);
    }

    public List<ProductImage> findProductImagesByIsPrimary(Boolean isPrimary) {
        return productImageRepository.findByIsPrimary(isPrimary);
    }

    public List<ProductImage> findProductImagesByCreatedAtBetween(Timestamp start, Timestamp end) {
        return productImageRepository.findByCreatedAtBetween(start, end);
    }

    public List<ProductImage> findPrimaryProductImageByProductId(Long productId) {
        return productImageRepository.findByProductIdAndIsPrimaryTrue(productId);
    }

    public List<ProductImage> findProductImagesByProductIdOrdered(Long productId) {
        return productImageRepository.findByProductIdOrderByDisplayOrderAsc(productId);
    }

}