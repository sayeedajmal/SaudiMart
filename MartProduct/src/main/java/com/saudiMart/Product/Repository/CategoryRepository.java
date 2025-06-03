package com.saudiMart.Product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saudiMart.Product.Model.Category;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryid(Long categoryId);

    Category findByName(String name);

    List<Category> findByIsActive(Boolean isActive);

    List<Category> findByParentCategory(Category parentCategory);

    List<Category> findByParentCategoryCategoryId(Long parentCategoryId);

    List<Category> findByCreatedAtBetween(Timestamp start, Timestamp end);
}