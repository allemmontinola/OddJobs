package com.example.allem.revised_capstone.Data;



import java.util.ArrayList;

import com.example.allem.revised_capstone.Model.TagModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagData{

    @SerializedName("Tags")
    @Expose
    private ArrayList<TagModel> tags = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public ArrayList<TagModel> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagModel> tags) {
        this.tags = tags;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
