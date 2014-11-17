package com.example.georgi.week3flappybirds;

import android.os.Handler;

import com.example.georgi.week3flappybirds.gameObject.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgi on 29.10.2014 г..
 */
public class GameClock {
    private long elapsedTime;

    private DrawingView rootView;

    private Handler handler = new Handler();

    public GameClock() {
        handler.post(new ClockRunnable());
    }

    private class ClockRunnable implements Runnable {
        @Override
        public void run() {
            if (!rootView.isGameOver()) {
                onTimerTick();
                handler.postDelayed(this, Settings.FRAMERATE_CONSTANT);
            }
        }

        private void onTimerTick() {

            rootView.update();
            rootView.invalidate();
        }
    }

    public DrawingView getRootView() {
        return rootView;
    }

    public void setRootView(DrawingView rootView) {
        this.rootView = rootView;
    }
}