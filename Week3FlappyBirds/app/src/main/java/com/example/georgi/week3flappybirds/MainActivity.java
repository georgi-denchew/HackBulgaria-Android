package com.example.georgi.week3flappybirds;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.georgi.week3flappybirds.webService.ResultSubmitter;
import com.example.georgi.week3flappybirds.webService.UserData;


public class MainActivity extends Activity
        implements GameFragment.OnGameOverListener,
        SubmitFragment.OnSubmitActionListener, ResultSubmitter.ResultHandler {

    private long mScore;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        GameFragment gameFragment = new GameFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_placeholder, gameFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mMediaPlayer.pause();
    }

    @Override
    public void handleGameOver(Long score) {
        mScore = score;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SubmitFragment submitFragment = new SubmitFragment();
        fragmentTransaction.replace(R.id.fragment_placeholder, submitFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    public void onSubmitAction(UserData userData) {
        userData.setScore(mScore);

        ResultSubmitter resultSubmitter = new ResultSubmitter(this);
        resultSubmitter.execute(userData);
    }

    @Override
    public void showProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayNetworkError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "A network error has occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void displayNoInternetConnection() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "No Internet connection detected", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}