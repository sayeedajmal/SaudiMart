package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class PriceTierService {

    @Autowired
    private PriceTierRepository priceTierRepository;

    public List<PriceTier> getAllPriceTiers() {
        return priceTierRepository.findAll();
    }

    public PriceTier getPriceTierById(Long priceTierId) throws ProductException {
        return priceTierRepository.findById(priceTierId)
 .orElseThrow(() -> new ProductException("Price Tier not found with id: " + priceTierId));
    }

    public PriceTier createPriceTier(PriceTier priceTier) throws ProductException {
 if (priceTier == null) {
 throw new ProductException("Price Tier cannot be null");
 }
        return priceTierRepository.save(priceTier);
    }

    public PriceTier updatePriceTier(Long priceTierId, PriceTier priceTierDetails) throws ProductException {
 if (priceTierDetails == null) {
 throw new ProductException("Price Tier details cannot be null");
 }
        Optional<PriceTier> priceTierOptional = priceTierRepository.findById(priceTierId);
        if (priceTierOptional.isPresent()) {
            PriceTier priceTier = priceTierOptional.get();
            // Add null checks for individual fields if necessary
            priceTier.setMinimumQuantity(priceTierDetails.getMinimumQuantity());
            priceTier.setMaxQuantity(priceTierDetails.getMaxQuantity());
            priceTier.setPrice(priceTierDetails.getPrice());
            priceTier.setIsActive(priceTierDetails.getIsActive());
            return priceTierRepository.save(priceTier);
        }
 throw new ProductException("Price Tier not found with id: " + priceTierId);
    }

    public void deletePriceTier(Long priceTierId) throws ProductException {
 if (!priceTierRepository.existsById(priceTierId)) {
 throw new ProductException("Price Tier not found with id: " + priceTierId);
 }
        priceTierRepository.deleteById(priceTierId);
    }
}