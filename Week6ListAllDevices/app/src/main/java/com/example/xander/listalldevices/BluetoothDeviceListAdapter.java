package com.example.xander.listalldevices;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;

/**
 * Created by xander on 14-12-9.
 */
public class BluetoothDeviceListAdapter extends ArrayAdapter<String> {

    public BluetoothDeviceListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public void add(String object) {
        super.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(getContext());
        String currentDevice = getItem(position);
        textView.setText(currentDevice);

        return textView;
    }
}
