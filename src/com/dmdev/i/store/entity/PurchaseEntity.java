package com.dmdev.i.store.entity;

public class PurchaseEntity {

    private Long id;
    private Integer storeId;
    private Long productId;
    private Integer productPrice;
    private Long clientId;
    private Integer purchaseStatusId;

    public PurchaseEntity(Long id, Integer storeId, Long productId, Integer productPrice, Long clientId, Integer purchaseStatusId) {
        this.id = id;
        this.storeId = storeId;
        this.productId = productId;
        this.productPrice = productPrice;
        this.clientId = clientId;
        this.purchaseStatusId = purchaseStatusId;
    }

    public PurchaseEntity(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Integer getPurchaseStatusId() {
        return purchaseStatusId;
    }

    public void setPurchaseStatusId(Integer purchaseStatusId) {
        this.purchaseStatusId = purchaseStatusId;
    }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
               "id=" + id +
               ", storeId=" + storeId +
               ", productId=" + productId +
               ", productPrice=" + productPrice +
               ", clientId=" + clientId +
               ", purchaseStatusId=" + purchaseStatusId +
               '}';
    }
}
