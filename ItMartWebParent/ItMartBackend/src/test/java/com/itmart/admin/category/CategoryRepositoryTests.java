package com.itmart.admin.category;

import com.itmart.itmartcommon.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void testCreate_RootCategory() {
        Category category = new Category("Electronics");
        Category savedCategory = repository.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0L);
    }

    @Test
    public void testCreate_SubCategory() {
        Category parent = new Category(7L);
        Category subCategory = new Category("iPhone", parent);
        Category savedCategory = repository.save(subCategory);

        assertThat(savedCategory.getId()).isGreaterThan(0L);
    }

    @Test
    public void testGet_Category() {
        Category category = repository.findById(2L).get();
        System.out.println(category.getName());
        Set<Category> children = category.getChildren();

        for (Category subCategory : children) {
            System.out.println(subCategory.getName());
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrint_Hierarchical_Categories() {
        Iterable<Category> categories = repository.findAll();
        for (Category category : categories) {
            if (category.getParent() == null) {
                System.out.println(category.getName());
                Set<Category> children = category.getChildren();

                for (Category subCategory : children) {
                    System.out.println("--" + subCategory.getName());
                    printChildren(subCategory, 1);
                }
            }
        }
    }

    private void printChildren(Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        for (Category subCategory : parent.getChildren()) {
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }
            System.out.println(subCategory.getName());
            printChildren(subCategory, newSubLevel);
        }
    }

    @Test
    public void testList_Root_Categories() {
        List<Category> rootCategories = repository.listRootCategories(Sort.by("name").ascending());
        rootCategories.forEach(category -> System.out.println(category.getName()));
    }

    @Test
    public void testFind_By_Name() {
        String name = "Electronics";
        Category category = repository.findByName(name);

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    public void testFind_By_Alias() {
        String alias = "Electronics";
        Category category = repository.findByAlias(alias);

        assertThat(category).isNotNull();
        assertThat(category.getAlias()).isEqualTo(alias);
    }
}
