package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            // Assuming ProductSpecification has fields like name and value
            productSpecification.setName(productSpecificationDetails.getName());
            productSpecification.setValue(productSpecificationDetails.getValue());
            // Update other fields as necessary
            return productSpecificationRepository.save(productSpecification);
        }
        return null;
    }

    public void deleteProductSpecification(Long id) {
        productSpecificationRepository.deleteById(id);
    }
}