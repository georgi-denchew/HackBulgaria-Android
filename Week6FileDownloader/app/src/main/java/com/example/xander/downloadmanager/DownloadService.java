package com.example.xander.downloadmanager;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by xander on 14-11-26.
 */
public class DownloadService extends IntentService {
    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(MainActivity.DOWNLOAD_ACTION);
        URL downloadUrl = null;
        try {
            downloadUrl = new URL(url);

            URLConnection conn = downloadUrl.openConnection();
            InputStream input = conn.getInputStream();
            File pictureDirectory = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            FileOutputStream output = new FileOutputStream(new File(pictureDirectory + "/"
                    + url.substring(url.lastIndexOf("/"))));
            byte data[] = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
            output.close();
            input.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent finishedActionIntent = new Intent();
        finishedActionIntent.setAction(MainActivity.DOWNLOAD_ACTION);
        sendBroadcast(finishedActionIntent);
    }
}
