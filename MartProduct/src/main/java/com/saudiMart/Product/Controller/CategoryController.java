package com.saudiMart.Product.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.saudiMart.Product.Utils.ProductException;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity
                .ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Categories retrieved successfully", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Optional<Category>>> getCategoryById(@PathVariable Long id)
            throws ProductException {
        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Category retrieved successfully", category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Category>> createCategory(@RequestBody Category category)
            throws ProductException {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(HttpStatus.CREATED.value(),
                    "Category created successfully", createdCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> updateCategory(@PathVariable Long id,
            @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Category updated successfully", updatedCategory));
        } catch (ProductException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity
                    .ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Category deleted successfully", null));
        } catch (ProductException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<ResponseWrapper<List<Category>>> getChildCategories(@PathVariable Long id)
            throws ProductException {
        try {
            List<Category> childCategories = categoryService.getChildCategories(id);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Child categories retrieved successfully", childCategories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @GetMapping("/{id}/parent")
    public ResponseEntity<ResponseWrapper<Optional<Category>>> getParentCategory(@PathVariable Long id) {
        try {
            Optional<Category> parentCategory = categoryService.getParentCategory(id);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(),
                    "Parent category retrieved successfully", parentCategory));
        } catch (ProductException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
        }
    }
}