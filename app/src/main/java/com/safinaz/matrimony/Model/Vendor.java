package com.safinaz.matrimony.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vendor implements Serializable {
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("vendor_img")
    @Expose
    private String vendorImg;
    @SerializedName("vendor_start_rate_range")
    @Expose
    private String vendorStartRateRange;
    @SerializedName("vendor_end_rate_range")
    @Expose
    private String vendorEndRateRange;
    @SerializedName("vendor_country_name")
    @Expose
    private String vendorCountryName;
    @SerializedName("vendor_city_name")
    @Expose
    private String vendorCityName;
    @SerializedName("vendor_category_name")
    @Expose
    private String vendorCategoryName;
    @SerializedName("vendor_planner_name")
    @Expose
    private String vendorPlannerName;
    @SerializedName("vendor_capacity")
    @Expose
    private String vendorCapacity;
    @SerializedName("vendor_address")
    @Expose
    private String vendorAddress;
    @SerializedName("vendor_email")
    @Expose
    private String vendorEmail;
    @SerializedName("vendor_mobile")
    @Expose
    private String vendorMobile;
    private final static long serialVersionUID = -6133981158948396520L;

    public Vendor(){}

    public Vendor(String vendorId, String vendorImg, String vendorStartRateRange, String vendorEndRateRange, String vendorCountryName, String vendorCityName, String vendorCategoryName, String vendorPlannerName, String vendorCapacity, String vendorAddress, String vendorEmail, String vendorMobile) {
        this.vendorId = vendorId;
        this.vendorImg = vendorImg;
        this.vendorStartRateRange = vendorStartRateRange;
        this.vendorEndRateRange = vendorEndRateRange;
        this.vendorCountryName = vendorCountryName;
        this.vendorCityName = vendorCityName;
        this.vendorCategoryName = vendorCategoryName;
        this.vendorPlannerName = vendorPlannerName;
        this.vendorCapacity = vendorCapacity;
        this.vendorAddress = vendorAddress;
        this.vendorEmail = vendorEmail;
        this.vendorMobile = vendorMobile;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorImg() {
        return vendorImg;
    }

    public void setVendorImg(String vendorImg) {
        this.vendorImg = vendorImg;
    }

    public String getVendorStartRateRange() {
        return vendorStartRateRange;
    }

    public void setVendorStartRateRange(String vendorStartRateRange) {
        this.vendorStartRateRange = vendorStartRateRange;
    }

    public String getVendorEndRateRange() {
        return vendorEndRateRange;
    }

    public void setVendorEndRateRange(String vendorEndRateRange) {
        this.vendorEndRateRange = vendorEndRateRange;
    }

    public String getVendorCountryName() {
        return vendorCountryName;
    }

    public void setVendorCountryName(String vendorCountryName) {
        this.vendorCountryName = vendorCountryName;
    }

    public String getVendorCityName() {
        return vendorCityName;
    }

    public void setVendorCityName(String vendorCityName) {
        this.vendorCityName = vendorCityName;
    }

    public String getVendorCategoryName() {
        return vendorCategoryName;
    }

    public void setVendorCategoryName(String vendorCategoryName) {
        this.vendorCategoryName = vendorCategoryName;
    }

    public String getVendorPlannerName() {
        return vendorPlannerName;
    }

    public void setVendorPlannerName(String vendorPlannerName) {
        this.vendorPlannerName = vendorPlannerName;
    }

    public String getVendorCapacity() {
        return vendorCapacity;
    }

    public void setVendorCapacity(String vendorCapacity) {
        this.vendorCapacity = vendorCapacity;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorMobile() {
        return vendorMobile;
    }

    public void setVendorMobile(String vendorMobile) {
        this.vendorMobile = vendorMobile;
    }
}
