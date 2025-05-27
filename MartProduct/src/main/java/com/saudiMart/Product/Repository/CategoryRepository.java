package com.saudiMart.Product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saudiMart.Product.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentCategory_CategoryId(Long parentId);
    List<Category> findByParentCategoryIsNull();
}