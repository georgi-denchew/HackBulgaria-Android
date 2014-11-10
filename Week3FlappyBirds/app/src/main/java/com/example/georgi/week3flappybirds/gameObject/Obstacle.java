package com.example.georgi.week3flappybirds.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Georgi on 8.11.2014 г..
 */
public class Obstacle implements GameObject {


    public static final int VERTICAL_DISTANCE_BETWEEN_OBSTACLES_PARAMETER = 4;
    private final int birdHeight;
    private PointF position;
    private RectF firstRectangle;
    private RectF secondRectangle;

    public Obstacle(int left, int width, int screenHeight, int birdHeight) {
        this.birdHeight = birdHeight;

        int bottom = calculateFirstRectangleBottom(screenHeight, birdHeight);

        this.firstRectangle = new RectF(left, 0, left + width, bottom);
        this.secondRectangle = new RectF(left, this.firstRectangle.bottom + birdHeight * VERTICAL_DISTANCE_BETWEEN_OBSTACLES_PARAMETER, left + width, screenHeight);
        this.position = new PointF(firstRectangle.left, firstRectangle.top);
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setAntiAlias(true);
        p.setFilterBitmap(true);
        p.setDither(true);
        p.setColor(Color.rgb(80, 180, 80));

        canvas.drawRect(firstRectangle, p);
        canvas.drawRect(secondRectangle, p);
    }

    @Override
    public PointF getPosition() {
        return position;
    }

    @Override
    public int getWidth() {
        return (int) Math.abs(firstRectangle.right - firstRectangle.left);
    }

    @Override
    public int getHeight() {
        return (int) (firstRectangle.bottom - firstRectangle.top);
    }

    @Override
    public void onClick(int screenWidth, int screenHeight) {

    }

    @Override
    public void update(int screenWidth, int screenHeight, float speed) {

        if (this.firstRectangle.left + this.getWidth() < 0) {

            reset(screenWidth, screenHeight);

        } else {
            this.firstRectangle.left -= speed;
            this.firstRectangle.right -= speed;

            this.secondRectangle.left -= speed;
            this.secondRectangle.right -= speed;
        }

        this.position.x = this.firstRectangle.left;
    }

    public void reset(int screenWidth, int screenHeight) {
        int width = getWidth();

        this.firstRectangle.left = screenWidth + width;
        this.firstRectangle.right = this.firstRectangle.left + width;

        this.secondRectangle.left = firstRectangle.left;
        this.secondRectangle.right = firstRectangle.right;

        int bottom = calculateFirstRectangleBottom(screenHeight, this.birdHeight);
        this.firstRectangle.bottom = bottom;
        this.secondRectangle.top = this.firstRectangle.bottom + birdHeight * VERTICAL_DISTANCE_BETWEEN_OBSTACLES_PARAMETER;
    }

    public RectF getFirstRectangle() {
        return firstRectangle;
    }

    public RectF getSecondRectangle() {
        return secondRectangle;
    }

    private int calculateFirstRectangleBottom(int screenHeight, int birdHeight) {
        double randomHeightDouble = (Math.random() + 0.1) * (screenHeight - birdHeight * 5);
        return (int) randomHeightDouble;
    }
}
