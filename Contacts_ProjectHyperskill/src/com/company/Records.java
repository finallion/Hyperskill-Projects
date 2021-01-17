package com.company;

import java.time.LocalDateTime;

public abstract class Records {
    private String name;
    private String phoneNumber;
    private LocalDateTime creationTime;
    private LocalDateTime editionTime;


    public Records(String name, String number, LocalDateTime creationTime, LocalDateTime editionTime) {
        this.name = name;
        this.phoneNumber = number;
        this.creationTime = creationTime;
        this.editionTime = editionTime;
    }

    abstract String info();

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getEditionTime() {
        return editionTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEditionTime(LocalDateTime editionTime) {
        this.editionTime = editionTime;
    }

}
