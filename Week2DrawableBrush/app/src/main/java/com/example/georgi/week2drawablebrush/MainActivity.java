package com.example.georgi.week2drawablebrush;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity {

    private MyCustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customView = (MyCustomView) findViewById(R.id.my_custom_view);
    }

    public void chooseBrush(View view) {
        ImageView imageView = (ImageView)view;
        imageView.setSelected(true);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        customView.setBitmapDrawable(bitmapDrawable);
    }
}