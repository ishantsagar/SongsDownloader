package com.techjini.training.songsdownloader;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;

/**
 * Created by techjini on 16/9/16.
 */
public class FullScreenVideoActivity extends Activity {

    FullscreenVideoLayout videoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        videoLayout = (FullscreenVideoLayout) findViewById(R.id.videoview);
        videoLayout.setActivity(this);

        Uri videoUri = Uri.parse("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4");
        try {
            videoLayout.setVideoURI(videoUri);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
