package com.dmdev.i.store.entity;

public class BlackListEntity {

    private Long id;
    private Long clientId;
    private String state;

    public BlackListEntity(Long id, Long clientId, String state) {
        this.id = id;
        this.clientId = clientId;
        this.state = state;
    }

    public BlackListEntity(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BlackListEntity{" +
               "id=" + id +
               ", clientId=" + clientId +
               ", state='" + state + '\'' +
               '}';
    }
}
