package com.example.allem.revised_capstone.Data;

import com.example.allem.revised_capstone.Model.ServiceHomeModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServiceHomeData {
    @SerializedName("Home")
    @Expose
    private ArrayList<ServiceHomeModel> home = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public ArrayList<ServiceHomeModel> getHome() {
        return home;
    }

    public void setHome(ArrayList<ServiceHomeModel> home) {
        this.home = home;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
