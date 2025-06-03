package com.saudiMart.Product.Service;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Repository.CategoryRepository;
import com.saudiMart.Product.Utils.ProductException;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        // Add any business logic or validation before saving
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) throws ProductException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ProductException("Category not found with id: " + id));

        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setIsActive(categoryDetails.getIsActive());
        // Update other fields as needed

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) throws ProductException {
        if (!categoryRepository.existsById(id)) {
            throw new ProductException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public Optional<Category> getParentCategory(Long categoryId) throws ProductException {
        return categoryRepository.findById(categoryId).map(Category::getParentCategory);
    }

    public Optional<Category> findCategoryByName(String name) {
 return Optional.ofNullable(categoryRepository.findByName(name));
    }

    public List<Category> findCategoriesByIsActive(Boolean isActive) {
 return categoryRepository.findByIsActive(isActive);
    }

    public List<Category> findCategoriesByParentCategory(Category parentCategory) {
 return categoryRepository.findByParentCategory(parentCategory);
    }

    public List<Category> findCategoriesByParentCategoryId(Long parentCategoryId) {
 return categoryRepository.findByParentCategoryCategoryId(parentCategoryId);
    }

    public List<Category> findCategoriesByCreatedAtBetween(Timestamp start, Timestamp end) {
 return categoryRepository.findByCreatedAtBetween(start, end);
    }
}