package com.example.georgi.week0layouts;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MyActivity extends Activity {

    int[] colors;
    int currentColor = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_my);
//        addButtonOnClickListener();
//        setContentView(R.layout.activity_my_building_ui);
//        setContentView(R.layout.activity_my_relative_layout);
//        setContentView(R.layout.flags_italy);
//        setContentView(R.layout.flags_uber);
        setContentView(R.layout.flags_medium);
        colors = getResources().getIntArray(R.array.flag_colors);
    }

    private void addButtonOnClickListener() {
        Button imageSwapButton = (Button) findViewById(R.id.image_swap_button);

        imageSwapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView) findViewById(R.id.image_view);
                imageView.setImageResource(R.drawable.android_logo_png);
            }

        });
    }

    public void onClick(View view) {
        currentColor++;
        if (currentColor > colors.length - 1) {
            currentColor = 0;
        }
        view.setBackgroundColor(colors[currentColor]);
    }

}
