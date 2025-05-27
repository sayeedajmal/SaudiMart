package com.saudiMart.Product.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyRole('BUYER', 'SELLER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findCategoryById(id);
        return category.map(cat -> ResponseWrapper.success(cat, HttpStatus.OK))
                .orElseGet(() -> ResponseWrapper.error("Category not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('BUYER', 'SELLER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseWrapper.success(categories, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('BUYER', 'SELLER', 'ADMIN')")
    @GetMapping("/children/{parentId}")
    public ResponseEntity<ResponseWrapper<List<Category>>> getChildCategories(@PathVariable Long parentId) {
        // Assuming findChildCategoriesByParentId might return empty list if parent not
        // found or no children
        // You might want to add a check if the parent category actually exists.
        List<Category> childCategories = categoryService.findChildCategoriesByParentId(parentId); // Assuming this
                                                                                                  // handles
                                                                                                  // non-existent parent
                                                                                                  // by returning empty
                                                                                                  // list
        return ResponseWrapper.success(childCategories, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('BUYER', 'SELLER', 'ADMIN')")
    @GetMapping("/root")
    public ResponseEntity<ResponseWrapper<List<Category>>> getRootCategories() {
        List<Category> rootCategories = categoryService.findRootCategories();
        return ResponseWrapper.success(rootCategories, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseWrapper<Category>> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.saveCategory(category);
        return ResponseWrapper.success(createdCategory, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> updateCategory(@PathVariable Long id,
            @RequestBody Category categoryDetails) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
            return ResponseWrapper.success(updatedCategory, HttpStatus.OK);
        } catch (RuntimeException e) { // Assuming a generic RuntimeException for simplicity, can be more specific
            // In a real application, you'd likely have a more specific exception for "not
            // found"
            return ResponseWrapper.error("Category not found with id: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other potential exceptions during update
            return ResponseWrapper.error("Error updating category: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.categoryExists(id)) {
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No content response for successful deletion
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not found if category doesn't exist
        }
    }
}