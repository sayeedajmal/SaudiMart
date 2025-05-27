package com.saudiMart.Product.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class PriceTierService {

    private final PriceTierRepository priceTierRepository;

    @Autowired
    public PriceTierService(PriceTierRepository priceTierRepository) {
        this.priceTierRepository = priceTierRepository;
    }

    public PriceTier findPriceTierById(Long id) throws ProductException {
        return priceTierRepository.findById(id)
                .orElseThrow(() -> new ProductException("Price tier not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public List<PriceTier> findPriceTiersByProductVariantId(Long productVariantId) {
        return priceTierRepository.findByProductVariant_VariantId(productVariantId);
    }

    public PriceTier savePriceTier(PriceTier priceTier) {
        return priceTierRepository.save(priceTier);
    }

    public PriceTier updatePriceTier(Long id, PriceTier updatedPriceTier) throws ProductException {
        PriceTier existingPriceTier = findPriceTierById(id); // Use the find method that throws exception

        existingPriceTier.setMinimumQuantity(updatedPriceTier.getMinimumQuantity());
        existingPriceTier.setPrice(updatedPriceTier.getPrice());
        existingPriceTier.setProductVariant(updatedPriceTier.getProductVariant());
        // Assuming other fields if any need to be updated

        return priceTierRepository.save(existingPriceTier);
    }

    public void deletePriceTier(Long id) throws ProductException {
        if (priceTierRepository.existsById(id)) {
            priceTierRepository.deleteById(id);
        } else {
            throw new ProductException("Price tier not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}