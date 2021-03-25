package com.itmart.admin.brand;

import com.itmart.itmartcommon.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    public Long countById(Long id);
}
