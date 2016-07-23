package com.dev.gallery.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Page {

    @SerializedName("current_page")
    public int currentPage;

    @SerializedName("photos")
    public List<Photo> photos;
}
