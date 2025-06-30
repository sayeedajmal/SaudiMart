package com.saudiMart.Product.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.CategoryRepository;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import org.springframework.data.domain.Pageable;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Page<Products> getAllProducts(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    public Products getProductById(String productId) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        // product.setImages(productImageRepository.findByProduct(product));
        product.setSpecifications(productSpecificationRepository.findByProduct(product));
        product.setVariants(productVariantRepository.findByProduct(product));

        return product;
    }

    public Page<Products> getProductsBySellerId(String sellerId, Pageable pageable) {
        return productsRepository.findBySellerId(sellerId, pageable);
    }

    public Page<Products> getAvailableProductsBySellerId(String sellerId, Pageable pageable) {
        return productsRepository.findBySellerId(sellerId, pageable);
    }

    public Page<Products> getProductsByCategoryId(String categoryId, Pageable pageable) throws ProductException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductException("Category not found with id: " + categoryId));
        return productsRepository.findByCategory(category, pageable);
    }

    public Page<Products> getAvailableProductsByCategoryId(String categoryId, Pageable pageable) throws ProductException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductException("Category not found with id: " + categoryId));
        // Assuming findByCategory also handles 'available' or you'd add a custom method
        return productsRepository.findByCategory(category, pageable);
    }

    public Page<Products> searchProducts(String keywords, String categoryId, String sellerId, Boolean available, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) throws ProductException {
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ProductException("Category not found with id: " + categoryId));
        }

        return productsRepository.searchProducts(
                keywords, category, sellerId, available, minPrice, maxPrice, pageable
        );
    }

    @Transactional
    public Products createProduct(Products product) throws ProductException {
        if (product == null) {
            throw new ProductException("Product cannot be null");
        }

        if (product.getSpecifications() != null) {
            product.getSpecifications().forEach(spec -> spec.setProduct(product));
        }

        if (product.getVariants() != null) {
            product.getVariants().forEach(variant -> variant.setProduct(product));
        }

        return productsRepository.save(product);
    }

    @Transactional
    public Products updateProduct(String productId, Products newProduct) throws ProductException {
        if (newProduct == null) {
            throw new ProductException("Product details cannot be null");
        }

        Products oldProduct = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        if (newProduct.getName() != null)
            oldProduct.setName(newProduct.getName());
        if (newProduct.getDescription() != null)
            oldProduct.setDescription(newProduct.getDescription());
        if (newProduct.getBasePrice() != null)
            oldProduct.setBasePrice(newProduct.getBasePrice());
        if (newProduct.getWeight() != null)
            oldProduct.setWeight(newProduct.getWeight());
        if (newProduct.getWeightUnit() != null)
            oldProduct.setWeightUnit(newProduct.getWeightUnit());
        if (newProduct.getDimensions() != null)
            oldProduct.setDimensions(newProduct.getDimensions());
        if (newProduct.getSku() != null)
            oldProduct.setSku(newProduct.getSku());
        if (newProduct.getAvailable() != null)
            oldProduct.setAvailable(newProduct.getAvailable());

        updateProductSpecifications(oldProduct, newProduct.getSpecifications());
        updateProductVariants(oldProduct, newProduct.getVariants());

        return productsRepository.save(oldProduct);
    }

    private void updateProductSpecifications(Products oldProduct, List<ProductSpecification> incomingSpecs) {
        List<ProductSpecification> existingSpecs = productSpecificationRepository.findByProduct(oldProduct);

        Map<String, ProductSpecification> existingSpecsMap = existingSpecs.stream()
                .filter(spec -> spec.getId() != null)
                .collect(Collectors.toMap(ProductSpecification::getId, spec -> spec));

        List<ProductSpecification> specsToSave = new ArrayList<>();

        if (incomingSpecs != null) {
            for (ProductSpecification incomingSpec : incomingSpecs) {
                if (incomingSpec.getId() != null) {
                    ProductSpecification existingSpec = existingSpecsMap.get(incomingSpec.getId());
                    if (existingSpec != null) {
                        existingSpec.setSpecName(incomingSpec.getSpecName());
                        existingSpec.setSpecValue(incomingSpec.getSpecValue());
                        existingSpec.setUnit(incomingSpec.getUnit());
                        existingSpec.setDisplayOrder(incomingSpec.getDisplayOrder());
                        specsToSave.add(existingSpec);
                    }
                } else {
                    incomingSpec.setProduct(oldProduct);
                    specsToSave.add(incomingSpec);
                }
            }
        }

        productSpecificationRepository.saveAll(specsToSave);
    }

    private void updateProductVariants(Products product, List<ProductVariant> incomingVariants) {
        List<ProductVariant> existingVariants = productVariantRepository.findByProduct(product);

        Map<String, ProductVariant> existingVariantsMap = existingVariants.stream()
                .filter(variant -> variant.getId() != null)
                .collect(Collectors.toMap(ProductVariant::getId, variant -> variant));

        Map<String, ProductVariant> incomingVariantsMap = new HashMap<>();
        if (incomingVariants != null) {
            incomingVariants.stream()
                    .filter(variant -> variant.getId() != null)
                    .forEach(variant -> incomingVariantsMap.put(variant.getId(), variant));
        }

        List<ProductVariant> variantsToSave = new ArrayList<>();
        List<ProductVariant> variantsToDelete = new ArrayList<>();

        if (incomingVariants != null) {
            for (ProductVariant incomingVariant : incomingVariants) {
                if (incomingVariant.getId() != null && existingVariantsMap.containsKey(incomingVariant.getId())) {
                    ProductVariant existingVariant = existingVariantsMap.get(incomingVariant.getId());
                    existingVariant.setSku(incomingVariant.getSku());
                    existingVariant.setVariantName(incomingVariant.getVariantName());
                    existingVariant.setAvailable(incomingVariant.getAvailable());
                    variantsToSave.add(existingVariant);
                } else {
                    incomingVariant.setProduct(product);
                    variantsToSave.add(incomingVariant);
                }
            }
        }

        for (ProductVariant existingVariant : existingVariants) {
            if (existingVariant.getId() != null && !incomingVariantsMap.containsKey(existingVariant.getId())) {
                variantsToDelete.add(existingVariant);
            }
        }
    }

    @Transactional
    public void deleteProduct(String productId) throws ProductException {
       if(productsRepository.findById(productId).isEmpty()){
           throw new ProductException("Product not found");
       }
        productsRepository.deleteById(productId);
    }
}
