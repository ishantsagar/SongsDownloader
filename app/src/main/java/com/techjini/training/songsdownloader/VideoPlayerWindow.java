package com.techjini.training.songsdownloader;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.VideoView;

/**
 * Created by techjini on 15/9/16.
 */
public class VideoPlayerWindow extends PopupWindow {

    public VideoPlayerWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public void showAsDropDown(Activity activity, View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        playVideo(activity);
    }

    private void playVideo(final Activity activity) {
        final VideoView videoview = (VideoView) getContentView().findViewById(R.id.videoView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                videoview.requestFocus();
                String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
                videoview.setVideoURI(Uri.parse(VideoURL));
                videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });
                videoview.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        mediaPlayer.start();
                        return false;
                    }
                });
                videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.reset();
                    }
                });
            }
        }, 4000);
    }
}
