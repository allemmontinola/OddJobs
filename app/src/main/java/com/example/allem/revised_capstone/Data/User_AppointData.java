package com.example.allem.revised_capstone.Data;
import java.util.ArrayList;
import java.util.List;

import com.example.allem.revised_capstone.Model.User_AppointModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class User_AppointData {
    @SerializedName("Appoint")
    @Expose
    private ArrayList<User_AppointModel> appoint = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public ArrayList<User_AppointModel> getAppoint() {
        return appoint;
    }

    public void setAppoint(ArrayList<User_AppointModel> appoint) {
        this.appoint = appoint;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
