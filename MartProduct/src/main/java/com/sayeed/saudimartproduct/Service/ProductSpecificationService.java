package com.sayeed.saudimartproduct.Service;

import com.sayeed.saudimartproduct.Model.ProductSpecification;
import com.sayeed.saudimartproduct.Repository.ProductSpecificationRepository;
import com.sayeed.saudimartproduct.Utils.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ProductSpecification findProductSpecificationById(Long id) throws ProductException {
        return productSpecificationRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product specification not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public List<ProductSpecification> findProductSpecificationsByProductId(Long productId) {
        return productSpecificationRepository.findByProduct_ProductId(productId);
    }

    public ProductSpecification saveProductSpecification(ProductSpecification productSpecification) {
        return productSpecificationRepository.save(productSpecification);
    }

    public ProductSpecification updateProductSpecification(Long id, ProductSpecification updatedSpecification) throws ProductException {
        ProductSpecification existingSpecification = findProductSpecificationById(id);

        existingSpecification.setAttributeName(updatedSpecification.getAttributeName());
        existingSpecification.setAttributeValue(updatedSpecification.getAttributeValue());
        existingSpecification.setProduct(updatedSpecification.getProduct()); // Consider if product can be updated here

        return productSpecificationRepository.save(existingSpecification);
    }

    public void deleteProductSpecification(Long id) throws ProductException {
        if (productSpecificationRepository.existsById(id)) {
            productSpecificationRepository.deleteById(id);
        } else {
            throw new ProductException("Product specification not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}