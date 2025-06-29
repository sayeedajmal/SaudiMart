package com.saudiMart.Product.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Repository.ProductImageRepository;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private PriceTierRepository priceTierRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    public List<ProductVariant> getAllProductVariants() {
        return productVariantRepository.findAll();
    }

    public ProductVariant getProductVariantById(String id) throws ProductException {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + id));
    }

    public List<ProductVariant> getProductVariantsByProductId(String productId) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        return productVariantRepository.findByProduct(product);
    }

    public Optional<ProductVariant> getAvailableProductVariantBySku(String sku) {
        return productVariantRepository.findBySku(sku).filter(ProductVariant::getAvailable);
    }

    public List<ProductVariant> getAvailableProductVariantsByProductId(String productId) throws ProductException {
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

        // Save the product variant first to get an ID
        ProductVariant savedVariant = productVariantRepository.save(productVariant);

        // Handle Product Images
        if (productVariant.getImages() != null) {
            productVariant.getImages().forEach(image -> image.setVariant(savedVariant));
        }

        if (productVariant.getSpecifications() != null) {
            productVariant.getSpecifications().forEach(spec -> spec.setVariant(savedVariant));
        }

        return productVariantRepository.save(savedVariant);
    }

    public ProductVariant updateProductVariant(String id, ProductVariant productVariantDetails)
            throws ProductException {
        if (productVariantDetails == null) {
            throw new ProductException("Product Variant details cannot be null for update");
        }
        Optional<ProductVariant> productVariantOptional = productVariantRepository.findById(id);
        if (productVariantOptional.isPresent()) {
            ProductVariant productVariant = productVariantOptional.get();
            if (productVariantDetails.getSku() != null) {
                Optional<ProductVariant> existingVariant = productVariantRepository
                        .findBySku(productVariantDetails.getSku());
                if (existingVariant.isPresent() && !existingVariant.get().getId().equals(id)) {
                    throw new ProductException(
                            "Product Variant with SKU " + productVariantDetails.getSku() + " already exists.");
                }
                productVariant.setSku(productVariantDetails.getSku());
            }
            if (productVariantDetails.getVariantName() != null)
                productVariant.setVariantName(productVariantDetails.getVariantName());
            // additionalPrice removed as per schema change
            if (productVariantDetails.getBasePrice() != null)
                productVariant.setBasePrice(productVariantDetails.getBasePrice());
            if (productVariantDetails.getAvailable() != null)
                productVariant.setAvailable(productVariantDetails.getAvailable());

            // Use helper methods to update associated collections
            updateVariantImages(productVariant, productVariantDetails.getImages());
            updateVariantSpecifications(productVariant, productVariantDetails.getSpecifications());
            updateProductSpecifications(productVariant, productVariant.getSpecifications());
            
            return productVariantRepository.save(productVariant);
        }
        throw new ProductException("Product Variant not found with id: " + id);
    }

    // Helper method to update ProductSpecification collection for a ProductVariant
    private void updateVariantSpecifications(ProductVariant variant, List<ProductSpecification> incomingSpecs) {
        List<ProductSpecification> existingSpecs = productSpecificationRepository.findByVariant(variant);

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
                    incomingSpec.setVariant(variant);
                    specsToSave.add(incomingSpec);
                }
            }
        }

        productSpecificationRepository.saveAll(specsToSave);
    }

    // Helper method to update ProductImage collection for a ProductVariant
    private void updateVariantImages(ProductVariant variant, List<ProductImage> incomingImages) {
        List<ProductImage> existingImages = productImageRepository.findByVariant(variant);

        Map<String, ProductImage> existingImagesMap = existingImages.stream()
                .filter(image -> image.getId() != null)
                .collect(Collectors.toMap(ProductImage::getId, image -> image));

        if (newImages != null) {
            for (ProductImage incomingImage : incomingImages) {
                Optional<ProductImage> existingImageOpt = Optional.ofNullable(existingImagesMap.get(incomingImage.getId()));
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
                } else if (incomingImage.getId() == null){ // Treat as new if no ID
                    incomingImage.setVariant(oldProduct);
                    existingImages.add(incomingImage);
                }
            }
        }

        productImageRepository.saveAll(existingImages);

        Set<String> incomingImageIds = incomingImages != null ? incomingImages.stream()
                .filter(image -> image.getId() != null)
                .map(ProductImage::getId)
                .collect(Collectors.toSet()) : new HashSet<>();

        existingImages.stream().filter(image -> !incomingImageIds.contains(image.getId())).forEach(productImageRepository::delete);
    }

    public void deleteProductVariant(String id) throws ProductException {
        if (!productVariantRepository.existsById(id)) {
            throw new ProductException("Product Variant not found with id: " + id);
        }
        productVariantRepository.deleteById(id);
    }

    // Price Tier methods
    public PriceTier addPriceTierToVariant(String variantId, PriceTier priceTier) throws ProductException {
        ProductVariant variant = getProductVariantById(variantId);
        if (priceTier == null) {
            throw new ProductException("Price Tier cannot be null");
        }
        priceTier.setVariant(variant);
        return priceTierRepository.save(priceTier);
    }

    public PriceTier updatePriceTier(String tierId, PriceTier updatedTierDetails) throws ProductException {
        Optional<PriceTier> tierOptional = priceTierRepository.findById(tierId);
        if (tierOptional.isPresent()) {
            PriceTier existingTier = tierOptional.get();
            if (updatedTierDetails.getMinQuantity() != null)
                existingTier.setMinQuantity(updatedTierDetails.getMinQuantity());
            if (updatedTierDetails.getMaxQuantity() != null)
                existingTier.setMaxQuantity(updatedTierDetails.getMaxQuantity());
            if (updatedTierDetails.getPricePerUnit() != null)
                existingTier.setPricePerUnit(updatedTierDetails.getPricePerUnit());
            if (updatedTierDetails.getDiscountPercent() != null)
                existingTier.setDiscountPercent(updatedTierDetails.getDiscountPercent());
            if (updatedTierDetails.getIsActive() != null)
                existingTier.setIsActive(updatedTierDetails.getIsActive());

            return priceTierRepository.save(existingTier);
        }
        throw new ProductException("Price Tier not found with id: " + tierId);
    }

    public void deletePriceTier(String tierId) throws ProductException {
        if (!priceTierRepository.existsById(tierId)) {
            throw new ProductException("Price Tier not found with id: " + tierId);
        }
        priceTierRepository.deleteById(tierId);
    }

    public List<PriceTier> getPriceTiersByVariantId(String variantId) throws ProductException {
        ProductVariant variant = getProductVariantById(variantId);
        return priceTierRepository.findByVariant(variant);
    }
}