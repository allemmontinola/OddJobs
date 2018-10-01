package com.example.allem.revised_capstone.Data;

import com.example.allem.revised_capstone.Model.ServiceAppointModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServiceAppointData {
    @SerializedName("AppointList")
    @Expose
    private ArrayList<ServiceAppointModel> appointList = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public ArrayList<ServiceAppointModel> getAppointList() {
        return appointList;
    }

    public void setAppointList(ArrayList<ServiceAppointModel> appointList) {
        this.appointList = appointList;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
