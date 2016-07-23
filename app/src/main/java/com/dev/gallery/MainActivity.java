package com.dev.gallery;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.dev.gallery.adapters.ImageAdapter;
import com.dev.gallery.json.Image;
import com.dev.gallery.json.Page;
import com.dev.gallery.json.Photo;
import com.dev.gallery.loaders.PageLoader;
import com.dev.gallery.tools.NetworkStateManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Page>, View.OnClickListener{


    public static final String TAG = MainActivity.class.getName();
    public static final String PAGE_NUMBER = "PageNumber";
    public static final String IS_ANOTHER_PAGE = "IsAnotherPage";

    public static final int LOADER_ID = 1;

    private int pageNumber = 1;
    private boolean isAnotherPage;

    private ProgressDialog progress;
    private GridView gridView;
    private ImageAdapter imageAdapter;
    private Button previousButton;
    private Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previousButton = (Button) findViewById(R.id.previous_button);
        nextButton = (Button) findViewById(R.id.next_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (imageAdapter != null) {
                    Image image = (Image) imageAdapter.getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(FullImageActivity.URL, image.url);
                    Intent intent = new Intent(MainActivity.this, FullImageActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

        if( savedInstanceState != null ) {
            isAnotherPage = savedInstanceState.getBoolean(IS_ANOTHER_PAGE);
            pageNumber = savedInstanceState.getInt(PAGE_NUMBER);
        }

        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);


        progress=new ProgressDialog(this);
        progress.setMessage("Loading");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(isNetworkConnected()){
            getLoaderManager().initLoader(LOADER_ID, savedInstanceState, this);
        }

    }

    private void updateTitle(int displayPageNumber){
        getSupportActionBar().setTitle("Gallery|Page " + displayPageNumber);
    }

    private boolean isNetworkConnected(){
        if(NetworkStateManager.isNetworkConnected(this)){
            return true;
        }else{
            Toast.makeText(this,"Please check internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.previous_button:
                if(pageNumber > 1){
                    pageNumber--;
                    isAnotherPage = true;
                    if(isNetworkConnected()){
                        getLoaderManager().restartLoader(LOADER_ID, null, this);
                    }
                }
                break;
            case R.id.next_button:
                pageNumber++;
                isAnotherPage = true;
                if(isNetworkConnected()){
                    getLoaderManager().restartLoader(LOADER_ID, null, this);
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(PAGE_NUMBER, pageNumber);
        outState.putBoolean(IS_ANOTHER_PAGE, isAnotherPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Page> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        PageLoader pageLoader = null;
        if (id == LOADER_ID) {
            pageLoader = new PageLoader(this);
            pageLoader.setPageNumber(pageNumber);
        }
        progress.show();
        return pageLoader;
    }

    @Override
    public void onLoadFinished(Loader<Page> loader, Page data) {
        Log.d(TAG, "onLoadFinished");
        if(imageAdapter == null || isAnotherPage){
            try{
                List<Photo> photos = data.photos;
                List<Image> images = new ArrayList<Image>();
                for(Photo photo : photos){
                    images.addAll(photo.images);
                }
                imageAdapter = new ImageAdapter(this, images);
                gridView.setAdapter(imageAdapter);
                isAnotherPage = false;
                updateTitle(data.currentPage);
            }catch (NullPointerException e){
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
            }

        }
        progress.hide();
    }

    @Override
    public void onLoaderReset(Loader<Page> loader){
        //do something
    }
}
