package com.example.minitumblr.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.minitumblr.R;
import com.example.minitumblr.view.model.PhotoVM;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    private Context mContext;
    private List<PhotoVM> mPhotos;

    public SliderAdapter(Context context) {
        mContext = context;
    }

    public void setPhotos(List<PhotoVM> photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_slider, container, false);
        PhotoVM photo = mPhotos.get(position);
        ImageView imgSliderItem = view.findViewById(R.id.img_slider_item);
        Picasso.get()
                .load(photo.getUrl())
                .placeholder(R.drawable.ic_image_24dp)
                .error(R.drawable.ic_broken_image_24dp)
                .into(imgSliderItem);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}
