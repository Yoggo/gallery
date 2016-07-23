package com.dev.gallery.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.dev.gallery.json.Image;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends BaseAdapter{

    private Context context;
    private List<Image> images;

    public ImageAdapter(Context c, List<Image> images){
        context = c;
        this.images = images;
    }

    @Override
    public int getCount(){
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }else{
            imageView = (ImageView) convertView;
        }
        Picasso.with(context).load(images.get(position).url).into(imageView);
        return imageView;
    }

}
