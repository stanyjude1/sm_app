package com.safinaz.matrimony.Model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorCategory implements Serializable
{

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_img")
    @Expose
    private String categoryImg;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    private final static long serialVersionUID = 9143211817175278121L;

    public VendorCategory(){}

    public VendorCategory(String categoryId, String categoryImg, String categoryName) {
        this.categoryId = categoryId;
        this.categoryImg = categoryImg;
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
