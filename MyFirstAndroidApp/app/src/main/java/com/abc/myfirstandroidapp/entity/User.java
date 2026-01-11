package com.abc.myfirstandroidapp.entity;

public class User {

    private long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String dob;
    private String country;
    private String skills;      // stored as comma-separated
    private boolean termsAccepted;

    public User() { }


    public User(String name, String email, String password,
                String phone, String gender, String dob,
                String country, String skills, boolean termsAccepted) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.country = country;
        this.skills = skills;
        this.termsAccepted = termsAccepted;
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getCountry() {
        return country;
    }

    public String getSkills() {
        return skills;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", country='" + country + '\'' +
                ", skills='" + skills + '\'' +
                ", termsAccepted=" + termsAccepted +
                '}';
    }
}
