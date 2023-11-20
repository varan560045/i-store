package com.dmdev.i.store.entity;

public class AdministratorEntity {

    private Integer id;
    private Long purchaseId;
    private String actions;
    private Integer admissionId;

    public AdministratorEntity(Integer id, Long purchaseId, String actions, Integer admissionId) {
        this.id = id;
        this.purchaseId = purchaseId;
        this.actions = actions;
        this.admissionId = admissionId;
    }

    public AdministratorEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public Integer getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Integer admissionId) {
        this.admissionId = admissionId;
    }

    @Override
    public String toString() {
        return "AdministratorEntity{" +
               "id=" + id +
               ", purchaseId=" + purchaseId +
               ", actions='" + actions + '\'' +
               ", admissionId=" + admissionId +
               '}';
    }
}
