package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.ProductImageRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public Page<ProductImage> getAllVarientImages(Pageable pageable) {
 return productImageRepository.findAll(pageable);
    }

    public ProductImage getProductImageById(String productImageId) throws ProductException {
        return productImageRepository.findById(productImageId)
                .orElseThrow(() -> new ProductException("Product image not found with id: " + productImageId));
    }
    

    public List<ProductImage> getProductImagesByVariantId(String variantId) throws ProductException {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
        return productImageRepository.findByVariant(variant);
    }

    public ProductImage createProductImage(ProductImage productImage) throws ProductException {
        if (productImage == null) {
            throw new ProductException("Product image cannot be null");
        }
        return productImageRepository.save(productImage);
    }

    public ProductImage updateProductImage(String productImageId, ProductImage productImageDetails)
            throws ProductException {
        if (productImageDetails == null) {
            throw new ProductException("Product image details cannot be null");
        }
        Optional<ProductImage> productImageOptional = productImageRepository.findById(productImageId);
        ProductImage productImage = productImageOptional
                .orElseThrow(() -> new ProductException("Product image not found with id: " + productImageId));

        if (productImageDetails.getImageUrl() != null)
            productImage.setImageUrl(productImageDetails.getImageUrl());
        if (productImageDetails.getAltText() != null)
            productImage.setAltText(productImageDetails.getAltText());
        if (productImageDetails.getDisplayOrder() != null)
            productImage.setDisplayOrder(productImageDetails.getDisplayOrder());
        if (productImageDetails.getIsPrimary() != null)
            productImage.setIsPrimary(productImageDetails.getIsPrimary());

        return productImageRepository.save(productImage);
    }

    public void deleteProductImage(String productImageId) throws ProductException {
        if (!productImageRepository.existsById(productImageId)) {
            throw new ProductException("Product image not found with id: " + productImageId);
        }
        productImageRepository.deleteById(productImageId);
    }

    public Page<ProductImage> searchProductImagesByVariantId(String variantId, Pageable pageable) throws ProductException {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
 return productImageRepository.findByVariant(variant, pageable);
    }

}