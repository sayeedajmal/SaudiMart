package com.saudimart.martProduct.Repository;

import com.saudimart.martProduct.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentCategory_CategoryId(Long parentId);
    List<Category> findByParentCategoryIsNull();
}