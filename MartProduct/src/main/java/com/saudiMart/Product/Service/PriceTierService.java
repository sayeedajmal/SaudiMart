package com.saudiMart.Product.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class PriceTierService {

    @Autowired
    private PriceTierRepository priceTierRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

 public Page<PriceTier> getAllPriceTiers(Pageable pageable) {
 return priceTierRepository.findAll(pageable);
    }

    public PriceTier getPriceTierById(String priceTierId) throws ProductException {
        return priceTierRepository.findById(priceTierId)
                .orElseThrow(() -> new ProductException("Price Tier not found with id: " + priceTierId));
    }

 public Page<PriceTier> getPriceTiersByVariantId(String variantId, Pageable pageable) throws ProductException {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
 return priceTierRepository.findByVariant(variant, pageable);
    }

 public Page<PriceTier> getActivePriceTiersByVariantId(String variantId, Pageable pageable) throws ProductException {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
 return priceTierRepository.findByVariantAndIsActiveTrue(variant, pageable);
    }

    public List<PriceTier> getApplicablePriceTiers(String variantId, Integer quantity) throws ProductException {
        if (quantity == null || quantity <= 0) {
            throw new ProductException("Quantity must be positive");
        }
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
 return priceTierRepository.findByVariantAndIsActiveTrue(variant, Pageable.unpaged()).stream() // Applicable tiers should not be paginated in this specific method
                .filter(tier -> quantity >= tier.getMinQuantity()
                        && (tier.getMaxQuantity() == null || quantity <= tier.getMaxQuantity()))
                .collect(Collectors.toList());
    }

    public PriceTier createPriceTier(PriceTier priceTier) throws ProductException {
        if (priceTier == null) {
            throw new ProductException("Price Tier cannot be null");
        }
        // Ensure the associated variant exists
        ProductVariant variant = productVariantRepository.findById(priceTier.getVariant().getId())
                .orElseThrow(() -> new ProductException(
                        "Product Variant not found with id: " + priceTier.getVariant().getId()));
        priceTier.setVariant(variant);

        return priceTierRepository.save(priceTier);
    }

    public void deletePriceTier(String priceTierId) throws ProductException {
        if (!priceTierRepository.existsById(priceTierId)) {
            throw new ProductException("Price Tier not found with id: " + priceTierId);
        }
        priceTierRepository.deleteById(priceTierId);
    }

    public PriceTier updatePriceTier(String id, PriceTier priceTierDetails) throws ProductException {
        PriceTier existingPriceTier = getPriceTierById(id);

        if (priceTierDetails.getMinQuantity() != null) {
            existingPriceTier.setMinQuantity(priceTierDetails.getMinQuantity());
        }
        if (priceTierDetails.getMaxQuantity() != null) {
            existingPriceTier.setMaxQuantity(priceTierDetails.getMaxQuantity());
        }
        if (priceTierDetails.getPricePerUnit() != null) {
            existingPriceTier.setPricePerUnit(priceTierDetails.getPricePerUnit());
        }
        if (priceTierDetails.getDiscountPercent() != null) {
            existingPriceTier.setDiscountPercent(priceTierDetails.getDiscountPercent());
        }
        if (priceTierDetails.getIsActive() != null) {
            existingPriceTier.setIsActive(priceTierDetails.getIsActive());
        }

        // Ensure the variant association is not changed during update
        if (priceTierDetails.getVariant() != null
                && !existingPriceTier.getVariant().getId().equals(priceTierDetails.getVariant().getId())) {
            throw new ProductException("Cannot change the associated product variant for a price tier during update.");
        }
        return priceTierRepository.save(existingPriceTier);
    }

 public Page<PriceTier> searchPriceTiers(
            String variantId,
 Integer minQuantity,
 Integer maxQuantity,
 BigDecimal minPricePerUnit,
 BigDecimal maxPricePerUnit,
 Boolean isActive,
 Pageable pageable) throws ProductException {
 ProductVariant variant = null;
 if (variantId != null) {
 variant = productVariantRepository.findById(variantId)
 .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
 }

 return priceTierRepository.searchPriceTiers(
 variant,
 minQuantity,
 maxQuantity,
 minPricePerUnit,
 maxPricePerUnit,
 isActive,
 pageable);
 }
}