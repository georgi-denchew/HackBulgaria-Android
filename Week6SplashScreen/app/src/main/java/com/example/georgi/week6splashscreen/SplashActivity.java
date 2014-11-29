package com.example.georgi.week6splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    public static final int DELAY_MILLIS = 3000;
    public static final String SETTINGS = "settings";
    public static final String SKIP_SPLASH = "skipSplash";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(SETTINGS, MODE_PRIVATE);

        boolean skipSplash = sharedPreferences.getBoolean(SKIP_SPLASH, false);

        if (skipSplash) {
            moveToMainActivity();

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    moveToMainActivity();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(SKIP_SPLASH, true);
                    editor.commit();
                }
            }, DELAY_MILLIS);
        }

    }

    private void moveToMainActivity() {
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

}
