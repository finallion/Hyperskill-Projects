package com.company;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PersonRecord extends Records implements Serializable {
    private static final long serialVersionUID = 7L;
    private String surname;
    private String birthDate;
    private String gender;


    public PersonRecord(String name, String surname, String birthDate, String gender, String phoneNumber, LocalDateTime creationTime, LocalDateTime editionTime) {
        super(name, phoneNumber, creationTime, editionTime);
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        return getName() + " " + surname;
    }

    public String info() {
        return "Name: " + getName() +
                "\n" + "Surname: " + surname +
                "\n" + "Birth date: " + birthDate +
                "\n" + "Gender: " + gender +
                "\n" + "Number: " + getPhoneNumber() +
                "\n" + "Time created: " + getCreationTime() +
                "\n" + "Time last edit: " + getEditionTime();
    }

}