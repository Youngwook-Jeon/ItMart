package com.itmart.admin.category;

import com.itmart.itmartcommon.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
