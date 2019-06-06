package com.java_app.entity;

import java.util.List;

public class Employee {

    private long id;

    private String last_name;

    private String first_name;

    private String age;

    private String email;

    private int wage;

    private Profession profession;

    private NumberOfCabinet numberOfCabinet;

    private List<Projects> projectsList;

    public List<Projects> getProjectsList() {
        return projectsList;
    }

    public void setProjectsList(List<Projects> projectsList) {
        this.projectsList = projectsList;
    }

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public NumberOfCabinet getNumberOfCabinet() {
        return numberOfCabinet;
    }

    public void setNumberOfCabinet(NumberOfCabinet numberOfCabinet) {
        this.numberOfCabinet = numberOfCabinet;
    }
}
