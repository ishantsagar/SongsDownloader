package com.techjini.training.songsdownloader.com.techjini.jio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.techjini.training.songsdownloader.R;

/**
 * Created by techjini on 14/9/16.
 */
public class JioShowsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jio_shows_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
}
