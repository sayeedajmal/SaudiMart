package com.saudiMart.Product.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<ResponseWrapper<List<Category>>> getAllCategories() throws ProductException {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "All categories fetched successfully", categories));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Category>>> getAllActiveCategoriesByName(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean isActive) throws ProductException {
        List<Category> categories = categoryService.getAllActiveCategoriesByName(name, isActive);

        return ResponseEntity.ok(new ResponseWrapper<>(200, "Category fetched successfully", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> getCategoryById(@PathVariable Long id)
            throws ProductException {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Category fetched successfully", category));

    }

    @PostMapping("/parent")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<Category>> createParentCategory(@RequestBody Category category)
            throws ProductException {
        Category createdCategory = categoryService.createParentCategory(category);
        return ResponseEntity.ok(new ResponseWrapper<>(201, "Parent category created successfully", createdCategory));
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ResponseWrapper<Category>> createCategory(@RequestBody Category category)
            throws ProductException {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(new ResponseWrapper<>(201, "Child category created successfully", createdCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> updateCategory(@PathVariable Long id,
            @RequestBody Category category) throws ProductException {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Category updated successfully", updatedCategory));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteCategory(@PathVariable Long id) throws ProductException {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Category deleted successfully", null));
    }
}
