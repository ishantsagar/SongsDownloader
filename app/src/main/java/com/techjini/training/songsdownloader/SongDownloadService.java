package com.techjini.training.songsdownloader;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by techjini on 14/9/16.
 */
public class SongDownloadService extends IntentService {

    public static final int UPDATE_PROGRESS = 8344;
    private final String TAG = "SongDownloadService.class";

    public SongDownloadService() {
        super("SongDownloadService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intent.putExtra("startId", startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        downloadSong(intent);
    }

    private void downloadSong(Intent intent) {
        String urlToDownload = intent.getStringExtra("url");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {
            Log.e(TAG, intent.getStringExtra("startIdFlag") + "  -- " + intent.getStringExtra("startIdVal"));
            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();

            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + getString(R.string.song_path));

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress", (int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);
        resultData.putInt("id", intent.getIntExtra("id", 0));
        receiver.send(UPDATE_PROGRESS, resultData);
        stopSelf(intent.getIntExtra("startId", -1));
    }

}
