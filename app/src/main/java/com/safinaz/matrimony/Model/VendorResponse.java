package com.safinaz.matrimony.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    private List<Vendor> success = null;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    private final static long serialVersionUID = 1539804499786411581L;

    public List<Vendor> getSuccess() {
        return success;
    }

    public void setSuccess(List<Vendor> success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}