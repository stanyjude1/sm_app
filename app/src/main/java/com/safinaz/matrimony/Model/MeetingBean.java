package com.safinaz.matrimony.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MeetingBean {
    @SerializedName("date")
    public String date;
    @SerializedName("time")
    public String time;
    @SerializedName("place")
    public String place;
    @SerializedName("other_id")
    public String otherId;
    @SerializedName("other_data")
    public OtherDataBean otherData;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public OtherDataBean getOtherData() {
        return otherData;
    }

    public void setOtherData(OtherDataBean otherData) {
        this.otherData = otherData;
    }
}