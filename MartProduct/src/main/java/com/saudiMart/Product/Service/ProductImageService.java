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

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

    public Optional<ProductImage> getProductImageById(Long productImageId) {
        return productImageRepository.findById(productImageId);
    }

    public ProductImage createProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public ProductImage updateProductImage(Long productImageId, ProductImage productImageDetails) {
        Optional<ProductImage> productImageOptional = productImageRepository.findById(productImageId);
        if (productImageOptional.isPresent()) {
            ProductImage productImage = productImageOptional.get();
            productImage.setImageUrl(productImageDetails.getImageUrl());
            productImage.setProduct(productImageDetails.getProduct());
            return productImageRepository.save(productImage);
        }
        return null;
    }

    public void deleteProductImage(Long productImageId) {
        productImageRepository.deleteById(productImageId);
    }
}