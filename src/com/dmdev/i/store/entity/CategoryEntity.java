package com.dmdev.i.store.entity;

public class CategoryEntity {

    private Integer id;
    private String categoryName;

    public CategoryEntity(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryEntity(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
               "id=" + id +
               ", clientId='" + categoryName + '\'' +
               '}';
    }
}
