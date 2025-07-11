package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.ProductSpecification;
import com.saudiMart.Product.Model.Products;
import com.saudiMart.Product.Repository.ProductSpecificationRepository;
import com.saudiMart.Product.Repository.ProductsRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class ProductSpecificationService {

    @Autowired
    private ProductSpecificationRepository productSpecificationRepository;

    @Autowired
    private ProductsRepository productsRepository;

    public Page<ProductSpecification> getAllProductSpecifications(Pageable pageable) {
        return productSpecificationRepository.findAll(pageable);
    }

    public ProductSpecification getProductSpecificationById(String id) throws ProductException {
        return productSpecificationRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product specification not found with id: " + id));
    }

    public List<ProductSpecification> getProductSpecificationsByProductId(String productId)
            throws ProductException {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        return productSpecificationRepository.findByProduct(product);
    }

    public ProductSpecification createProductSpecification(ProductSpecification productSpecification)
            throws ProductException {
        if (productSpecification == null) {
            throw new ProductException("Product specification cannot be null");
        }
        return productSpecificationRepository.save(productSpecification);
    }

    public ProductSpecification updateProductSpecification(String id, ProductSpecification productSpecificationDetails)
            throws ProductException {
        if (productSpecificationDetails == null) {
            throw new ProductException("Product specification details cannot be null");
        }
        Optional<ProductSpecification> productSpecificationOptional = productSpecificationRepository.findById(id);
        if (productSpecificationOptional.isPresent()) {
            ProductSpecification productSpecification = productSpecificationOptional.get();
            if (productSpecificationDetails.getSpecName() != null)
                productSpecification.setSpecName(productSpecificationDetails.getSpecName());
            if (productSpecificationDetails.getSpecValue() != null)
                productSpecification.setSpecValue(productSpecificationDetails.getSpecValue());
            if (productSpecificationDetails.getUnit() != null)
                productSpecification.setUnit(productSpecificationDetails.getUnit());
            if (productSpecificationDetails.getDisplayOrder() != null)
                productSpecification.setDisplayOrder(productSpecificationDetails.getDisplayOrder());
            return productSpecificationRepository.save(productSpecification);
        }
        throw new ProductException("Product specification not found with id: " + id);
    }

    public void deleteProductSpecification(String id) throws ProductException {
        if (!productSpecificationRepository.existsById(id)) {
            throw new ProductException("Product specification not found with id: " + id);
        }
        productSpecificationRepository.deleteById(id);
    }

    public Page<ProductSpecification> searchProductSpecifications(String productId, String specName, Pageable pageable)
            throws ProductException {
        Products product = null;
        if (productId != null) {
            product = productsRepository.findById(productId)
                    .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
        }
        return productSpecificationRepository.findByProductAndSpecNameContainingIgnoreCase(product, specName, pageable);
    }
}