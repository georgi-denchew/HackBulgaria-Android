package com.example.georgi.week1puzzlegame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends Activity {

    TypedArray mImages;

    List<ImageView> mImageViews;
    List<Drawable> mDrawables;
    private List<Drawable> mDrawablesInitialPossitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createImageViews();
    }

    private void createImageViews() {
        mImages = getResources().obtainTypedArray(R.array.images);

        mImageViews = new ArrayList<ImageView>(mImages.length());
        mDrawables = new ArrayList<Drawable>(mImages.length());
        // used for index comparison in the solved-checker
        mDrawablesInitialPossitions = new ArrayList<Drawable>(mImages.length());

        for (int i = 0; i < mImages.length(); i++){
            Drawable drawable = mImages.getDrawable(i);
            mDrawables.add(drawable);
            mDrawablesInitialPossitions.add(drawable);
        }

        mImages.recycle();

        Collections.shuffle(mDrawables);

        LinearLayout initialLayout = (LinearLayout) findViewById(R.id.initial_layout);

        int currentDrawableIndex = 0;

        for (int i = 0; i < 4; i++) {
            LinearLayout childLayout = new LinearLayout(this);
            childLayout.setPadding(0,0,0,0);

            initialLayout.addView(childLayout);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.25f);

            for (int j = 0; j < 4; j++){
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(layoutParams);
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                MyTouchListener touchListener = new MyTouchListener();
                imageView.setOnTouchListener(touchListener);

                MyDragListener dragListener = new MyDragListener();
                imageView.setOnDragListener(dragListener);

                Drawable drawable = mDrawables.get(currentDrawableIndex++);
                imageView.setImageDrawable(drawable);

                mImageViews.add(imageView);

                childLayout.addView(imageView);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.startDrag(null, new View.DragShadowBuilder(v), v, 0);

            return true;
        }
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();

            switch (action) {
                case DragEvent.ACTION_DRAG_ENTERED: {
                    v.setPadding(1, 1, 1, 1);
                    v.setBackgroundColor(Color.parseColor("#00ff00"));
                    break;
                }

                case DragEvent.ACTION_DRAG_EXITED: {
                    v.setPadding(0, 0, 0, 0);
                    v.setBackgroundColor(Color.parseColor("#000000"));
                    break;
                }

                case DragEvent.ACTION_DROP: {
                    ImageView v1 = ((ImageView) v);
                    v1.setPadding(0, 0, 0, 0);
                    v1.setBackgroundColor(Color.parseColor("#000000"));
                    ImageView v2 = ((ImageView) event.getLocalState());
                    Drawable d1 = v1.getDrawable();
                    Drawable d2 = v2.getDrawable();

                    v2.setImageDrawable(d1);
                    v1.setImageDrawable(d2);

                    boolean isSolved = checkSolvedPuzzle();

                    if (isSolved) {
                        Toast.makeText(MainActivity.this, "Congrats! Puzzle solved!", Toast.LENGTH_LONG).show();
                    }

                    break;
                }
            }

            return true;
        }
    }

    private boolean checkSolvedPuzzle(){
        boolean isSolved = true;

        for (int i = 0; i < mImageViews.size(); i++){
            Drawable drawable = mImageViews.get(i).getDrawable();

            int indexOf = mDrawablesInitialPossitions.indexOf(drawable);

            if (indexOf != i) {
                isSolved = false;
                break;
            }
        }

        return isSolved;
    }
}
