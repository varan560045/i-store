package com.dmdev.i.store.entity;

public class ManufacturerEntity {

    private Integer id;
    private String manufacturerName;

    public ManufacturerEntity(Integer id, String manufacturerName) {
        this.id = id;
        this.manufacturerName = manufacturerName;
    }

    public ManufacturerEntity(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public String toString() {
        return "ManufacturerEntity{" +
               "id=" + id +
               ", manufacturerName='" + manufacturerName + '\'' +
               '}';
    }
}
