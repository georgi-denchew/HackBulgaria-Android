package com.example.georgi.week0widgets;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getName();

    RelativeLayout mColorPreviewerLayout;
    EditText mColorEditText;
    String mColorString;

    ImageButton mPrevButton;
    ImageButton mPlayPauseButton;
    ImageButton mNextButton;
    VideoView mVideoView;
    boolean mHasPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.color_previewer);
//        initColorPreviewerTask();

        setContentView(R.layout.video_player);

        initVideoPlayerPlayLayout();
    }


    private void initVideoPlayerPlayLayout() {
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPlayPauseButton = (ImageButton) findViewById(R.id.play_pause_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPlayPauseButton.setImageResource(R.drawable.play);

        mHasPlayButton = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        String externalStorageDirectory = Environment.getExternalStorageDirectory().getPath();

        File videoFile = new File(externalStorageDirectory + "/Movies/Ronaldo.wmv");
        Uri videoUri = Uri.fromFile(videoFile);

        mVideoView = (VideoView) findViewById(R.id.video_player);
        mVideoView.setVideoURI(videoUri);
        mVideoView.requestFocus();
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

    public void rewind(View view) {
        int current = mVideoView.getCurrentPosition();
        mVideoView.seekTo(current - 3000);

        initPause();
    }

    public void playPause(View view) {
        if (!mHasPlayButton) {
            initPause();
        } else {
            mHasPlayButton = false;
            mVideoView.start();
            mPlayPauseButton.setImageResource(R.drawable.pause);
        }
    }

    private void initPause() {
        mHasPlayButton = true;
        mVideoView.pause();
        mPlayPauseButton.setImageResource(R.drawable.play);
    }

    public void fastForward(View view) {
        int current = mVideoView.getCurrentPosition();
        mVideoView.seekTo(current + 3000);

        initPause();
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

    private void changeColor() {
        if (mColorEditText != null && mColorPreviewerLayout != null) {
            String colorString = mColorEditText.getText().toString();

            mColorPreviewerLayout.setBackgroundColor(Color.parseColor(colorString));
        }
    }

    public void changeColor(View view) {
        changeColor();
    }
}
