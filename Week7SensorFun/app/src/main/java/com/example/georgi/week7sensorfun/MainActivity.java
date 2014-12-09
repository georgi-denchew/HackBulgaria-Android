package com.example.georgi.week7sensorfun;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;


public class MainActivity extends Activity implements SensorEventListener {

    private Sensor accelerometerSensor;
    private Sensor proximitySensor;
    private SensorManager sensorManager;
    MediaPlayer rayGunPlayer;
    MediaPlayer roarGunPlayer;

    private static final float ACCELEROMETER_CONST = 12f;
    private static final float PROXIMITY_CONST = 5F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        rayGunPlayer = MediaPlayer.create(this, R.raw.ray_gun);
        roarGunPlayer = MediaPlayer.create(this, R.raw.roar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometerSensor);
        sensorManager.unregisterListener(this, proximitySensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER: {
                if (event.values[0] > ACCELEROMETER_CONST || event.values[1] > ACCELEROMETER_CONST || event.values[2] > ACCELEROMETER_CONST) {

                    if (rayGunPlayer.isPlaying()){
                        rayGunPlayer.stop();
                        try {
                            rayGunPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    rayGunPlayer.start();
                }
                break;
            }
            case Sensor.TYPE_PROXIMITY: {
                if (event.values[0] < PROXIMITY_CONST){

                    if (roarGunPlayer.isPlaying()){
                        roarGunPlayer.stop();
                        try {
                            roarGunPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    roarGunPlayer.start();
                }
                break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
