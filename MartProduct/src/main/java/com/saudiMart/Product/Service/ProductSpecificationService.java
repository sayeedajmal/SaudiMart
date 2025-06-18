package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ProductSpecificationService {

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    public List<ProductSpecification> getAllProductSpecifications() {
        return productSpecificationRepository.findAll();
    }

    public ProductSpecification getProductSpecificationById(Long id) throws ProductException {
        return productSpecificationRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product specification not found with id: " + id));
    }

    public ProductSpecification createProductSpecification(ProductSpecification productSpecification) throws ProductException {
        if (productSpecification == null) {
            throw new ProductException("Product specification cannot be null");
        }
        return productSpecificationRepository.save(productSpecification);
    }

    public ProductSpecification updateProductSpecification(Long id, ProductSpecification productSpecificationDetails) throws ProductException {
        if (productSpecificationDetails == null) {
            throw new ProductException("Product specification details cannot be null");
        }
        Optional<ProductSpecification> productSpecificationOptional = productSpecificationRepository.findById(id);
        if (productSpecificationOptional.isPresent()) {
            ProductSpecification productSpecification = productSpecificationOptional.get();
            productSpecification.setSpecName(productSpecificationDetails.getSpecName());
            productSpecification.setSpecValue(productSpecificationDetails.getSpecValue());
            productSpecification.setUnit(productSpecificationDetails.getUnit());
            return productSpecificationRepository.save(productSpecification);
        }
        throw new ProductException("Product specification not found with id: " + id);
    }

    public void deleteProductSpecification(Long id) throws ProductException {
        if (!productSpecificationRepository.existsById(id)) {
            throw new ProductException("Product specification not found with id: " + id);
        }
        productSpecificationRepository.deleteById(id);
    }
}