package com.safinaz.matrimony.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorCategoryResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    private List<VendorCategory> success = null;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    private final static long serialVersionUID = 1931046832881484610L;

    public List<VendorCategory> getSuccess() {
        return success;
    }

    public void setSuccess(List<VendorCategory> success) {
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
