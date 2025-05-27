package com.sayeed.saudimartproduct.Repository;

import com.sayeed.saudimartproduct.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentCategory_CategoryId(Long parentId);
    List<Category> findByParentCategoryIsNull();
}