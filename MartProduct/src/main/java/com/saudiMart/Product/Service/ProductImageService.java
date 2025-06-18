package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Repository.ProductImageRepository;
import com.saudiMart.Product.Utils.ProductException;
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

    public ProductImage getProductImageById(Long productImageId) throws ProductException {
        return productImageRepository.findById(productImageId)
                .orElseThrow(() -> new ProductException("Product image not found with id: " + productImageId));
    }

    public ProductImage createProductImage(ProductImage productImage) throws ProductException {
        if (productImage == null) {
            throw new ProductException("Product image cannot be null");
        }
        return productImageRepository.save(productImage);
    }

    public ProductImage updateProductImage(Long productImageId, ProductImage productImageDetails) throws ProductException {
        if (productImageDetails == null) {
            throw new ProductException("Product image details cannot be null");
        }
        Optional<ProductImage> productImageOptional = productImageRepository.findById(productImageId);
        ProductImage productImage = productImageOptional.orElseThrow(() -> new ProductException("Product image not found with id: " + productImageId));

        productImage.setImageUrl(productImageDetails.getImageUrl());
        productImage.setProduct(productImageDetails.getProduct());
        return productImageRepository.save(productImage);
    }

    public void deleteProductImage(Long productImageId) throws ProductException {
        if (!productImageRepository.existsById(productImageId)) {
            throw new ProductException("Product image not found with id: " + productImageId);
        }
        productImageRepository.deleteById(productImageId);
    }
}