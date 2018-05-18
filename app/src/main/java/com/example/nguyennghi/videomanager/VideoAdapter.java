package com.example.nguyennghi.videomanager;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nguyennghi on 5/18/18 10:07 AM
 */

public class VideoAdapter extends ArrayAdapter<VideoItem> {
    Context context;
    ArrayList<VideoItem> videoItems;
    public VideoAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<VideoItem> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.videoItems = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_video_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgThumbnail = convertView.findViewById(R.id.imgThumbnail);
            viewHolder.txtTitle = convertView.findViewById(R.id.txtTitle);
            viewHolder.txtDate = convertView.findViewById(R.id.txtDate);
            viewHolder.txtDuration = convertView.findViewById(R.id.txtDuration);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final VideoItem videoItem = videoItems.get(position);

        MediaPlayer mp = MediaPlayer.create(context, Uri.parse(videoItem.path));

        int duration = mp.getDuration();

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoItem.path);
        String date = (retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE));
        retriever.release();


        Bitmap bmThumbnail;
        bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoItem.path, MediaStore.Video.Thumbnails.MINI_KIND);
        viewHolder.imgThumbnail.setImageBitmap(bmThumbnail);
        File file = new File(videoItem.path);
        viewHolder.txtTitle.setText(file.getName());
        viewHolder.txtDate.setText(date);
        viewHolder.txtDuration.setText(String.valueOf(duration));

        viewHolder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoItem.path));
                intent.setDataAndType(Uri.parse(videoItem.path), "video/mp4");
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

class ViewHolder{
    ImageView imgThumbnail;
    TextView txtTitle;
    TextView txtDate;
    TextView txtDuration;

}
