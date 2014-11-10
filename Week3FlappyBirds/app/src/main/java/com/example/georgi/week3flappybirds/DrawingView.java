package com.example.georgi.week3flappybirds;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.georgi.week3flappybirds.gameObject.Bird;
import com.example.georgi.week3flappybirds.gameObject.GameObject;
import com.example.georgi.week3flappybirds.gameObject.Obstacle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Georgi on 29.10.2014 г..
 */
public class DrawingView extends ImageView {

    private CollisionListener collisionListener;
    private long gameStartTime = SystemClock.uptimeMillis();

    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private int screenWidth;
    private int screenHeight;
    private Bird bird;
    private double speed = 1;

    public void distributeClick() {
        for (GameObject gameObject : gameObjects) {
            calculateScreenSize();

            gameObject.onClick(screenWidth, screenHeight);
        }
    }


    public static interface GameClockListener {
        public void onGameEvent(GameEvent gameEvent);
    }

    public void subscribe(GameObject gameObject) {
        gameObjects.add(gameObject);

        if (gameObject instanceof Bird){
            this.bird =(Bird) gameObject;
        }
    }

    public void unSubscribe(GameObject listener) {
        gameObjects.remove(listener);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void update() {
        calculateScreenSize();

        checkForOutOfBounds();
        checkForCollisions();

        this.speed += 0.0001f;
        for (GameObject gameObject : gameObjects) {
            gameObject.update(screenWidth, screenHeight, (float)speed);
        }
    }

    private void checkForOutOfBounds() {
        boolean isGameOver = bird.getPosition().y + bird.getHeight() > screenHeight;

        if (isGameOver){
            reset();
        }
    }

    public CollisionListener getCollisionListener() {
        return collisionListener;
    }

    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(getContext(), canvas);
        }
    }

    private void calculateScreenSize() {
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    private void checkForCollisions() {
        RectF birdRect = new RectF(bird.getPosition().x, bird.getPosition().y, bird.getPosition().x + bird.getWidth(), bird.getPosition().y + bird.getHeight());

        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Obstacle){
                Obstacle obstacle = (Obstacle) gameObject;
                boolean hasCollisions = birdRect.intersect(obstacle.getFirstRectangle()) || birdRect.intersect(obstacle.getSecondRectangle());
                boolean isAboveObstacle = bird.getPosition().y < 0 && (obstacle.getFirstRectangle().left <= bird.getPosition().x && bird.getPosition().x <= obstacle.getFirstRectangle().right);
                hasCollisions = hasCollisions || isAboveObstacle;
                if (hasCollisions){
                    collisionListener.handleCollision();
                    reset();
                    break;
                }
            }
        }
    }

    private synchronized void reset() {
        long gameEndTime = SystemClock.uptimeMillis();

        long gameScore = (gameEndTime - gameStartTime) / 1000;
        Toast.makeText(getContext(), "Game Over! Score: " + gameScore, Toast.LENGTH_SHORT).show();
        this.bird.getPosition().y = 0;

        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {

            GameObject gameObject = iterator.next();
            if (gameObject instanceof Obstacle){
                iterator.remove();
            }
        }

        for (int i = 0; i < 4; i++) {
            Obstacle obstacle = new Obstacle(screenWidth + (i * screenWidth / 3), 100, screenHeight, bird.getHeight());
            subscribe(obstacle);
        }

        this.gameStartTime = SystemClock.uptimeMillis();
        this.speed = 1;
    }
}
