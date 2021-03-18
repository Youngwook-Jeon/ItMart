package com.itmart.admin.category;

import com.itmart.itmartcommon.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUsedInForm = new ArrayList<>();
        Iterable<Category> categories =  categoryRepository.findAll();
        for (Category category : categories) {
            if (category.getParent() == null) {
                categoriesUsedInForm.add(Category.copyIdAndName(category));
                Set<Category> children = category.getChildren();

                for (Category subCategory : children) {
                    String name = "--" + subCategory.getName();
                    categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
                    listChildren(categoriesUsedInForm, subCategory, 1);
                }
            }
        }
        return categoriesUsedInForm;
    }

    private void listChildren(List<Category> categoriesUsedInForm,
                              Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        for (Category subCategory : parent.getChildren()) {
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < newSubLevel; i++) {
                name.append("--");
            }
            name.append(subCategory.getName());
            categoriesUsedInForm.add(
                    Category.copyIdAndName(subCategory.getId(), name.toString()));
            listChildren(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }
}
