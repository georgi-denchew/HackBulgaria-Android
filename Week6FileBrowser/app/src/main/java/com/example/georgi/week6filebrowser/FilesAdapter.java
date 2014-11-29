package com.example.georgi.week6filebrowser;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by Georgi on 29.11.2014 г..
 */

// typeId for styles
public class FilesAdapter extends ArrayAdapter<File> {

    public FilesAdapter(Context context, List<File> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        File file = getItem(position);

        TextView textView = new TextView(getContext());

        if (file.isDirectory()){
            textView.setTextColor(Color.YELLOW);
        }

        textView.setText(file.getAbsolutePath());

        return textView;
    }
}
