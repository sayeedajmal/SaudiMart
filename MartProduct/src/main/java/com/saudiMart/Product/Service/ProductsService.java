package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.InventoryRepository;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Repository.ProductImageRepository;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    @Autowired
    private PriceTierRepository priceTierRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Transactional
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public Products getProductById(Long productId) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        product.setSpecifications(productSpecificationRepository.findByProduct(product));
        product.setPriceTiers(priceTierRepository.findByProduct(product));
        inventoryRepository.findByProduct(product).ifPresent(product::setInventory);
        product.setVariants(productVariantRepository.findByProduct(product));

        return product;
    }

    @Transactional
    public Products createProduct(Products product) throws ProductException {
        if (product == null) {
            throw new ProductException("Product cannot be null");
        }

        if (product.getImages() != null) {
            product.getImages().forEach(image -> image.setProduct(product));
        }

        if (product.getSpecifications() != null) {
            product.getSpecifications().forEach(spec -> spec.setProduct(product));
        }

        if (product.getPriceTiers() != null) {
            product.getPriceTiers().forEach(tier -> tier.setProduct(product));
        }

        if (product.getVariants() != null) {
            product.getVariants().forEach(variant -> variant.setProduct(product));
        }

        if (product.getInventory() != null) {
            product.getInventory().setProduct(product);
        }

        return productsRepository.save(product);
    }

    @Transactional
    public Products updateProduct(Long productId, Products productDetails) throws ProductException {
        if (productDetails == null) {
            throw new ProductException("Product details cannot be null");
        }

        Products existingProduct = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        if (productDetails.getName() != null)
            existingProduct.setName(productDetails.getName());
        if (productDetails.getDescription() != null)
            existingProduct.setDescription(productDetails.getDescription());
        if (productDetails.getBasePrice() != null)
            existingProduct.setBasePrice(productDetails.getBasePrice());
        if (productDetails.getCategory() != null)
            existingProduct.setCategory(productDetails.getCategory());
        if (productDetails.getIsBulkOnly() != null)
            existingProduct.setIsBulkOnly(productDetails.getIsBulkOnly());
        if (productDetails.getMinimumOrderQuantity() != null)
            existingProduct.setMinimumOrderQuantity(productDetails.getMinimumOrderQuantity());
        if (productDetails.getWeight() != null)
            existingProduct.setWeight(productDetails.getWeight());
        if (productDetails.getWeightUnit() != null)
            existingProduct.setWeightUnit(productDetails.getWeightUnit());
        if (productDetails.getDimensions() != null)
            existingProduct.setDimensions(productDetails.getDimensions());
        if (productDetails.getSku() != null)
            existingProduct.setSku(productDetails.getSku());
        if (productDetails.getAvailable() != null)
            existingProduct.setAvailable(productDetails.getAvailable());

        updateProductImages(existingProduct, productDetails.getImages());
        updateProductSpecifications(existingProduct, productDetails.getSpecifications());
        updatePriceTiers(existingProduct, productDetails.getPriceTiers());
        updateInventory(existingProduct, productDetails.getInventory());
        updateProductVariants(existingProduct, productDetails.getVariants());

        return productsRepository.save(existingProduct);
    }

    private void updateProductImages(Products product, List<ProductImage> incomingImages) {
        productImageRepository.deleteByProduct(product);
        if (incomingImages != null) {
            incomingImages.forEach(image -> {
                image.setProduct(product);
                productImageRepository.save(image);
            });
        }
    }

    private void updateProductSpecifications(Products product, List<ProductSpecification> incomingSpecs) {
        productSpecificationRepository.deleteByProduct(product);
        if (incomingSpecs != null) {
            incomingSpecs.forEach(spec -> {
                spec.setProduct(product);
                productSpecificationRepository.save(spec);
            });
        }
    }

    private void updatePriceTiers(Products product, List<PriceTier> incomingTiers) {
        priceTierRepository.deleteByProduct(product);
        if (incomingTiers != null) {
            incomingTiers.forEach(tier -> {
                tier.setProduct(product);
                priceTierRepository.save(tier);
            });
        }
    }

    private void updateInventory(Products product, Inventory incomingInventory) {
        Optional<Inventory> existing = inventoryRepository.findByProduct(product);

        if (incomingInventory != null) {
            if (existing.isPresent()) {
                Inventory old = existing.get();
                old.setQuantity(incomingInventory.getQuantity());
                old.setReservedQuantity(incomingInventory.getReservedQuantity());
                inventoryRepository.save(old);
            } else {
                incomingInventory.setProduct(product);
                inventoryRepository.save(incomingInventory);
            }
        } else {
            existing.ifPresent(inventoryRepository::delete);
        }
    }

    private void updateProductVariants(Products product, List<ProductVariant> incomingVariants) {
        productVariantRepository.deleteByProduct(product);
        if (incomingVariants != null) {
            incomingVariants.forEach(variant -> {
                variant.setProduct(product);
                productVariantRepository.save(variant);
            });
        }
    }

    @Transactional
    public void deleteProduct(Long productId) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        productImageRepository.deleteByProduct(product);
        productSpecificationRepository.deleteByProduct(product);
        priceTierRepository.deleteByProduct(product);
        inventoryRepository.deleteByProduct(product);
        productVariantRepository.deleteByProduct(product);

        productsRepository.delete(product);
    }
}
