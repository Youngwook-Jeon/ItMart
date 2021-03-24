package com.itmart.admin.brand;

import com.itmart.itmartcommon.entity.Brand;
import com.itmart.itmartcommon.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCreate_Brand1() {
        Category laptops = new Category(4L);
        Brand acer = new Brand("Acer");
        acer.getCategories().add(laptops);

        Brand savedBrand = brandRepository.save(acer);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0L);
    }

    @Test
    public void testCreate_Brand2() {
        Category electronics = new Category(2L);
        Category iphone = new Category(9L);
        Brand apple = new Brand("Apple");
        apple.getCategories().add(electronics);
        apple.getCategories().add(iphone);

        Brand savedBrand = brandRepository.save(apple);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0L);
    }

}
