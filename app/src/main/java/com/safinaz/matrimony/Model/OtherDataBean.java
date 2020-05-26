package com.safinaz.matrimony.Model;

import com.google.gson.annotations.SerializedName;


public class OtherDataBean {
    @SerializedName("id")
    public String id;
    @SerializedName("matri_id")
    public String matriId;
    @SerializedName("username")
    public String username;
    @SerializedName("photo1")
    public String photo1;

    public String getMatriId() {
        return matriId;
    }

    public void setMatriId(String matriId) {
        this.matriId = matriId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
