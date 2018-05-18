package com.example.nguyennghi.videomanager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab =  findViewById (R.id.fab);
        ListView listView = findViewById(R.id.listview);
        ArrayList<VideoItem> videoItems = new ArrayList<>();

        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(MainActivity.this, Download.class);
                startActivity(intent);
            }
        });

        ArrayList<String> videos = new ArrayList<>();
        findVideos(new File("/mnt/sdcard"), videos);

        for (String name: videos)
        {
           VideoItem item = new VideoItem(name);
           videoItems.add(item);
        }

        VideoAdapter videoAdapter = new VideoAdapter(this, 1, 1,videoItems);
        listView.setAdapter(videoAdapter);
    }


    void findVideos(File dir, ArrayList<String> list){
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) findVideos(file, list);
            else if(file.getAbsolutePath().contains(".mp4")) list.add(file.getAbsolutePath());
        }
    }

}
