package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.ProductVariantRepository;

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
            productVariant.setSku(productVariantDetails.getSku());
            productVariant.setVariantName(productVariantDetails.getVariantName());
            productVariant.setAdditionalPrice(productVariantDetails.getAdditionalPrice());
            productVariant.setAvailable(productVariantDetails.getAvailable());
            return productVariantRepository.save(productVariant);
        }
        return null;
    }

    public void deleteProductVariant(Long id) {
        productVariantRepository.deleteById(id);
    }
}