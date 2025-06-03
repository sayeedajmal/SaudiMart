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

    public Category getCategoryById(Long categoryId) throws ProductException {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ProductException("Product not found"));
    }

    public List<Category> getCategoriesByParent(Category parentCategory) throws ProductException {
 if (parentCategory == null) {
 throw new ProductException("Parent category cannot be null");
 }
        return categoryRepository.findByParentCategory(parentCategory);
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByIsActive(true);
    }

    public Category createCategory(Category category) throws ProductException {
 if (category == null) {
 throw new ProductException("Category details cannot be null");
 }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) throws ProductException {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
 if (categoryDetails.getName() != null)
            category.setName(categoryDetails.getName());
 if (categoryDetails.getDescription() != null)
            category.setDescription(categoryDetails.getDescription());
            category.setIsActive(categoryDetails.getIsActive());
            return categoryRepository.save(category);
        }
        return null;
    }

    public void deleteCategory(Long categoryId) {
 Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
 if (categoryOptional.isPresent()) {
 categoryRepository.deleteById(categoryId);
 }
    }
}
