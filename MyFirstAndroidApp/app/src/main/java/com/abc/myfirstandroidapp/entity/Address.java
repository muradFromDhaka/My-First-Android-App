package com.abc.myfirstandroidapp.entity;

public class Address {
    private Long id;
    private String name;
    private String city;
    private  String po;

    public Address() {}

    public Address(String name, String city, String po) {
        this.name = name;
        this.city = city;
        this.po = po;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPo() {
        return po;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPo(String po) {
        this.po = po;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", po='" + po + '\'' +
                '}';
    }
}
