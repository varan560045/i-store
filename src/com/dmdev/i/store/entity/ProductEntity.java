package com.dmdev.i.store.entity;

public class ProductEntity {

    private Long id;
    private String productName;
    private Integer price;
    private Integer categoryId;
    private Integer manufacturerId;
    private Integer quantity;

    public ProductEntity(Long id, String productName, Integer price, Integer categoryId, Integer manufacturerId, Integer quantity) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
               "id=" + id +
               ", productName='" + productName + '\'' +
               ", price=" + price +
               ", categoryId=" + categoryId +
               ", manufacturerId=" + manufacturerId +
               ", quantity=" + quantity +
               '}';
    }
}
