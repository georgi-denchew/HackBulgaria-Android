package com.example.georgi.week1puzzlegame;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
import android.widget.GridLayout;
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
    List<Integer> tags;


    private List<Drawable> mDrawablesInitialPossitions;
    private GridLayout initialLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main2);

        createImageViews2();
//        createImageViews();
    }

    private void createImageViews2() {
        mImages = getResources().obtainTypedArray(R.array.images);

        mImageViews = new ArrayList<ImageView>(mImages.length());
        mDrawables = new ArrayList<Drawable>(mImages.length());
        // used for index comparison in the solved-checker
        mDrawablesInitialPossitions = new ArrayList<Drawable>(mImages.length());

        for (int i = 0; i < mImages.length(); i++) {
            Drawable drawable = mImages.getDrawable(i);
            mDrawables.add(drawable);
            mDrawablesInitialPossitions.add(drawable);
        }

        mImages.recycle();
//
//        Collections.shuffle(mDrawables);

        initialLayout = (GridLayout) findViewById(R.id.initial_layout);

        int currentDrawableIndex = 0;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        for (int i = 0; i < 16; i++) {

            // hardcoded on purpose!
            ViewGroup.LayoutParams viewGroupParams = new ViewGroup.LayoutParams(120, 120);

            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(viewGroupParams);

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
            imageView.setTag(i + 1);

            mImageViews.add(imageView);
        }

        Collections.shuffle(mImageViews);

        tags = new ArrayList<Integer>();

        for (ImageView imageView : mImageViews){
            int tag = (Integer) imageView.getTag();
            tags.add(tag);
            initialLayout.addView(imageView);
        }
    }

    private void createImageViews() {
        mImages = getResources().obtainTypedArray(R.array.images);

        mImageViews = new ArrayList<ImageView>(mImages.length());
        mDrawables = new ArrayList<Drawable>(mImages.length());
        // used for index comparison in the solved-checker
        mDrawablesInitialPossitions = new ArrayList<Drawable>(mImages.length());

        for (int i = 0; i < mImages.length(); i++) {
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
            childLayout.setPadding(0, 0, 0, 0);

            initialLayout.addView(childLayout);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.25f);

            for (int j = 0; j < 4; j++) {
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

                case DragEvent.ACTION_DRAG_STARTED: {
                    v.setPadding(1, 1, 1, 1);
                    v.setBackgroundColor(Color.parseColor("#00ff00"));
                    break;
                }

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

//                    v2.setImageDrawable(d1);
//                    v1.setImageDrawable(d2);
                    float x1 = v1.getX();
                    float y1 = v1.getY();
                    float x2 = v2.getX();
                    float y2 = v2.getY();

                    PropertyValuesHolder pvhX1 = PropertyValuesHolder.ofFloat("x", x1);
                    PropertyValuesHolder pvhY1 = PropertyValuesHolder.ofFloat("y", y1);

                    PropertyValuesHolder pvhX2 = PropertyValuesHolder.ofFloat("x", x2);
                    PropertyValuesHolder pvhY2 = PropertyValuesHolder.ofFloat("y", y2);

                    Animator a1 = ObjectAnimator.ofPropertyValuesHolder(v1, pvhX2, pvhY2);
                    Animator a2 = ObjectAnimator.ofPropertyValuesHolder(v2, pvhX1, pvhY1);

                    int tag1 =(Integer) v1.getTag();
                    int tag2 =(Integer) v2.getTag();

                    int index1 = tags.indexOf(tag1);
                    int index2= tags.indexOf(tag2);
                    tags.set(index1, tag2);
                    tags.set(index2, tag1);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playSequentially(a1, a2);
                    animatorSet.start();

                    boolean isSolved = checkSolvedPuzzle2();
//                    boolean isSolved = checkSolvedPuzzle();

                    if (isSolved) {
                        Toast.makeText(MainActivity.this, "Congrats! Puzzle solved!", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }

            return true;
        }
    }

    private boolean checkSolvedPuzzle2() {
        boolean isSolved = true;

        for (int i =0; i < tags.size() - 1; i++){
            int t1 = tags.get(i);
            int t2 = tags.get(i + 1);

            if (t1 >= t2){
                isSolved =false;
            }
        }

//        for (int i = 0; i < initialLayout.getChildCount() - 1; i++) {
//            initialLayout.getChildAt(i).getTag();
//            int tag0 = (Integer) initialLayout.getChildAt(i).getTag();
//            int tag1 = (Integer) initialLayout.getChildAt(i + 1).getTag();
////            int taga0 = (Integer) mImageViews.get(i).getTag();
////            int taga1 = (Integer) mImageViews.get(i + 1).getTag();
//
//            if (tag0 <= tag1){
//                isSolved = false;
//            }
//        }

        return isSolved;
    }

    private boolean checkSolvedPuzzle() {
        boolean isSolved = true;

        for (int i = 0; i < mImageViews.size(); i++) {
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
