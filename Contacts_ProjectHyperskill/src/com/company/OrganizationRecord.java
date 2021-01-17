package com.company;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrganizationRecord extends Records implements Serializable {
    private static final long serialVersionUID = 7L;
    private String address;

    public OrganizationRecord(String name, String address, String number, LocalDateTime creationTime, LocalDateTime editionTime) {
        super(name, number, creationTime, editionTime);
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return getName();
    }

    public String info() {
        return "Organization name: " + getName() +
                "\n" + "Address: " + address +
                "\n" + "Number: " + getPhoneNumber() +
                "\n" + "Time created: " + getCreationTime() +
                "\n" + "Time last edit: " + getEditionTime();
    }
}
