package com.dev.gallery.api;

import com.dev.gallery.json.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GalleryAPI {
    public static String ENDPOINT = "https://api.500px.com/";
    public static String FEATURE = "popular";
    public static String CONSUMER_KEY = "wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF";

    @GET("v1/photos")
    Call<Page> getPage(@Query("page") String page,
                       @Query("feature") String feature,
                       @Query("consumer_key") String consumerKey);
}
