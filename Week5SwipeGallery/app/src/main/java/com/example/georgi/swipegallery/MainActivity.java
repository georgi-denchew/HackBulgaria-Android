package com.example.georgi.swipegallery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private SwipeFragmentAdapter swipeFragmentAdapter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("page", mViewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewPager = (ViewPager) findViewById(R.id.view_pager);

        File picturesFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        List<File> picturesList = Arrays.asList(picturesFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return new File(dir, filename).isDirectory() == false;
            }
        }));

        swipeFragmentAdapter = new SwipeFragmentAdapter(getSupportFragmentManager(), picturesList);
        mViewPager.setAdapter(swipeFragmentAdapter);
        mViewPager.setSaveFromParentEnabled(false);
        mViewPager.setSaveEnabled(false);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        final int myPage = savedInstanceState.getInt("page");
        mViewPager.setCurrentItem(myPage);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
