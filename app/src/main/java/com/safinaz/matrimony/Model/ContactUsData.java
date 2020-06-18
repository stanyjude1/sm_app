package com.safinaz.matrimony.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUsData {
    @SerializedName("vendor_id")
    @Expose
    String id;
    @SerializedName("vendor_mail")
    @Expose
    String vendorMail;
    @SerializedName("customer_name")
    @Expose
    String customerName;
    @SerializedName("customer_mail")
    @Expose
    String customerMail;
    @SerializedName("customer_number")
    @Expose
    String customerNumber;
    @SerializedName("customer_message")
    String customerMessage;

    public ContactUsData(String id, String vendorMail, String customerName, String customerMail, String customerNumber, String customerMessage) {
        this.id = id;
        this.vendorMail = vendorMail;
        this.customerName = customerName;
        this.customerMail = customerMail;
        this.customerNumber = customerNumber;
        this.customerMessage = customerMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorMail() {
        return vendorMail;
    }

    public void setVendorMail(String vendorMail) {
        this.vendorMail = vendorMail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }
}
