package com.saudiMart.Product.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.CategoryService;
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<Page<Category>>> getAllCategories(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Category> categories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "All categories fetched successfully", categories));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<Category>>> getAllActiveCategoriesByName(\
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean isActive,\
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Category> categories = categoryService.getAllActiveCategoriesByName(name, isActive, pageable);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Category fetched successfully", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> getCategoryById(@PathVariable String id)
            throws ProductException {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Category fetched successfully", category));

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Category>> createCategory(@RequestBody Category category)
            throws ProductException {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(new ResponseWrapper<>(201, " new Category created successfully", createdCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> updateCategory(@PathVariable String id,
            @RequestBody Category category) throws ProductException {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Category updated successfully", updatedCategory));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteCategory(@PathVariable String id) throws ProductException {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Category deleted successfully", null));
    }
}
