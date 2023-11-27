package com.dmdev.i.store.entity;

public class StoreEntity {

    private Integer id;
    private String storeName;

    public StoreEntity(Integer id, String storeName) {
        this.id = id;
        this.storeName = storeName;
    }

    public StoreEntity(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return "StoreEntity{" +
               "id=" + id +
               ", storeName='" + storeName + '\'' +
               '}';
    }
}
