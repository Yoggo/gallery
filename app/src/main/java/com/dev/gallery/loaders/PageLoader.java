package com.dev.gallery.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.dev.gallery.api.GalleryAPI;
import com.dev.gallery.json.Page;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PageLoader extends AsyncTaskLoader<Page> {

    public static final String TAG = PageLoader.class.getName();

    private Page page;
    private int pageNumber;

    public PageLoader(Context context) {
        super(context);
    }

    public void setPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
    }


    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public Page loadInBackground() {
        Log.d(TAG, "loadInBackground");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GalleryAPI.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
                .build();
        GalleryAPI galleryAPI = retrofit.create(GalleryAPI.class);
        Call<Page> callPage = galleryAPI.getPage(String.valueOf(pageNumber),GalleryAPI.FEATURE,GalleryAPI.CONSUMER_KEY);
        try {
            page = callPage.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public void deliverResult(Page page){
        super.deliverResult(page);
    }
}
