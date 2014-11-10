package com.example.georgi.week3flappybirds.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import com.example.georgi.week3flappybirds.GameEvent;

/**
 * Created by Georgi on 29.10.2014 г..
 */
public interface GameObject {
    void draw(Context context, Canvas canvas);
    PointF getPosition();
    int getWidth();
    int getHeight();

    //boolean or void?
    void onClick(int screenWidth, int screenHeight);
    void update(int screenWidth, int screenHeight, float speed);
}
