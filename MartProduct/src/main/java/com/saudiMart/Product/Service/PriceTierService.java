package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Repository.PriceTierRepository;

@Service
public class PriceTierService {

    @Autowired
    private PriceTierRepository priceTierRepository;

    public List<PriceTier> getAllPriceTiers() {
        return priceTierRepository.findAll();
    }

    public Optional<PriceTier> getPriceTierById(Long priceTierId) {
        return priceTierRepository.findById(priceTierId);
    }

    public PriceTier createPriceTier(PriceTier priceTier) {
        return priceTierRepository.save(priceTier);
    }

    public PriceTier updatePriceTier(Long priceTierId, PriceTier priceTierDetails) {
        Optional<PriceTier> priceTierOptional = priceTierRepository.findById(priceTierId);
        if (priceTierOptional.isPresent()) {
            PriceTier priceTier = priceTierOptional.get();
            priceTier.setMinimumQuantity(priceTierDetails.getMinimumQuantity());
            priceTier.setMaxQuantity(priceTierDetails.getMaxQuantity());
            priceTier.setPrice(priceTierDetails.getPrice());
            priceTier.setIsActive(priceTierDetails.getIsActive());
            return priceTierRepository.save(priceTier);
        }
        return null; // Or throw an exception
    }

    public void deletePriceTier(Long priceTierId) {
        priceTierRepository.deleteById(priceTierId);
    }
}