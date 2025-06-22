package com.saudiMart.Product.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Repository.CategoryRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getAllActiveCategoriesByName(String name, Boolean isActive) {
        if (name != null && isActive != null) {
            return categoryRepository.findByNameContainingIgnoreCaseAndIsActive(name, isActive);
        } else if (name != null) {
            return categoryRepository.findByNameContainingIgnoreCase(name);
        } else if (isActive != null) {
            return categoryRepository.findByIsActive(isActive);
        } else {
            return categoryRepository.findAll();
        }
    }

    public Category getCategoryById(Long categoryId) throws ProductException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductException("Category not found with id: " + categoryId));
    }

    public Category getCategoryByName(String name) throws ProductException {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ProductException("Category not found with name: " + name));
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByIsActive(true);
    }

    public Category createCategory(Category category) throws ProductException {
        if (category == null) {
            throw new ProductException("Category details cannot be null.");
        }
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new ProductException("Category with name " + category.getName() + " already exists.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) throws ProductException {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryDetails == null) {
            throw new ProductException("Category details cannot be null for update.");
        }
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            if (categoryDetails.getImageUrl() != null) {
                category.setImageUrl(categoryDetails.getImageUrl());
            }
            if (categoryDetails.getName() != null)
                category.setName(categoryDetails.getName());
            if (categoryDetails.getDescription() != null)
                category.setDescription(categoryDetails.getDescription());
            if (categoryDetails.getIsActive() != null)
                category.setIsActive(categoryDetails.getIsActive());
            return categoryRepository.save(category);
        }
        throw new ProductException("Category not found with id: " + categoryId);
    }

    public void deleteCategory(Long categoryId) throws ProductException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ProductException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
