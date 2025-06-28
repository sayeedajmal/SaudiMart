package com.saudiMart.Product.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.CategoryRepository;
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
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public Products getProductById(String productId) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        product.setImages(productImageRepository.findByProduct(product));
        product.setSpecifications(productSpecificationRepository.findByProduct(product));
        product.setVariants(productVariantRepository.findByProduct(product));

        return product;
    }

    public List<Products> getProductsBySellerId(String sellerId) {
        return productsRepository.findBySellerId(sellerId);
    }

    public List<Products> getAvailableProductsBySellerId(String sellerId) {
        return productsRepository.findBySellerId(sellerId).stream()
                .filter(Products::getAvailable)
                .collect(Collectors.toList());
    }

    public List<Products> getProductsByCategoryId(String categoryId) throws ProductException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductException("Category not found with id: " + categoryId));
        return productsRepository.findByCategory(category);
    }

    public List<Products> getAvailableProductsByCategoryId(String categoryId) throws ProductException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductException("Category not found with id: " + categoryId));
        return productsRepository.findByCategory(category).stream()
                .filter(Products::getAvailable)
                .collect(Collectors.toList());
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
        updateProductImages(oldProduct, newProduct.getImages());
        updateProductVariants(oldProduct, newProduct.getVariants());

        return productsRepository.save(oldProduct);
    }

    private void updateProductImages(Products oldProduct, List<ProductImage> newImages) {
        List<ProductImage> existingImages = productImageRepository.findByProduct(oldProduct);

        if (newImages != null) {
            for (ProductImage incomingImage : newImages) {
                Optional<ProductImage> existingImageOpt = existingImages.stream()
                        .filter(image -> image.getId() != null && image.getId().equals(incomingImage.getId()))
                        .findFirst();

                if (existingImageOpt.isPresent()) {
                    ProductImage existingImage = existingImageOpt.get();
                    if (incomingImage.getImageUrl() != null)
                        existingImage.setImageUrl(incomingImage.getImageUrl());
                    if (incomingImage.getAltText() != null)
                        existingImage.setAltText(incomingImage.getAltText());
                    if (incomingImage.getDisplayOrder() != null)
                        existingImage.setDisplayOrder(incomingImage.getDisplayOrder());
                    if (incomingImage.getIsPrimary() != null)
                        existingImage.setIsPrimary(incomingImage.getIsPrimary());
                } else {
                    incomingImage.setProduct(oldProduct);
                    existingImages.add(incomingImage);
                }
            }
        }

        productImageRepository.saveAll(existingImages);
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
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        productImageRepository.deleteByProduct(product);
        productSpecificationRepository.deleteByProduct(product);
        productVariantRepository.deleteByProduct(product);

        productsRepository.delete(product);
    }
}
