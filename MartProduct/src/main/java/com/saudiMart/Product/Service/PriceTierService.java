package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Model.ProductVariant;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ProductVariantRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class PriceTierService {

    @Autowired
    private PriceTierRepository priceTierRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public List<PriceTier> getAllPriceTiers() {
        return priceTierRepository.findAll();
    }

    public PriceTier getPriceTierById(Long priceTierId) throws ProductException {
        return priceTierRepository.findById(priceTierId)
                .orElseThrow(() -> new ProductException("Price Tier not found with id: " + priceTierId));
    }

    public List<PriceTier> getPriceTiersByVariantId(Long variantId) throws ProductException {
 ProductVariant variant = productVariantRepository.findById(variantId)
 .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
 return priceTierRepository.findByVariant(variant);
    }

    public List<PriceTier> getActivePriceTiersByVariantId(Long variantId) throws ProductException {
 ProductVariant variant = productVariantRepository.findById(variantId)
 .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
 return priceTierRepository.findByVariantAndIsActiveTrue(variant);
    }

 public List<PriceTier> getApplicablePriceTiers(Long variantId, Integer quantity) throws ProductException {
 if (quantity == null || quantity <= 0) {
 throw new ProductException("Quantity must be positive");
 }
 ProductVariant variant = productVariantRepository.findById(variantId)
 .orElseThrow(() -> new ProductException("Product Variant not found with id: " + variantId));
 return priceTierRepository.findByVariantAndIsActiveTrue(variant).stream().filter(tier -> quantity >= tier.getMinQuantity() && (tier.getMaxQuantity() == null || quantity <= tier.getMaxQuantity())).collect(Collectors.toList());
    }

    public PriceTier createPriceTier(PriceTier priceTier) throws ProductException {
        if (priceTier == null) {
            throw new ProductException("Price Tier cannot be null");
        }
        return priceTierRepository.save(priceTier);
    }

    public void managePriceTiersForProduct(Products product, List<PriceTier> incomingTiers) throws ProductException {
        List<PriceTier> existingTiers = priceTierRepository.findByProduct(product);

        Map<Long, PriceTier> existingTiersMap = existingTiers.stream()
                .filter(tier -> tier.getId() != null)
                .collect(Collectors.toMap(PriceTier::getId, tier -> tier));

        Map<Long, PriceTier> incomingTiersMap = new java.util.HashMap<>();
        if (incomingTiers != null) {
            incomingTiers.stream()
 .filter(tier -> tier.getId() != null)
                    .forEach(tier -> incomingTiersMap.put(tier.getId(), tier));
        }

        List<PriceTier> tiersToSave = new java.util.ArrayList<>();
        List<PriceTier> tiersToDelete = new java.util.ArrayList<>();

        // Process incoming price tiers
        if (incomingTiers != null) {
            for (PriceTier incomingTier : incomingTiers) {
                if (incomingTier.getId() != null && existingTiersMap.containsKey(incomingTier.getId())) {
                    // Existing price tier - update it
                    PriceTier existingTier = existingTiersMap.get(incomingTier.getId());
                    // Only update fields that are provided in the incoming tier
                    if (incomingTier.getMinQuantity() != null) existingTier.setMinQuantity(incomingTier.getMinQuantity());
                    if (incomingTier.getMaxQuantity() != null) existingTier.setMaxQuantity(incomingTier.getMaxQuantity());
                    if (incomingTier.getPricePerUnit() != null) existingTier.setPricePerUnit(incomingTier.getPricePerUnit());
                    if (incomingTier.getDiscountPercent() != null) existingTier.setDiscountPercent(incomingTier.getDiscountPercent());
                    if (incomingTier.getIsActive() != null) existingTier.setIsActive(incomingTier.getIsActive());
                    // Update variant association if provided
                    if (incomingTier.getVariant() != null) {
                        existingTier.setVariant(incomingTier.getVariant());
                    }
                    tiersToSave.add(existingTier);
                } else if (incomingTier.getId() == null) {
                    // New price tier - set product and variant, then add to save list
 if (incomingTier.getVariant() == null) {
 throw new ProductException("New price tier is missing a variant association.");
 }
                    incomingTier.setProduct(product);
                    tiersToSave.add(incomingTier);
                }
                // If incomingTier has an ID but is not in existingTiersMap, it's likely an error
                // or an attempt to associate a price tier from another product/variant.
                // You might want to add logging or error handling here.
            }
        }
    
        // Identify price tiers to delete (existing tiers not in the incoming list)
        for (PriceTier existingTier : existingTiers) {
            if (existingTier.getId() != null && !incomingTiersMap.containsKey(existingTier.getId())) {
                tiersToDelete.add(existingTier);
            }
        }

        // Perform deletions and saves
        priceTierRepository.deleteAll(tiersToDelete);
        priceTierRepository.saveAll(tiersToSave);
    }
    
    public void deletePriceTier(Long priceTierId) throws ProductException {
        if (!priceTierRepository.existsById(priceTierId)) {
            throw new ProductException("Price Tier not found with id: " + priceTierId);
        }
        priceTierRepository.deleteById(priceTierId);
    }
}