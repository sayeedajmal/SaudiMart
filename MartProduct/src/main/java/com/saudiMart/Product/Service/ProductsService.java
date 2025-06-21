package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.Inventory;
import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;
import java.util.ArrayList;
import java.util.HashSet;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Repository.ProductImageRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.saudiMart.Product.Utils.ProductException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

 Optional<Inventory> inventoryOptional = inventoryRepository.findByProduct(product);
 if (inventoryOptional.isPresent()) {
 product.setInventory(inventoryOptional.get());
 }

 product.setVariants(productVariantRepository.findByProduct(product));

        return product;
    }

    @Transactional
    public Products createProduct(Products product) throws ProductException {
        if (product == null) {
            throw new ProductException("Product cannot be null");
        }
 Products savedProduct = productsRepository.save(product);

 if (product.getImages() != null) {
 for (ProductImage image : product.getImages()) {
 image.setProduct(savedProduct);
 productImageRepository.save(image);
 }
 }

 if (product.getSpecifications() != null) {
 for (ProductSpecification specification : product.getSpecifications()) {
 specification.setProduct(savedProduct);
 productSpecificationRepository.save(specification);
 }
 }

 if (product.getPriceTiers() != null) {
 for (PriceTier priceTier : product.getPriceTiers()) {
 priceTier.setProduct(savedProduct);
 priceTierRepository.save(priceTier);
 }
 }

 if (product.getInventory() != null) {
 product.getInventory().setProduct(savedProduct);
 inventoryRepository.save(product.getInventory());
 }

 return savedProduct;
    }

    @Transactional
    public Products updateProduct(Long productId, Products productDetails) throws ProductException {
        if (productDetails == null) {
            throw new ProductException("Product details cannot be null");
        }

        Products existingProduct = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        if (productDetails.getName() != null) existingProduct.setName(productDetails.getName());
        if (productDetails.getDescription() != null) existingProduct.setDescription(productDetails.getDescription());
        if (productDetails.getBasePrice() != null) existingProduct.setBasePrice(productDetails.getBasePrice());
        if (productDetails.getCategory() != null) existingProduct.setCategory(productDetails.getCategory());
        if (productDetails.getIsBulkOnly() != null) existingProduct.setIsBulkOnly(productDetails.getIsBulkOnly());
        if (productDetails.getMinimumOrderQuantity() != null) existingProduct.setMinimumOrderQuantity(productDetails.getMinimumOrderQuantity());
        if (productDetails.getWeight() != null) existingProduct.setWeight(productDetails.getWeight());
        if (productDetails.getWeightUnit() != null) existingProduct.setWeightUnit(productDetails.getWeightUnit());
        if (productDetails.getDimensions() != null) existingProduct.setDimensions(productDetails.getDimensions());
        if (productDetails.getSku() != null) existingProduct.setSku(productDetails.getSku());
        if (productDetails.getAvailable() != null) existingProduct.setAvailable(productDetails.getAvailable());


        updateProductImages(existingProduct, productDetails.getImages());

        updateProductSpecifications(existingProduct, productDetails.getSpecifications());

        updatePriceTiers(existingProduct, productDetails.getPriceTiers());

        updateInventory(existingProduct, productDetails.getInventory());

        updateProductVariants(existingProduct, productDetails.getVariants());

        return productsRepository.save(existingProduct);
    }

    private void updateProductImages(Products product, List<ProductImage> incomingImages) {
        if (incomingImages == null) {
        } else {
            List<ProductImage> existingImages = productImageRepository.findByProduct(product);
            new HashSet<>(existingImages).stream()
                    .forEach(productImageRepository::delete);

            incomingImages.stream()
                    .forEach(newImage -> {
                        newImage.setProduct(product);
                        productImageRepository.save(newImage);
                    });

        }
    }

    private void updateProductSpecifications(Products product, List<ProductSpecification> incomingSpecifications) {
        if (incomingSpecifications == null) {
            productSpecificationRepository.deleteByProduct(product);
        } else {
        }
    }

    private void updatePriceTiers(Products product, List<PriceTier> incomingPriceTiers) {
        if (incomingPriceTiers == null) {
            priceTierRepository.deleteByProduct(product);
        } else {
        }
    }

    private void updateInventory(Products product, Inventory incomingInventory) {
        Optional<Inventory> existingInventoryOptional = inventoryRepository.findByProduct(product);

        if (incomingInventory != null) {
            if (existingInventoryOptional.isPresent()) {
                Inventory existingInventory = existingInventoryOptional.get();
                 existingInventory.setQuantity(incomingInventory.getQuantity());
                 existingInventory.setReservedQuantity(incomingInventory.getReservedQuantity());
                inventoryRepository.save(existingInventory);
            } else {
                incomingInventory.setProduct(product);
                inventoryRepository.save(incomingInventory);
            }
        } else {
            existingInventoryOptional.ifPresent(inventoryRepository::delete);
        }
    }

    private void updateProductVariants(Products product, List<ProductVariant> incomingVariants) {
         if (incomingVariants == null) {
            productVariantRepository.deleteByProduct(product);
        } else {
            List<ProductVariant> existingVariants = productVariantRepository.findByProduct(product);
            new HashSet<>(existingVariants).stream()
                    .forEach(productVariantRepository::delete);

            incomingVariants.stream()
                    .forEach(incomingVariant -> {
                            incomingVariant.setProduct(product);
                            productVariantRepository.save(incomingVariant);
                        } else {
                            Optional<ProductVariant> existingVariantOptional = productVariantRepository.findById(incomingVariant.getId());
                             existingVariantOptional.ifPresent(existingVariant -> {
                                 existingVariant.setVariantName(incomingVariant.getVariantName());
                                 existingVariant.setAdditionalPrice(incomingVariant.getAdditionalPrice());
                                 productVariantRepository.save(existingVariant);
                             });
                        }
                    });
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Optional<Products> productOptional = productsRepository.findById(productId);
        if (productOptional.isPresent()) {
            Products product = productOptional.get();

            productImageRepository.deleteByProduct(product);
            productSpecificationRepository.deleteByProduct(product);
            priceTierRepository.deleteByProduct(product);
            inventoryRepository.deleteByProduct(product);
            productVariantRepository.deleteByProduct(product);

            productsRepository.deleteById(productId);
        } else {
        }
    }
}