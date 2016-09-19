package com.techjini.training.songsdownloader;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by techjini on 14/9/16.
 */
public class JioActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private ScrollView mScrollView;
    private LinearLayout mMainVerticalLinearLayout, mLeftBarLinearLayout, mTimingLinearLayout, mDateLinearLayout;
    private VideoView videoview;
    private HorizontalScrollView hsvTimings, hsvShows;

    private String[] showNames = {"Swaragini", "Bigg Boss", "Siya Ke Raam", "CID", "Crime Patrol",
            "Savdhaan India", "Swaragini", "Bigg Boss", "Siya Ke Raam", "CID"};

    private String VideoURL = "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4";

    private LayoutInflater inflater;

    private RelativeLayout mVideoViewBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jio_shows);

        setUpToolBar();

        initialiseViews();

        inflater = LayoutInflater.from(this);

        for (int i = 0; i < 10; i++) {

            LinearLayout parent = new LinearLayout(this);

            parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            parent.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < 10; j++) {
                View inflatedLayout = inflater.inflate(R.layout.show_item_view, null, false);
                TextView showName = (TextView) inflatedLayout.findViewById(R.id.text_show_name);
                showName.setText(showNames[j]);
                if (i == 1 && (j == 1 || j == 0)) {
                    if (j == 1) {
                        showName.setText(null);
                    }
                    inflatedLayout.findViewById(R.id.view_bg).setBackgroundResource(0);
                }
                parent.addView(inflatedLayout);

                inflatedLayout.setOnClickListener(this);
            }
            mMainVerticalLinearLayout.addView(parent);

            View inflatedLayout = inflater.inflate(R.layout.show_item_timing, null, false);
            mTimingLinearLayout.addView(inflatedLayout);

            View inflatedLayoutDate = inflater.inflate(R.layout.show_item_date, null, false);
            mDateLinearLayout.addView(inflatedLayoutDate);
        }

        for (int i = 0; i < 10; i++) {
            View inflatedLayout = inflater.inflate(R.layout.channel_item_view, null, false);
            ImageView iv = (ImageView) inflatedLayout.findViewById(R.id.image);
            if (i % 2 == 0) {
                iv.setImageResource(R.drawable.star);
            } else if (i % 3 == 0) {
                iv.setImageResource(R.drawable.colors);
            } else {
                iv.setImageResource(R.drawable.sab);
            }
            mLeftBarLinearLayout.addView(inflatedLayout);
        }


        hsvShows.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = hsvShows.getScrollY(); // For ScrollView
                int scrollX = hsvShows.getScrollX(); // For HorizontalScrollView
                // DO SOMETHING WITH THE SCROLL COORDINATES
                Log.e(scrollX + ",", scrollY + "");
                hsvTimings.scrollTo(scrollX, scrollY);
            }
        });

    }

    private void initialiseViews() {
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mMainVerticalLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
        mLeftBarLinearLayout = (LinearLayout) findViewById(R.id.lifeBarLinearLayout);
        mTimingLinearLayout = (LinearLayout) findViewById(R.id.timingLinearLayout);
        mDateLinearLayout = (LinearLayout) findViewById(R.id.dateLinearLayout);

        hsvShows = (HorizontalScrollView) findViewById(R.id.hsv_shows);
        hsvTimings = (LockableScrollView) findViewById(R.id.hsv_timings);
        mVideoViewBg = (RelativeLayout) findViewById(R.id.view_video_bg);

        ((LockableScrollView) hsvTimings).setScrollingEnabled(false);

        videoview = (VideoView) findViewById(R.id.videoView);
//        videoview.setOnTouchListener(this);

        mVideoViewBg.setOnTouchListener(this);
    }

    private void setUpToolBar() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitleTextColor(Color.DKGRAY);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Jio TV App");
    }

    private void showPopUpWindow() {
        startActivity(new Intent(this, VideoActivity.class));
    }

    @Override
    public void onClick(View view) {
        startVideo();
    }

    public void showFullScreenVideo(View v) {
        mVideoViewBg.setOnTouchListener(null);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mVideoViewBg.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void showSemiScreenVideo() {
        mVideoViewBg.setOnTouchListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) mVideoViewBg.getLayoutParams();
        params.width = (int) (250 * metrics.density);
        params.height = (int) (250 * metrics.density);
        params.leftMargin = 30;
        mVideoViewBg.setLayoutParams(params);
    }

    private void startVideo() {

        mVideoViewBg.setVisibility(View.VISIBLE);


//        startActivity(new Intent(this, FullScreenVideoActivity.class));
        try {
            // Start the MediaController

            MediaController mediacontroller = new MediaController(
                    this);
//            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
//            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                videoview.start();
            }
        });
    }

    private int _xDelta;
    private int _yDelta;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                return true;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);
                return true;
        }
        mMainVerticalLinearLayout.invalidate();
        return false;
    }

    @Override
    public void onBackPressed() {

        if (videoview.isPlaying()) {
            showSemiScreenVideo();
        } else {
            super.onBackPressed();
        }

    }
}
