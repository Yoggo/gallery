package com.dev.gallery.json;

import com.google.gson.annotations.SerializedName;


public class Image {
    @SerializedName("size")
    public int size;

    @SerializedName("url")
    public String url;
}
