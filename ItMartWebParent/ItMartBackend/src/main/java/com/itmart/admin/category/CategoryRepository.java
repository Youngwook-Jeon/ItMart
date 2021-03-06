package com.itmart.admin.category;

import com.itmart.itmartcommon.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    List<Category> listRootCategories(Sort sort);

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    Page<Category> listRootCategories(Pageable pageable);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword%")
    Page<Category> search(String keyword, Pageable pageable);

    Category findByName(String name);

    Category findByAlias(String alias);

    Long countById(Long id);

    @Query("UPDATE Category c SET c.enabled = :enabled WHERE c.id = :id")
    @Modifying
    void updateEnabledStatus(Long id, boolean enabled);
}
