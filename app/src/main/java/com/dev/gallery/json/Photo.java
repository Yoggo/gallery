package com.dev.gallery.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Photo {
    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("images")
    public List<Image> images;
}
