package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;
import com.saudiMart.Product.Model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSpecificationService {

    private final ProductSpecificationRepository productSpecificationRepository;

    @Autowired
    public ProductSpecificationService(ProductSpecificationRepository productSpecificationRepository) {
        this.productSpecificationRepository = productSpecificationRepository;
    }

    public List<ProductSpecification> getAllProductSpecifications() {
        return productSpecificationRepository.findAll();
    }

    public Optional<ProductSpecification> getProductSpecificationById(Long id) {
        return productSpecificationRepository.findById(id);
    }

    public Optional<ProductSpecification> findProductSpecificationBySpecId(Long specId) {
        return Optional.ofNullable(productSpecificationRepository.findBySpecId(specId));
    }

    public List<ProductSpecification> findProductSpecificationsByProduct(Products product) {
        return productSpecificationRepository.findByProduct(product);
    }

    public List<ProductSpecification> findProductSpecificationsByProductId(Long productId) {
        return productSpecificationRepository.findByProductId(productId);
    }

    public List<ProductSpecification> findProductSpecificationsBySpecName(String specName) {
        return productSpecificationRepository.findBySpecName(specName);
    }

    public List<ProductSpecification> findProductSpecificationsBySpecValue(String specValue) {
        return productSpecificationRepository.findBySpecValue(specValue);
    }

    public List<ProductSpecification> findProductSpecificationsByUnit(String unit) {
        return productSpecificationRepository.findByUnit(unit);
    }

    public List<ProductSpecification> findProductSpecificationsByDisplayOrder(Integer displayOrder) {
        return productSpecificationRepository.findByDisplayOrder(displayOrder);
    }

    public Optional<ProductSpecification> findProductSpecificationByProductIdAndSpecName(Long productId, String specName) {
        return Optional.ofNullable(productSpecificationRepository.findByProductIdAndSpecName(productId, specName));
    }

    public List<ProductSpecification> findProductSpecificationsByProductIdOrdered(Long productId) {
        return productSpecificationRepository.findByProductIdOrderByDisplayOrderAsc(productId);
    }

    public ProductSpecification createProductSpecification(ProductSpecification productSpecification) {
        return productSpecificationRepository.save(productSpecification);
    }

    public ProductSpecification updateProductSpecification(Long id, ProductSpecification productSpecificationDetails) {
        Optional<ProductSpecification> optionalProductSpecification = productSpecificationRepository.findById(id);
        if (optionalProductSpecification.isPresent()) {
            ProductSpecification productSpecification = optionalProductSpecification.get();
            productSpecification.setProduct(productSpecificationDetails.getProduct());
            productSpecification.setSpecName(productSpecificationDetails.getSpecName());
            productSpecification.setSpecValue(productSpecificationDetails.getSpecValue());
            productSpecification.setUnit(productSpecificationDetails.getUnit());
            productSpecification.setDisplayOrder(productSpecificationDetails.getDisplayOrder());
            return productSpecificationRepository.save(productSpecification);
        } else {
            // Handle not found case, e.g., throw an exception
            return null;
        }
    }

    public boolean deleteProductSpecification(Long id) {
        productSpecificationRepository.deleteById(id);
        return true;
    }
}