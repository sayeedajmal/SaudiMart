package com.saudiMart.Product.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Utils.ProductException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public ProductVariantService(ProductVariantRepository productVariantRepository) {
        this.productVariantRepository = productVariantRepository;
    }

    public ProductVariant findProductVariantById(Long id) throws ProductException {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product variant not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public List<ProductVariant> findAllProductVariants() {
        return productVariantRepository.findAll();
    }

    public List<ProductVariant> findProductVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId);
    }

    public ProductVariant saveProductVariant(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);
    }

    public ProductVariant updateProductVariant(Long id, ProductVariant updatedProductVariant) throws ProductException {
        ProductVariant existingProductVariant = findProductVariantById(id);

        existingProductVariant.setSku(updatedProductVariant.getSku());
        existingProductVariant.setAttribute1(updatedProductVariant.getAttribute1());
        existingProductVariant.setAttribute2(updatedProductVariant.getAttribute2());
        existingProductVariant.setAttribute3(updatedProductVariant.getAttribute3());
        existingProductVariant.setPrice(updatedProductVariant.getPrice());
        existingProductVariant.setAvailable(updatedProductVariant.getAvailable());
        // The product relationship should ideally not be changed here or handled carefully

        return productVariantRepository.save(existingProductVariant);
    }

    public void deleteProductVariant(Long id) throws ProductException {
        if (productVariantRepository.existsById(id)) {
            productVariantRepository.deleteById(id);
        } else {
            throw new ProductException("Product variant not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}