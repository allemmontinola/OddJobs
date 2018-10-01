package com.example.allem.revised_capstone.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tags")
    @Expose
    private String tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
