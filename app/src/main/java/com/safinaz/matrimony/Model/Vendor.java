package com.safinaz.matrimony.Model;

import java.io.Serializable;

public class Vendor implements Serializable {
    private String location;
    private Integer capacity;
    private String avgPrice;
    private String photoUrl;
    private Float rating;
    private String about;
    private String email;
    private String vendorName;
    private String phoneNumber;

    public Vendor(String location, Integer capacity, String avgPrice, String photoUrl, Float rating, String about, String email, String vendorName, String phoneNumber) {
        this.location = location;
        this.capacity = capacity;
        this.avgPrice = avgPrice;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.about = about;
        this.email = email;
        this.vendorName = vendorName;
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
