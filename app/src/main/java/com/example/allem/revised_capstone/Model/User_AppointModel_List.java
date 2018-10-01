package com.example.allem.revised_capstone.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_AppointModel_List {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("appointedTo")
    @Expose
    private String appointedTo;
    @SerializedName("appointedFrom")
    @Expose
    private String appointedFrom;
    @SerializedName("appointedFullName")
    @Expose
    private String appointedFullName;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppointedTo() {
        return appointedTo;
    }

    public void setAppointedTo(String appointedTo) {
        this.appointedTo = appointedTo;
    }

    public String getAppointedFrom() {
        return appointedFrom;
    }

    public void setAppointedFrom(String appointedFrom) {
        this.appointedFrom = appointedFrom;
    }

    public String getAppointedFullName() {
        return appointedFullName;
    }

    public void setAppointedFullName(String appointedFullName) {
        this.appointedFullName = appointedFullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
