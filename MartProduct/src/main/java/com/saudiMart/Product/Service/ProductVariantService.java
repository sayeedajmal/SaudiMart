package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductImage;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Repository.ProductImageRepository;
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

    public Page<ProductVariant> getAllProductVariants(Pageable pageable) {
        return productVariantRepository.findAll(pageable);
    }

    public ProductVariant getProductVariantById(String id) throws ProductException {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + id));
    }

    public Page<ProductVariant> getProductVariantsByProductId(String productId, Pageable pageable)
            throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        return productVariantRepository.findByProduct(product, pageable);
    }

    public Optional<ProductVariant> getAvailableProductVariantBySku(String sku) {
        return productVariantRepository.findBySku(sku).filter(ProductVariant::getAvailable);
    }

    public List<ProductVariant> getAvailableProductVariantsByProductId(String productId) throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        return productVariantRepository.findByProductAndAvailableTrue(product);
    }

    public Page<ProductVariant> searchProductVariants(String productId, String sku, Boolean available,
            Pageable pageable) throws ProductException {
        Products product = null;
        if (productId != null) {
            product = productsRepository.findById(productId)
                    .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        }
        return productVariantRepository.searchProductVariants(product, sku, available, pageable);
    }

    public ProductVariant createProductVariant(ProductVariant productVariant) throws ProductException {
        if (productVariant == null) {
            throw new ProductException("Product Variant cannot be null");
        }
        Optional<ProductVariant> existingVariant = productVariantRepository.findBySku(productVariant.getSku());
        if (existingVariant.isPresent()) {
            throw new ProductException("Product Variant with SKU " + productVariant.getSku() + " already exists.");
        }

        ProductVariant savedVariant = productVariantRepository.save(productVariant);

        if (productVariant.getImages() != null) {
            productVariant.getImages().forEach(image -> image.setVariant(savedVariant));
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

            if (productVariantDetails.getBasePrice() != null)
                productVariant.setBasePrice(productVariantDetails.getBasePrice());
            if (productVariantDetails.getAvailable() != null)
                productVariant.setAvailable(productVariantDetails.getAvailable());

            updateVariantImages(productVariant, productVariantDetails.getImages());

            return productVariantRepository.save(productVariant);
        }
        throw new ProductException("Product Variant not found with id: " + id);
    }

    private void updateVariantImages(ProductVariant variant, List<ProductImage> newImages) {
        @SuppressWarnings("unchecked")
        List<ProductImage> existingImages = (List<ProductImage>) productImageRepository.findByVariant(variant);

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
                    incomingImage.setVariant(variant);
                    existingImages.add(incomingImage);
                }
            }
        }

        productImageRepository.saveAll(existingImages);
    }

    public void deleteProductVariant(String id) throws ProductException {
        if (!productVariantRepository.existsById(id)) {
            throw new ProductException("Product Variant not found with id: " + id);
        }
        productVariantRepository.deleteById(id);
    }

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

    public Page<PriceTier> getPriceTiersByVariantId(String variantId, Pageable pageable) throws ProductException {
        ProductVariant variant = getProductVariantById(variantId);
        return priceTierRepository.findByVariant(variant, pageable);
    }
}