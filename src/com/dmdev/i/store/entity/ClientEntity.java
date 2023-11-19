package com.dmdev.i.store.entity;

public class ClientEntity {

    private Long id;
    private String fName;
    private String lName;
    private String email;
    private Integer admissionId;

    public ClientEntity(Long id, String fName, String lName, String email, Integer admissionId) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.admissionId = admissionId;
    }

    public ClientEntity(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Integer admissionId) {
        this.admissionId = admissionId;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
               "id=" + id +
               ", fName='" + fName + '\'' +
               ", lName='" + lName + '\'' +
               ", email='" + email + '\'' +
               ", admissionId=" + admissionId +
               '}';
    }
}
