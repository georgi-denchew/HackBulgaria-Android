package com.example.georgi.swipegallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ImageView;

import org.apache.commons.logging.Log;

import java.io.File;
import java.nio.BufferUnderflowException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Georgi on 19.11.2014 г..
 */
public class SwipeFragmentAdapter extends FragmentStatePagerAdapter {

    List<File> imagesList;

    public SwipeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public SwipeFragmentAdapter(FragmentManager fm, List<File> imagesList) {
        super(fm);
        this.imagesList = imagesList;
    }

    @Override
    public Fragment getItem(int i) {

        File currentImage = this.imagesList.get(i);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(currentImage.getPath(), options);

        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bitmap", bitmap);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }
}
