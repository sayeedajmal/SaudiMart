package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public List<ProductVariant> getAllProductVariants() {
        return productVariantRepository.findAll();
    }

    public Optional<ProductVariant> getProductVariantById(Long id) {
        return productVariantRepository.findById(id);
    }

    public ProductVariant createProductVariant(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);
    }

    public ProductVariant updateProductVariant(Long id, ProductVariant productVariantDetails) {
        Optional<ProductVariant> productVariantOptional = productVariantRepository.findById(id);
        if (productVariantOptional.isPresent()) {
            ProductVariant productVariant = productVariantOptional.get();
            // Update fields based on productVariantDetails
            productVariant.setSku(productVariantDetails.getSku());
            productVariant.setPrice(productVariantDetails.getPrice());
            productVariant.setStock(productVariantDetails.getStock());
            productVariant.setAttributes(productVariantDetails.getAttributes());
            // Update other fields as needed
            return productVariantRepository.save(productVariant);
        }
        return null;
    }

    public void deleteProductVariant(Long id) {
        productVariantRepository.deleteById(id);
    }
}