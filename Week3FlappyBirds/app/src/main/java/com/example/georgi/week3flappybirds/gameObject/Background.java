package com.example.georgi.week3flappybirds.gameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.georgi.week3flappybirds.DrawingView;
import com.example.georgi.week3flappybirds.GameEvent;
import com.example.georgi.week3flappybirds.R;

/**
 * Created by Georgi on 29.10.2014 г..
 */
public class Background implements GameObject {

    private static final float BACKGROUND_SLIDE_PARAMETER =  0.02f;

    private Bitmap bitmap;
    private PointF position = new PointF(0,0);

    private int width;
    private int height;

    public Background(Context context) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.clouds);
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.position =  new PointF(0,0);
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        canvas.drawBitmap(bitmap, position.x, position.y, new Paint());
        canvas.drawBitmap(bitmap, position.x + bitmap.getWidth(), position.y, new Paint());
    }

    @Override
    public PointF getPosition() {
        return position;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void onClick(int screenWidth, int screenHeight) {
    }

    @Override
    public void update(int screenWidth, int screenHeight, float speed) {
        this.position.x -= screenWidth * BACKGROUND_SLIDE_PARAMETER;
        double absX = Math.abs(position.x);
        if (bitmap.getWidth() <= absX){
            this.position.x = 0;
        }
    }

    public Bitmap getBitmapDrawable() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap= bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();

    }
}