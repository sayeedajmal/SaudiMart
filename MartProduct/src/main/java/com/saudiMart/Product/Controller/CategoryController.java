package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        ResponseWrapper<List<Category>> response = new ResponseWrapper<>(true, "Categories fetched successfully", categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            ResponseWrapper<Category> response = new ResponseWrapper<>(true, "Category fetched successfully", category);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<Category> response = new ResponseWrapper<>(false, "Category not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Category>> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        ResponseWrapper<Category> response = new ResponseWrapper<>(true, "Category created successfully", createdCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            ResponseWrapper<Category> response = new ResponseWrapper<>(true, "Category updated successfully", updatedCategory);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<Category> response = new ResponseWrapper<>(false, "Category not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ResponseWrapper<Void> response = new ResponseWrapper<>(true, "Category deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}