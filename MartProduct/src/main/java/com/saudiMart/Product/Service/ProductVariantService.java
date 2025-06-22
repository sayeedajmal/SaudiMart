package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

 @Autowired
    private ProductsRepository productsRepository;

    public List<ProductVariant> getAllProductVariants() {
        return productVariantRepository.findAll();
    }

    public ProductVariant getProductVariantById(Long id) throws ProductException {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + id));
    }

    public List<ProductVariant> getProductVariantsByProductId(Long productId) throws ProductException {
 Products product = productsRepository.findById(productId)
 .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
 return productVariantRepository.findByProduct(product);
    }

    public Optional<ProductVariant> getAvailableProductVariantBySku(String sku) {
 return productVariantRepository.findBySku(sku).filter(ProductVariant::getAvailable);
    }

    public List<ProductVariant> getAvailableProductVariantsByProductId(Long productId) throws ProductException {
 Products product = productsRepository.findById(productId)
 .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
 return productVariantRepository.findByProductAndAvailableTrue(product);
    }

    public ProductVariant createProductVariant(ProductVariant productVariant) throws ProductException {
        if (productVariant == null) {
            throw new ProductException("Product Variant cannot be null");
        }
 Optional<ProductVariant> existingVariant = productVariantRepository.findBySku(productVariant.getSku());
        if (existingVariant.isPresent()) {
 throw new ProductException("Product Variant with SKU " + productVariant.getSku() + " already exists.");
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
 if (productVariantDetails.getSku() != null) {
 Optional<ProductVariant> existingVariant = productVariantRepository.findBySku(productVariantDetails.getSku());
 if (existingVariant.isPresent() && !existingVariant.get().getId().equals(id)) {
 throw new ProductException("Product Variant with SKU " + productVariantDetails.getSku() + " already exists.");
                }
                productVariant.setSku(productVariantDetails.getSku());
            }
 if (productVariantDetails.getVariantName() != null)
                productVariant.setVariantName(productVariantDetails.getVariantName());
 if (productVariantDetails.getAdditionalPrice() != null)
                productVariant.setAdditionalPrice(productVariantDetails.getAdditionalPrice());
 if (productVariantDetails.getAvailable() != null)
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