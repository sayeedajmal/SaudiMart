package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

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
import com.saudiMart.Product.Repository.CategoryRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public Products getProductById(Long productId) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        product.setImages(productImageRepository.findByProduct(product));
        product.setSpecifications(productSpecificationRepository.findByProduct(product));
        product.setPriceTiers(priceTierRepository.findByProduct(product));
        inventoryRepository.findByProduct(product).ifPresent(product::setInventory);
        product.setVariants(productVariantRepository.findByProduct(product));

        return product;
    }

    public List<Products> getProductsBySellerId(Long sellerId) {
        return productsRepository.findBySellerId(sellerId);
    }

    public List<Products> getAvailableProductsBySellerId(Long sellerId) {
        return productsRepository.findBySellerId(sellerId).stream()
                .filter(Products::getAvailable)
                .collect(Collectors.toList());
    }

    public List<Products> getProductsByCategoryId(Long categoryId) throws ProductException {
         Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductException("Category not found with id: " + categoryId));
        return productsRepository.findByCategory(category);
    }

     public List<Products> getAvailableProductsByCategoryId(Long categoryId) throws ProductException {
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
        List<ProductImage> existingImages = productImageRepository.findByProduct(product);

        Map<Long, ProductImage> existingImagesMap = existingImages.stream()
                .filter(image -> image.getId() != null)
                .collect(Collectors.toMap(ProductImage::getId, image -> image));

        Map<Long, ProductImage> incomingImagesMap = new HashMap<>();
        if (incomingImages != null) {
            incomingImages.stream()
                    .filter(image -> image.getId() != null)
                    .forEach(image -> incomingImagesMap.put(image.getId(), image));
        }

        List<ProductImage> imagesToSave = new ArrayList<>();
        List<ProductImage> imagesToDelete = new ArrayList<>();

        if (incomingImages != null) {
            for (ProductImage incomingImage : incomingImages) {
                if (incomingImage.getId() != null && existingImagesMap.containsKey(incomingImage.getId())) {
                    ProductImage existingImage = existingImagesMap.get(incomingImage.getId());
                    existingImage.setImageUrl(incomingImage.getImageUrl());
                    existingImage.setAltText(incomingImage.getAltText());
                    existingImage.setDisplayOrder(incomingImage.getDisplayOrder());
                    existingImage.setIsPrimary(incomingImage.getIsPrimary());
                    imagesToSave.add(existingImage);
                } else {
                    incomingImage.setProduct(product);
                    imagesToSave.add(incomingImage);
                }
            }
        }

        for (ProductImage existingImage : existingImages) {
            if (existingImage.getId() != null && !incomingImagesMap.containsKey(existingImage.getId())) {
                imagesToDelete.add(existingImage);
            }
        }

        productImageRepository.deleteAll(imagesToDelete);
        productImageRepository.saveAll(imagesToSave);
    }


    private void updateProductSpecifications(Products product, List<ProductSpecification> incomingSpecs) {
         List<ProductSpecification> existingSpecs = productSpecificationRepository.findByProduct(product);

         Map<Long, ProductSpecification> existingSpecsMap = existingSpecs.stream()
                .filter(spec -> spec.getId() != null)
                .collect(Collectors.toMap(ProductSpecification::getId, spec -> spec));

        Map<Long, ProductSpecification> incomingSpecsMap = new HashMap<>();
        if (incomingSpecs != null) {
            incomingSpecs.stream()
                    .filter(spec -> spec.getId() != null)
                    .forEach(spec -> incomingSpecsMap.put(spec.getId(), spec));
        }

        List<ProductSpecification> specsToSave = new ArrayList<>();
        List<ProductSpecification> specsToDelete = new ArrayList<>();

        // Process incoming specifications
        if (incomingSpecs != null) {
            for (ProductSpecification incomingSpec : incomingSpecs) {
                if (incomingSpec.getId() != null && existingSpecsMap.containsKey(incomingSpec.getId())) {
                    // Existing specification - update it
                    ProductSpecification existingSpec = existingSpecsMap.get(incomingSpec.getId());
                    existingSpec.setSpecName(incomingSpec.getSpecName());
                    existingSpec.setSpecValue(incomingSpec.getSpecValue());
                    existingSpec.setUnit(incomingSpec.getUnit());
                    existingSpec.setDisplayOrder(incomingSpec.getDisplayOrder());
                    specsToSave.add(existingSpec);
                } else if (incomingSpec.getId() == null) {
                    // New specification - set product and add to save list
                    incomingSpec.setProduct(product);
                    specsToSave.add(incomingSpec);
                }
            }
        }

        // Process existing specifications to identify deletions
        for (ProductSpecification existingSpec : existingSpecs) {
            if (existingSpec.getId() != null && !incomingSpecsMap.containsKey(existingSpec.getId())) {
                // Existing specification not in incoming list - mark for deletion
                specsToDelete.add(existingSpec);
            }
        }

        // Perform deletions and saves
        productSpecificationRepository.deleteAll(specsToDelete);
        productSpecificationRepository.saveAll(specsToSave);
    }


    private void updatePriceTiers(Products product, List<PriceTier> incomingTiers) {
        List<PriceTier> existingTiers = priceTierRepository.findByProduct(product);

        Map<Long, PriceTier> existingTiersMap = existingTiers.stream()
                .filter(tier -> tier.getId() != null)
                .collect(Collectors.toMap(PriceTier::getId, tier -> tier));

        Map<Long, PriceTier> incomingTiersMap = new HashMap<>();
        if (incomingTiers != null) {
            incomingTiers.stream()
                    .filter(tier -> tier.getId() != null)
                    .forEach(tier -> incomingTiersMap.put(tier.getId(), tier));
        }

        List<PriceTier> tiersToSave = new ArrayList<>();
        List<PriceTier> tiersToDelete = new ArrayList<>();

        if (incomingTiers != null) {
            for (PriceTier incomingTier : incomingTiers) {
                if (incomingTier.getId() != null && existingTiersMap.containsKey(incomingTier.getId())) {
                    PriceTier existingTier = existingTiersMap.get(incomingTier.getId());
                    // Update properties as needed, excluding product association
                    existingTier.setMinimumQuantity(incomingTier.getMinimumQuantity());
                    existingTier.setMaxQuantity(incomingTier.getMaxQuantity());
                    existingTier.setPricePerUnit(incomingTier.getPricePerUnit());
                    existingTier.setDiscountPercent(incomingTier.getDiscountPercent());
                    existingTier.setIsActive(incomingTier.getIsActive());
                    tiersToSave.add(existingTier);
                } else {
                    incomingTier.setProduct(product);
                    tiersToSave.add(incomingTier);
                }
            }
        }

        // Identify price tiers to delete (existing tiers not in the incoming list)
        for (PriceTier existingTier : existingTiers) {
            if (existingTier.getId() != null && !incomingTiersMap.containsKey(existingTier.getId())) {
                tiersToDelete.add(existingTier);
            }
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
        List<ProductVariant> existingVariants = productVariantRepository.findByProduct(product);

        Map<Long, ProductVariant> existingVariantsMap = existingVariants.stream()
                .filter(variant -> variant.getId() != null)
                .collect(Collectors.toMap(ProductVariant::getId, variant -> variant));

        Map<Long, ProductVariant> incomingVariantsMap = new HashMap<>();
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
                    // Update properties as needed, excluding product association
                    existingVariant.setSku(incomingVariant.getSku());
                    existingVariant.setVariantName(incomingVariant.getVariantName());
                    existingVariant.setAdditionalPrice(incomingVariant.getAdditionalPrice());
                    existingVariant.setAvailable(incomingVariant.getAvailable());
                    variantsToSave.add(existingVariant);
                } else {
                    incomingVariant.setProduct(product);
                    variantsToSave.add(incomingVariant);
                }
            }
        }

        // Identify variants to delete (existing variants not in the incoming list)
        for (ProductVariant existingVariant : existingVariants) {
            if (existingVariant.getId() != null && !incomingVariantsMap.containsKey(existingVariant.getId())) {
                variantsToDelete.add(existingVariant);
            }
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
