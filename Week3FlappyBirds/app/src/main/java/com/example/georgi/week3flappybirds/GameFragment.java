package com.example.georgi.week3flappybirds;

import android.app.Activity;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.georgi.week3flappybirds.gameObject.Background;
import com.example.georgi.week3flappybirds.gameObject.Bird;
import com.example.georgi.week3flappybirds.gameObject.Obstacle;


public class GameFragment extends Fragment implements CollisionListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnGameOverListener mListener;

    private static final String LOG_TAG = MainActivity.class.getName();

    private DrawingView mDrawingView;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mClickSoundMediaPlayer;
    private MediaPlayer mGameOverPlayer;

    private GameClock mGameClock;
    private int screenHeight;
    private int screenWidth;

    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnGameOverListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        initGame();
    }


    private void initGame() {
        mDrawingView = (DrawingView) getActivity().findViewById(R.id.parent_view);

        mDrawingView.setCollisionListener(this);

        hideSystemUI();

        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.prey_overture);
        mMediaPlayer.setLooping(true);

        mClickSoundMediaPlayer = MediaPlayer.create(getActivity(), R.raw.beep);
        mClickSoundMediaPlayer.setVolume(1, 1);

        mGameOverPlayer = MediaPlayer.create(getActivity(), R.raw.beep);

        mGameClock = new GameClock();

        mGameClock.setRootView(mDrawingView);

        calculateScreenSize();

        Background background = new Background(getActivity());
        mDrawingView.subscribe(background);

        Bird bird = new Bird(getActivity());
        mDrawingView.subscribe(bird);

        for (int i = 0; i < 4; i++) {
            Obstacle obstacle = new Obstacle(screenWidth + (i * screenWidth / 3), 100, screenHeight, bird.getHeight());
            mDrawingView.subscribe(obstacle);
        }

        View.OnClickListener clickListener = new MyClickListener();
        mDrawingView.setOnClickListener(clickListener);
    }

    private void calculateScreenSize() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }


    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void handleCollision(Long score) {
        mGameOverPlayer.start();
        mListener.handleGameOver(score);
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mClickSoundMediaPlayer.start();
            DrawingView view = (DrawingView) v;
            view.distributeClick();
        }
    }

    public interface OnGameOverListener {
        public void handleGameOver(Long score);
    }
}