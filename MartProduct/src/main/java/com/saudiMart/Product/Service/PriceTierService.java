package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Utils.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<PriceTier> getAllPriceTiers() {
        return priceTierRepository.findAll();
    }

    public Optional<PriceTier> getPriceTierById(Long id) {
        return priceTierRepository.findById(id);
    }

    public PriceTier createPriceTier(PriceTier priceTier) {
        return priceTierRepository.save(priceTier);
    }

    public PriceTier updatePriceTier(Long id, PriceTier priceTierDetails) throws ProductException {
        PriceTier priceTier = priceTierRepository.findById(id)
                .orElseThrow(() -> new ProductException("PriceTier not found with id: " + id));

        priceTier.setMinimumQuantity(priceTierDetails.getMinimumQuantity());
        priceTier.setPrice(priceTierDetails.getPrice());

        return priceTierRepository.save(priceTier);
    }

    public void deletePriceTier(Long id) throws ProductException {
        if (!priceTierRepository.existsById(id)) {
            throw new ProductException("PriceTier not found with id: " + id);
        }
        priceTierRepository.deleteById(id);
    }
}