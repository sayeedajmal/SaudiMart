package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public List<ProductVariant> getAllProductVariants() {
        return productVariantRepository.findAll();
    }

    public ProductVariant getProductVariantById(Long id) throws ProductException {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + id));
    }

    public ProductVariant createProductVariant(ProductVariant productVariant) throws ProductException {
        if (productVariant == null) {
            throw new ProductException("Product Variant cannot be null");
        }
        return productVariantRepository.save(productVariant);
    }

    public ProductVariant updateProductVariant(Long id, ProductVariant productVariantDetails) throws ProductException {
        if (productVariantDetails == null) {
            throw new ProductException("Product Variant details cannot be null for update");
        }
        Optional<ProductVariant> productVariantOptional = productVariantRepository.findById(id);
        if (productVariantOptional.isPresent()) {
            ProductVariant productVariant = productVariantOptional.get();
            productVariant.setSku(productVariantDetails.getSku());
            productVariant.setVariantName(productVariantDetails.getVariantName());
            productVariant.setAdditionalPrice(productVariantDetails.getAdditionalPrice());
            productVariant.setAvailable(productVariantDetails.getAvailable());
            return productVariantRepository.save(productVariant);
        }
        throw new ProductException("Product Variant not found with id: " + id);
    }

    public void deleteProductVariant(Long id) throws ProductException {
        if (!productVariantRepository.existsById(id)) {
            throw new ProductException("Product Variant not found with id: " + id);
        }
        productVariantRepository.deleteById(id);
    }
}