package com.saudiMart.Product.Controller;

import com.saudiMart.Product.Model.Category;
import com.saudiMart.Product.Model.ResponseWrapper;
import com.saudiMart.Product.Service.CategoryService;
import com.saudiMart.Product.Utils.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Category>>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Categories retrieved successfully", categories));
        } catch (ProductException e) {
            return ResponseEntity.status(e.getStatus()).body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Category retrieved successfully", category));
        } catch (ProductException e) {
            return ResponseEntity.status(e.getStatus()).body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Category>> createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(HttpStatus.CREATED.value(), "Category created successfully", createdCategory));
        } catch (ProductException e) {
            return ResponseEntity.status(e.getStatus()).body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Category>> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Category updated successfully", updatedCategory));
        } catch (ProductException e) {
            return ResponseEntity.status(e.getStatus()).body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Category deleted successfully", null));
        } catch (ProductException e) {
            return ResponseEntity.status(e.getStatus()).body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
        }
    }

    @GetMapping("/{id}/children")
 public ResponseEntity<ResponseWrapper<List<Category>>> getChildCategories(@PathVariable Long id) {
 try {
 List<Category> childCategories = categoryService.getChildCategories(id);
 return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Child categories retrieved successfully", childCategories));
 } catch (ProductException e) {
 return ResponseEntity.status(e.getStatus()).body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
 } catch (Exception e) {
 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null));
 }
    }

    @GetMapping("/{id}/parent")
 public ResponseEntity<ResponseWrapper<Category>> getParentCategory(@PathVariable Long id) {
 try {
 Category parentCategory = categoryService.getParentCategory(id);
 return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Parent category retrieved successfully", parentCategory));
 } catch (ProductException e) {
 return ResponseEntity.status(e.getStatus()).body(new ResponseWrapper<>(e.getStatus().value(), e.getMessage(), null));
 }
    }
}