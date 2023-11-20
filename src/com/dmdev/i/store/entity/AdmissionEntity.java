package com.dmdev.i.store.entity;

public class AdmissionEntity {

    private Integer id;
    private String status;
    private String permissions;

    public AdmissionEntity(Integer id, String status, String permissions) {
        this.id = id;
        this.status = status;
        this.permissions = permissions;
    }

    public AdmissionEntity(){
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

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "AdmissionEntity{" +
               "id=" + id +
               ", status='" + status + '\'' +
               ", permissions='" + permissions + '\'' +
               '}';
    }
}
