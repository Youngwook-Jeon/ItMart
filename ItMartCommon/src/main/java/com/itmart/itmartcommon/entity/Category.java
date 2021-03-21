package com.itmart.itmartcommon.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 64, nullable = false, unique = true)
    private String alias;

    @Column(length = 128, nullable = false)
    private String image;

    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    public Category() {
    }

    public Category(Long id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
        this.alias = name;
        this.image = "default.png";
    }

    public Category(String name, Category parent) {
        this(name);
        this.parent = parent;
    }

    public Category(Long id, String name, String alias) {
        this();
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public static Category copyIdAndName(Category category) {
        Category copied = new Category();
        copied.setId(category.getId());
        copied.setName(category.getName());
        return copied;
    }

    public static Category copyIdAndName(Long id, String name) {
        Category copied = new Category();
        copied.setId(id);
        copied.setName(name);
        return copied;
    }

    public static Category copyFull(Category category) {
        Category copied = new Category();
        copied.setId(category.getId());
        copied.setName(category.getName());
        copied.setImage(category.getImage());
        copied.setAlias(category.getAlias());
        copied.setEnabled(category.isEnabled());

        return copied;
    }

    public static Category copyFull(Category category, String name) {
        Category copied = Category.copyFull(category);
        copied.setName(name);
        return copied;
    }

    @Transient
    public String getImagePath() {
        if (this.id == null) return "/image/image-thumbnail.png";
        return "/category-images/" + this.id + "/" + this.image;
    }
}
