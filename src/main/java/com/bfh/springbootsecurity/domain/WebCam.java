package com.bfh.springbootsecurity.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WebCam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String location;
    private Boolean isVip;
    private String owner;

    public WebCam() {
    }

    public WebCam(String name, String country, String location, Boolean isVip, String owner) {
        this.name = name;
        this.country = country;
        this.location = location;

        this.isVip = isVip;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void updateWebCam(WebCam webCam) {
        this.setName(webCam.getName());
        this.setCountry(webCam.getCountry());
        this.setLocation(webCam.getLocation());
    }

    @Override
    public String toString() {
        return "WebCam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", location='" + location + '\'' +
                ", owner='" + owner + '\'' +
                ", VIP=" + isVip +
                '}';
    }

}
