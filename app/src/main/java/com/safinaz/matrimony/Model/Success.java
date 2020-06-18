package com.safinaz.matrimony.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Success {
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = -3039296905164750119L;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
