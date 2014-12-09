package com.example.xander.downloadmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by xander on 14-11-26.
 */
public class FileBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (MainActivity.DOWNLOAD_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show();
        }
    }
}
