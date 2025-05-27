package com.sayeed.saudimartproduct.Service;

import com.sayeed.saudimartproduct.Model.Product;
import com.sayeed.saudimartproduct.Repository.ProductRepository;
import com.sayeed.saudimartproduct.Utils.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(Long id) throws ProductException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) throws ProductException {
        Product existingProduct = findProductById(id); // Use the find method that throws exception

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setBasePrice(updatedProduct.getBasePrice());
        existingProduct.setIsBulkOnly(updatedProduct.getIsBulkOnly());
        existingProduct.setMinimumOrderQuantity(updatedProduct.getMinimumOrderQuantity());
        existingProduct.setWeight(updatedProduct.getWeight());
        existingProduct.setWeightUnit(updatedProduct.getWeightUnit());
        existingProduct.setDimensions(updatedProduct.getDimensions());
        existingProduct.setSku(updatedProduct.getSku());
        existingProduct.setAvailable(updatedProduct.getAvailable());
        // createdAt and updatedAt are handled by @CreationTimestamp and @UpdateTimestamp

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) throws ProductException {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ProductException("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
