package com.saudiMart.Product.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Repository.ProductImageRepository;
import com.saudiMart.Product.Utils.ProductException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public ProductImage findProductImageById(Long id) throws ProductException {
        return productImageRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product image not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public List<ProductImage> findProductImagesByProductId(Long productId) {
        return productImageRepository.findByProduct_ProductId(productId);
    }

    public List<ProductImage> findProductImagesByProductVariantId(Long variantId) {
        return productImageRepository.findByProductVariant_VariantId(variantId);
    }

    public ProductImage saveProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public ProductImage updateProductImage(Long id, ProductImage updatedProductImage) throws ProductException {
        ProductImage existingProductImage = findProductImageById(id);

        existingProductImage.setImageUrl(updatedProductImage.getImageUrl());
        existingProductImage.setAltText(updatedProductImage.getAltText());
        existingProductImage.setSortOrder(updatedProductImage.getSortOrder());
        existingProductImage.setProduct(updatedProductImage.getProduct());
        existingProductImage.setProductVariant(updatedProductImage.getProductVariant());

        return productImageRepository.save(existingProductImage);
    }

    public void deleteProductImage(Long id) throws ProductException {
        if (productImageRepository.existsById(id)) {
            productImageRepository.deleteById(id);
        } else {
            throw new ProductException("Product image not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}