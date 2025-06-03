package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.PriceTier;
import com.saudiMart.Product.Repository.PriceTierRepository;
import com.saudiMart.Product.Model.Products;
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

    public Optional<PriceTier> findPriceTierByTierId(Long tierId) {
        return priceTierRepository.findByTierId(tierId);
    }

    public List<PriceTier> findPriceTiersByProduct(Products product) {
        return priceTierRepository.findByProduct(product);
    }

    public List<PriceTier> findPriceTiersByProductId(Long productId) {
        return priceTierRepository.findByProductId(productId);
    }

    public List<PriceTier> findPriceTiersByMinimumQuantity(Integer minimumQuantity) {
        return priceTierRepository.findByMinimumQuantity(minimumQuantity);
    }

    public List<PriceTier> findPriceTiersByPricePerUnit(BigDecimal pricePerUnit) {
        return priceTierRepository.findByPricePerUnit(pricePerUnit);
    }

    public List<PriceTier> findPriceTiersByMaxQuantity(Integer maxQuantity) {
        return priceTierRepository.findByMaxQuantity(maxQuantity);
    }

    public List<PriceTier> findPriceTiersByIsActive(Boolean isActive) {
        return priceTierRepository.findByIsActive(isActive);
    }

    public List<PriceTier> findPriceTiersByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return priceTierRepository.findByCreatedAtBetween(start, end);
    }

    public Optional<PriceTier> findPriceTierByProductAndQuantityRange(Products product, Integer quantity) {
        return priceTierRepository.findByProductAndMinimumQuantityLessThanEqualAndMaxQuantityGreaterThanEqual(product, quantity, quantity);
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

    public Optional<PriceTier> findPriceTierByProductIdAndQuantityRange(Long productId, Integer quantity) {
        return priceTierRepository.findByProductIdAndMinimumQuantityLessThanEqualAndMaxQuantityGreaterThanEqual(productId, quantity, quantity);
    }
}