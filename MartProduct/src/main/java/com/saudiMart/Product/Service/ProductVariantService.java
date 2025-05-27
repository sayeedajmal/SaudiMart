package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.ProductVariantRepository;

@Service
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public ProductVariantService(ProductVariantRepository productVariantRepository) {
        this.productVariantRepository = productVariantRepository;
    }

    public List<ProductVariant> getAllProductVariants() {
        return productVariantRepository.findAll();
    }

    public List<ProductVariant> getProductVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId);
    }

    public ProductVariant createProductVariant(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);
    }

    public ProductVariant updateProductVariant(Long id, ProductVariant productVariantDetails) {
        Optional<ProductVariant> optionalProductVariant = productVariantRepository.findById(id);
        if (optionalProductVariant.isPresent()) {
            ProductVariant productVariant = optionalProductVariant.get();
            productVariant.setProductId(productVariantDetails.getProductId());
            productVariant.setSku(productVariantDetails.getSku());
            productVariant.setVariantName(productVariantDetails.getVariantName());
            productVariant.setAdditionalPrice(productVariantDetails.getAdditionalPrice());
            productVariant.setAvailable(productVariantDetails.getAvailable());
            // createdAt is typically not updated, but if needed:
            // productVariant.setCreatedAt(productVariantDetails.getCreatedAt());
            return productVariantRepository.save(productVariant);
        } else {
            // Handle not found scenario, e.g., throw an exception
            return null;
        }
    }

    public void deleteProductVariant(Long id) {
        productVariantRepository.deleteById(id);
    }

    public Optional<ProductVariant> getProductVariantById(Long id) {
        return productVariantRepository.findById(id);
    }

}