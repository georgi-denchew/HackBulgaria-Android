package com.example.georgi.swipegallery;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Georgi on 19.11.2014 г..
 */
public class ViewPagerFragment extends Fragment {

    Bitmap bitmap;

    public ViewPagerFragment() {
    }

    @Override
    public void setArguments(Bundle args) {
        bitmap = (Bitmap) args.getParcelable("bitmap");
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());



        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        imageView.setLayoutParams(layoutParams);

        imageView.setImageBitmap(bitmap);
        return imageView;
    }
}
