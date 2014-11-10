package com.example.georgi.week3flappybirds;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.georgi.week3flappybirds.gameObject.Background;
import com.example.georgi.week3flappybirds.gameObject.Bird;
import com.example.georgi.week3flappybirds.gameObject.Obstacle;
//import com.example.georgi.week3flappybirds.gameObject.Bird;

import java.io.IOException;


public class MainActivity extends Activity implements CollisionListener {

    private static final String LOG_TAG = MainActivity.class.getName();

    private DrawingView mDrawingView;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mClickSoundMediaPlayer;
    private MediaPlayer mGameOverPlayer;

    private GameClock mGameClock;
    private int screenHeight;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawingView = (DrawingView) findViewById(R.id.parent_view);

        mDrawingView.setCollisionListener(this);

        hideSystemUI();

        mMediaPlayer = MediaPlayer.create(this, R.raw.prey_overture);
        mMediaPlayer.setLooping(true);

        mClickSoundMediaPlayer = MediaPlayer.create(this, R.raw.beep);
        mClickSoundMediaPlayer.setVolume(1, 1);

        mGameOverPlayer = MediaPlayer.create(this, R.raw.beep);

        mGameClock = new GameClock();

        mGameClock.setRootView(mDrawingView);

        calculateScreenSize();

        Background background = new Background(this);
        mDrawingView.subscribe(background);

        Bird bird = new Bird(this);
        mDrawingView.subscribe(bird);

        for (int i = 0; i < 4; i++) {
            Obstacle obstacle = new Obstacle(screenWidth + (i * screenWidth / 3), 100, screenHeight, bird.getHeight());
            mDrawingView.subscribe(obstacle);
        }

        View.OnClickListener clickListener = new MyClickListener();
        mDrawingView.setOnClickListener(clickListener);
    }

    private void calculateScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.pause();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void handleCollision() {
        mGameOverPlayer.start();
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mClickSoundMediaPlayer.start();
            DrawingView view = (DrawingView) v;
            view.distributeClick();
        }
    }
}