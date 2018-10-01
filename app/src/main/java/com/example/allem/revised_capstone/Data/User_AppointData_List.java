package com.example.allem.revised_capstone.Data;

import com.example.allem.revised_capstone.Adapters.User_AppointAdapter_List;
import com.example.allem.revised_capstone.Model.User_AppointModel_List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User_AppointData_List {
    @SerializedName("AppointList")
    @Expose
    private ArrayList<User_AppointModel_List> appointList = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public ArrayList<User_AppointModel_List> getAppointList() {
        return appointList;
    }

    public void setAppointList(ArrayList<User_AppointModel_List> appointList) {
        this.appointList = appointList;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
