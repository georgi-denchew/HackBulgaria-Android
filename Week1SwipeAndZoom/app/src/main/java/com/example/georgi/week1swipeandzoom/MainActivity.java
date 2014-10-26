package com.example.georgi.week1swipeandzoom;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

    private TypedArray mImagesTypedArray;
    private ImageView mImageView;
    private int currentIndex;
    private GestureDetector mDetector;
    private TextView mCounterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView)findViewById(R.id.image_view);
        mImagesTypedArray = getResources().obtainTypedArray(R.array.images);

        mImageView.setImageDrawable(mImagesTypedArray.getDrawable(0));

        GestureDetector.SimpleOnGestureListener gestureListener = new MyGestureListener();
        mDetector = new GestureDetector(this, gestureListener);

        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        mCounterTextView = (TextView) findViewById(R.id.counter_text_view);

        updateCurrentIndexTextView();
    }

    private void updateCurrentIndexTextView(){
        String counterText = String.format("%d/%d", currentIndex + 1, mImagesTypedArray.length());
        mCounterTextView.setText(counterText);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private boolean isUpscaled = false;

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            if (!isUpscaled) {
                mImageView.setScaleX(2);
                mImageView.setScaleY(2);
            } else {
                mImageView.setScaleX(1);
                mImageView.setScaleY(1);
            }

            isUpscaled = !isUpscaled;

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x0 = e1.getX();
            float x1 = e2.getX();

            if (x1 < x0) {
                currentIndex++;
            } else if (x1 > x0){
                currentIndex--;
            }

            if (currentIndex > mImagesTypedArray.length() - 1){
                currentIndex = 0;
            } else if (currentIndex <0 ){
                currentIndex = mImagesTypedArray.length() - 1;
            }

            mImageView.setImageDrawable(mImagesTypedArray.getDrawable(currentIndex));
            updateCurrentIndexTextView();
            return true;
        }
    }
}
