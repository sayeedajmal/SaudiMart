package com.sayeed.saudimartproduct.Service;

import com.sayeed.saudimartproduct.Repository.CategoryRepository;
import com.sayeed.saudimartproduct.Utils.ProductException;
import com.sayeed.saudimartproduct.model.Category;

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

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findChildCategoriesByParentId(Long parentId) {
        return categoryRepository.findByParentCategory_CategoryId(parentId);
    }

    public List<Category> findRootCategories() {
        return categoryRepository.findByParentCategoryIsNull();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(id);
        if (existingCategoryOptional.isPresent()) {
            Category existingCategory = existingCategoryOptional.get();
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setDescription(updatedCategory.getDescription());
            existingCategory.setIsActive(updatedCategory.getIsActive());
            if (updatedCategory.getParentCategory() != null && !updatedCategory.getParentCategory().getCategoryId().equals(id)) {
                existingCategory.setParentCategory(updatedCategory.getParentCategory());
            } else if (updatedCategory.getParentCategory() == null) {
                existingCategory.setParentCategory(null);
            }
            return categoryRepository.save(existingCategory);
        } else {
            throw new ProductException("Category not found with id: " + id);
        }
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new ProductException("Category not found with id: " + id);
        }
    }

    public boolean categoryExistsById(Long id) {
        return categoryRepository.existsById(id);
    }
}