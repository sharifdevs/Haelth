package com.example.haelth;

public class Person {

    // Fields
    private static String natID = "";
    private static String fName = "";
    private static String lName = "";
    private static App.Gender gender;
    private static People people[];
    private static String cellPhone = "";


    // Getters and Setters

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        Person.cellPhone = cellPhone;
    }

    public String getNatID() {
        return natID;
    }

    public void setNatID(String natID) { Person.natID = natID; }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        Person.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        Person.lName = lName;
    }

    public App.Gender getGender() {
        return gender;
    }

    public void setGender(App.Gender gender) {
        Person.gender = gender;
    }

    public People[] getPeople() {
        return people;
    }

    public void setPeople(People[] people) {
        Person.people = people;
    }
}