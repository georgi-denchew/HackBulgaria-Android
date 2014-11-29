package com.example.georgi.week6filebrowser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;


public class MyActivity extends Activity {

    private FilesAdapter mFilesAdapter;
    private File mRoot;
    private File mCurrentFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mRoot = Environment.getExternalStorageDirectory();
        List<File> files = new ArrayList<File>();
        files.addAll(Arrays.asList(mRoot.listFiles()));

        mFilesAdapter = new FilesAdapter(this, files);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mFilesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = mFilesAdapter.getItem(position);

                if (file.isDirectory()){
                    mFilesAdapter.clear();
                    mFilesAdapter.addAll(file.listFiles());
                    mFilesAdapter.notifyDataSetChanged();
                    mCurrentFile = file;
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    MimeTypeMap map = MimeTypeMap.getSingleton();
                    String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
                    String type = map.getMimeTypeFromExtension(ext);

                    if (type == null) {
                        type = "*/*";
                    }

                    Uri data = Uri.fromFile(file);

                    intent.setDataAndType(data, type);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (!mCurrentFile.equals(mRoot)){
            File parent = mCurrentFile.getParentFile();
            mFilesAdapter.clear();
            mFilesAdapter.addAll(parent.listFiles());
            mFilesAdapter.notifyDataSetChanged();
            mCurrentFile = parent;
        } else {
            super.onBackPressed();
        }
    }
}
