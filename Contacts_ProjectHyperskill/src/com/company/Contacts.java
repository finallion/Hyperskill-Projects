package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contacts implements Serializable {
    private static final long serialVersionUID = 7L;
    private List<Records> contacts = new ArrayList<>();

    public void addRecord(Records record) {
        contacts.add(record);
    }

    public void removeRecord(int index) {
        contacts.remove(index);
    }

    public int getIndexOfRecords(Records record) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).equals(record)) {
                return i;
            }
        }
        return 0;
    }

    public PersonRecord getPersonRecord(int index) {
        if (contacts.get(index) instanceof PersonRecord) {
            return (PersonRecord) contacts.get(index);
        }
        return null;
    }

    public OrganizationRecord getOrganizationRecord(int index) {
        if (contacts.get(index) instanceof OrganizationRecord) {
            return (OrganizationRecord) contacts.get(index);
        }
        return null;
    }

    public Records getRecords(int index) {
        return contacts.get(index);
    }

    public int getSize() {
        return contacts.size();
    }

    public String printAllContactInfo(int index) {
        return contacts.get(index).info();
    }

    public void printContacts() {
        for (Records records : contacts) {
            System.out.println((getIndexOfRecords(records) + 1) + ". " + records);
        }
    }

}
