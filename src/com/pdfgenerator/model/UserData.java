package com.pdfgenerator.model;

import java.io.Serializable;

/**
 * Created by tomaszgadek on 16.12.2017.
 */
public class UserData implements Serializable {

    private String firstName;
    private String lastName;
    private String description;

    public UserData() {
    }

    public UserData(final String firstName,
                    final String lastName,
                    final String description) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
