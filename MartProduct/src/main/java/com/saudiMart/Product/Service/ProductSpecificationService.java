package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;

@Service
public class ProductSpecificationService {

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    public List<ProductSpecification> getAllProductSpecifications() {
        return productSpecificationRepository.findAll();
    }

    public Optional<ProductSpecification> getProductSpecificationById(Long id) {
        return productSpecificationRepository.findById(id);
    }

    public ProductSpecification createProductSpecification(ProductSpecification productSpecification) {
        return productSpecificationRepository.save(productSpecification);
    }

    public ProductSpecification updateProductSpecification(Long id, ProductSpecification productSpecificationDetails) {
        Optional<ProductSpecification> productSpecificationOptional = productSpecificationRepository.findById(id);
        if (productSpecificationOptional.isPresent()) {
            ProductSpecification productSpecification = productSpecificationOptional.get();
            productSpecification.setSpecName(productSpecificationDetails.getSpecName());
            productSpecification.setSpecValue(productSpecificationDetails.getSpecValue());
            productSpecification.setUnit(productSpecificationDetails.getUnit());
            productSpecification.setDisplayOrder(productSpecificationDetails.getDisplayOrder());
            return productSpecificationRepository.save(productSpecification);
        }
        return null;
    }

    public void deleteProductSpecification(Long id) {
        productSpecificationRepository.deleteById(id);
    }
}