package com.dmdev.i.store.entity;

public class PurchaseStatusEntity {

    private Integer id;
    private String status;

    public PurchaseStatusEntity(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public PurchaseStatusEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PurchaseStatusEntity{" +
               "id=" + id +
               ", status='" + status + '\'' +
               '}';
    }
}
