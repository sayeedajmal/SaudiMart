package com.sayeed.saudimartproduct.Service;

import com.sayeed.saudimartproduct.Model.PriceTier;
import com.sayeed.saudimartproduct.Repository.PriceTierRepository;
import com.sayeed.saudimartproduct.Utils.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        existingPriceTier.setQuantity(updatedPriceTier.getQuantity());
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