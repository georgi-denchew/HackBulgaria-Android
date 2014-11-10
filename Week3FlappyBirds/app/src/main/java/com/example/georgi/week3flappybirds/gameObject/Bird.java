package com.example.georgi.week3flappybirds.gameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.example.georgi.week3flappybirds.R;

/**
 * Created by Georgi on 7.11.2014 г..
 */
public class Bird implements GameObject {

    public static final int FALL_ACCELERATION_DELIMITER = 50;
    public static final int MAX_FALL_ACCELERATION = 1;
    public static final float FALL_ACCELERATION_INCREMENTATION = 0.1f;
    public static final float MIN_RISE_DECELERATION = 0.0f;
    public static final float RISE_DECELERATION_INCREMENTATION = 0.1f;
    public static final int SCREEN_DELIMITER = 15;
    public static final float INITIAL_FALL_ACCELERATION = 0.01f;
    public static final int MIN_INTERPOLATION = 0;
    public static final int MAX_INTERPOLATION = 1;

    private AccelerateInterpolator accelerateInterpolator;
    private float fall_acceleration;

    private DecelerateInterpolator decelerateInterpolator;
    private float rise_deceleration;

    private Bitmap bitmap;
    private PointF position;

    private boolean toRise;

    public Bird(Context context) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_resized);
        this.position = new PointF(0, 0);

        this.accelerateInterpolator = new AccelerateInterpolator();
        this.fall_acceleration = INITIAL_FALL_ACCELERATION;

        this.decelerateInterpolator = new DecelerateInterpolator();
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        canvas.drawBitmap(bitmap, position.x, position.y, new Paint());

//        if(isGameOver){
//            Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show();
//            isGameOver = false;
//        }
    }

    @Override
    public PointF getPosition() {
        return this.position;
    }

    @Override
    public int getWidth() {
        return this.bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return this.bitmap.getHeight();
    }

    @Override
    public void onClick(int screenWidth, int screenHeight) {
        toRise = true;
        this.rise_deceleration = MIN_RISE_DECELERATION;
        this.fall_acceleration = INITIAL_FALL_ACCELERATION;
    }

    @Override
    public void update(int screenWidth, int screenHeight, float speed) {
        float deltaY = 0;

        //rise logic
        if (toRise) {
            this.rise_deceleration += RISE_DECELERATION_INCREMENTATION;
            if (MIN_INTERPOLATION < this.rise_deceleration && this.rise_deceleration < MAX_INTERPOLATION) {
                float distance = (screenHeight / SCREEN_DELIMITER);
                float interpolated = this.decelerateInterpolator.getInterpolation(this.rise_deceleration);
                deltaY = distance - (distance * interpolated);
                deltaY *= -1;
            } else {
                toRise = false;
            }
        } else {
            // fall logic
            if (this.fall_acceleration < MAX_FALL_ACCELERATION) {
                this.fall_acceleration += FALL_ACCELERATION_INCREMENTATION;
            }
            deltaY = (screenHeight / FALL_ACCELERATION_DELIMITER) * this.accelerateInterpolator.getInterpolation(fall_acceleration);
        }
        this.position.y += deltaY;
    }
}
