package com.itmart.admin.brand;

import com.itmart.admin.category.CategoryService;
import com.itmart.itmartcommon.entity.Brand;
import com.itmart.itmartcommon.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BrandController {

    private final BrandService brandService;

    private final CategoryService categoryService;

    @Autowired
    public BrandController(BrandService brandService, CategoryService categoryService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("/brands")
    public String listAll(Model model) {
        List<Brand> brandList = brandService.listAll();
        model.addAttribute("brandList", brandList);
        return "brands/brands";
    }

    @GetMapping("/brands/new")
    public String newBrand(Model model) {
        List<Category> categoryList = categoryService.listCategoriesUsedInForm();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("brand", new Brand());
        model.addAttribute("pageTitle", "Create New Brand");
        return "brands/brand_form";
    }
}
