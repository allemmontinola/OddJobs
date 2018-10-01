package com.example.allem.revised_capstone.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountAppointModel {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("count_appoint")
    @Expose
    private String countAppoint;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getCountAppoint() {
        return countAppoint;
    }

    public void setCountAppoint(String countAppoint) {
        this.countAppoint = countAppoint;
    }
}
