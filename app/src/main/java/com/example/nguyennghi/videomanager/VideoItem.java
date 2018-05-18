package com.example.nguyennghi.videomanager;

/**
 * Created by nguyennghi on 5/18/18 10:09 AM
 */
public class VideoItem {
    String path;
    String title;
    String date;
    String duration;

    public VideoItem(String path) {
        this.path = path;
        title = "";
        date = "";
        duration = "";

    }
}
