package com.example.haelth;

public class Patient extends Person {

    // Patient Info
    private static String email = "";
    private static String birthDate = "";
    private static String password = "";
    private static String profileImageUrl = "";
    private static String country = "";
    private static String city = "";
    private static String address = "";
    private static String homePhone = "";
    private static String location;
    private static String Problems[];
    private static String History[];
    private static String Medicine[];
    private static String comments;

    // Setter and Getters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Patient.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        Patient.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Patient.password = password;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        Patient.profileImageUrl = profileImageUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        Patient.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        Patient.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        Patient.address = address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        Patient.homePhone = homePhone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        Patient.location = location;
    }

    public String[] getProblems() {
        return Problems;
    }

    public void setProblems(String[] problems) {
        Problems = problems;
    }

    public String[] getHistory() {
        return History;
    }

    public void setHistory(String[] history) {
        History = history;
    }

    public String[] getMedicine() {
        return Medicine;
    }

    public void setMedicine(String[] medicine) {
        Medicine = medicine;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        Patient.comments = comments;
    }

}