package com.example.georgi.week0widgets;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getName();

    RelativeLayout mColorPreviewerLayout;
    EditText mColorEditText;
    String colorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_previewer);

        initColorPreviewerTask();
    }

    private void initColorPreviewerTask() {
        mColorPreviewerLayout = (RelativeLayout) findViewById(R.id.color_previewer_layout);
        mColorEditText = (EditText) findViewById(R.id.edit_text_color);

        mColorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {

                mColorPreviewerLayout.setBackgroundColor(Color.parseColor(charSequence.toString()));
                } catch (RuntimeException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColor(){
        if (mColorEditText != null && mColorPreviewerLayout != null) {
            String colorString = mColorEditText.getText().toString();

            mColorPreviewerLayout.setBackgroundColor(Color.parseColor(colorString));
        }
    }

    public void changeColor(View view) {
        changeColor();
    }
}
