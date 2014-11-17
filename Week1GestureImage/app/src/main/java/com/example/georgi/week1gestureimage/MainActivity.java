package com.example.georgi.week1gestureimage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.lang.annotation.ElementType;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MainActivity extends Activity {

    private static final int INDEX_FIRST_POINTER = 0;
    private static final int INDEX_SECOND_POINTER = 1;

    private ImageView mImageView;

    private PointF firstPoint;
    private PointF secondPoint;

    private PointF firstMiddlePoint;
    private PointF secondMiddlePoint;

    private boolean isTwoFingerGesture;

    private GestureDetector mGestureDetector;
    private float currentX;
    private float currentY;
    private float distance;
    private double atan1;
    private double atan2;

    List<Animator> states = new ArrayList<Animator>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image_view);

        MyTouchListener touchListener = new MyTouchListener();
        mImageView.setOnTouchListener(touchListener);

        MyDoubleTapListener gestureListener = new MyDoubleTapListener();
        mGestureDetector = new GestureDetector(this, gestureListener);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void saveFrame(View view) {
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", mImageView.getTranslationX());
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", mImageView.getTranslationY());
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", mImageView.getScaleX());
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", mImageView.getScaleY());
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation", mImageView.getRotation());
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(mImageView, translationX, translationY, scaleX, scaleY, rotation);
        states.add(animator);
    }

    public void playFrames(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(states);
        animatorSet.start();
    }

    class MyTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mGestureDetector.onTouchEvent(event);
            int maskedAction = event.getActionMasked();


            switch (maskedAction) {
                case MotionEvent.ACTION_DOWN: {

                    float x = event.getX(INDEX_FIRST_POINTER);
                    float y = event.getY(INDEX_FIRST_POINTER);

                    firstPoint = new PointF(x, y);

                    break;
                }
                case MotionEvent.ACTION_MOVE: {

                    int maxIndex = event.getPointerCount() - 1;

                    currentX = event.getX(maxIndex);
                    currentY = event.getY(maxIndex);

                    float translationX = mImageView.getTranslationX();
                    float translationY = mImageView.getTranslationY();

//                    if (!isTwoFingerGesture) {
//                        float newXTranslation = translationX + currentX - firstPoint.x;
//                        float newYTranslation = translationY + currentY - firstPoint.y;
//
//                        Log.w("X Translation: ", translationX + "");
////                        Log.w("Y: ", mImageView.getY() + "");
//                        Log.w("Y Translation: ", newYTranslation + "");
//                        mImageView.setTranslationX(newXTranslation);
//                        mImageView.setTranslationY(newYTranslation);
//                    }

//                    else {
                        firstPoint = new PointF(event.getX(INDEX_FIRST_POINTER), event.getY(INDEX_FIRST_POINTER));
                        secondPoint = new PointF(event.getX(INDEX_SECOND_POINTER), event.getY(INDEX_SECOND_POINTER));

                        secondMiddlePoint = calculateMiddlePoint(firstPoint.x, secondPoint.x, firstPoint.y, secondPoint.y);

                        if (firstMiddlePoint.x == 0 && firstMiddlePoint.y == 0)
                        {
                            throw new RuntimeException();
                        }
                        mImageView.setTranslationX(translationX + secondMiddlePoint.x - firstMiddlePoint.x);
                        mImageView.setTranslationY(translationY + secondMiddlePoint.y - firstMiddlePoint.y);

                        float currentDistance = (float) Math.sqrt(Math.pow((secondPoint.x - firstPoint.x), 2) + Math.pow((secondPoint.y - firstPoint.y), 2));

                        float scaleBy = currentDistance / distance;

                        float currentScale = mImageView.getScaleX();

//                        mImageView.setScaleX(currentScale * scaleBy);
//                        mImageView.setScaleY(currentScale * scaleBy);

                        PointF secondVector = new PointF(secondPoint.x - firstPoint.x, secondPoint.y - firstPoint.y);

                        atan2 = Math.atan2(secondVector.y, secondVector.x);

                        float deltaAngleRadians = (float) (atan2 - atan1);
//                          Log.w("Delta Angle:", deltaAngleRadians + "");

                        float deltaAngleDegrees = (float) Math.toDegrees(deltaAngleRadians);

//                        if (v1.y < secondVector.y) {
//                            deltaAngleDegrees = deltaAngleDegrees * -1;
//                        }

                        float currentAngle = mImageView.getRotation();
                        mImageView.setRotation(currentAngle + deltaAngleDegrees);
//                    }

                    break;
                }
                case MotionEvent.ACTION_UP: {
//                    toContinueAfterPointerUp = false;
                    break;
                }

                case MotionEvent.ACTION_POINTER_DOWN: {
                    isTwoFingerGesture = true;

                    float x1 = event.getX(INDEX_SECOND_POINTER);
                    float y1 = event.getY(INDEX_SECOND_POINTER);

                    secondPoint = new PointF(x1, y1);

                    PointF vector = new PointF(secondPoint.x - firstPoint.x, secondPoint.y - firstPoint.y);

                    atan1 = Math.atan2(vector.y, vector.x);

                    firstMiddlePoint = calculateMiddlePoint(firstPoint.x, x1, firstPoint.y, y1);

                    distance = (float) Math.sqrt(Math.pow((secondPoint.x - firstPoint.x), 2) + Math.pow((secondPoint.y - firstPoint.y), 2));

                    break;
                }
                case MotionEvent.ACTION_POINTER_UP: {

                    float upX = event.getX();
                    float upY = event.getY();

                    firstPoint = new PointF(event.getX(INDEX_FIRST_POINTER), event.getY(INDEX_FIRST_POINTER));
                    secondPoint = new PointF(event.getX(INDEX_SECOND_POINTER), event.getY(INDEX_SECOND_POINTER));

                    if (upX == event.getX(0) && upY == event.getY(0)) {
//                        firstPoint = new PointF(secondPoint.x, secondPoint.y);
                    }

                    isTwoFingerGesture = false;
//                    toContinueAfterPointerUp = true;
                    break;
                }
            }

            return true;
        }

        private void log(float currentAngle) {
            Log.w(MainActivity.class.getSimpleName(), currentAngle + "");
        }
    }

    private PointF calculateMiddlePoint(float x, float x1, float y, float y1) {
        float middleX = (x + x1) / 2;
        float middleY = (y + y1) / 2;

        return new PointF(middleX, middleY);
    }

    class MyDoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mImageView.setRotation(0);
            mImageView.setScaleX(1);
            mImageView.setScaleY(1);
            mImageView.setTranslationY(0);
            mImageView.setTranslationX(0);

            return true;
        }
    }
}
