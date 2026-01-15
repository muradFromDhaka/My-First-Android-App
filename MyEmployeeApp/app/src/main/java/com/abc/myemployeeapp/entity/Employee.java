package com.abc.myemployeeapp.entity;
public class Employee {

    private long id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private double salary;
    private String active; // 1 = active, 0 = inactive
    private long joiningDate; // timestamp (milliseconds)
    private String department;
    private String skills; // comma separated skills
    private String imagePath;

    public Employee() {
    }

    // 🔹 Constructor without id (for insert)
    public Employee(
            String name, String email, String phone, int age, double salary,
            String active, long joiningDate, String department, String skills,
            String imagePath) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.salary = salary;
        this.active = active;
        this.joiningDate = joiningDate;
        this.department = department;
        this.skills = skills;
        this.imagePath = imagePath;
    }

    public Employee(String name, String email, String phone, int age,
                    double salary, String active, long joiningDate,
                    String department, String skills) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.salary = salary;
        this.active = active;
        this.joiningDate = joiningDate;
        this.department = department;
        this.skills = skills;
    }

    // 🔹 Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public long getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(long joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", active='" + active + '\'' +
                ", joiningDate=" + joiningDate +
                ", department='" + department + '\'' +
                ", skills='" + skills + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}

