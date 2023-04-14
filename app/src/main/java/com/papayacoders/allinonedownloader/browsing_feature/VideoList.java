package com.papayacoders.allinonedownloader.browsing_feature;

import android.Manifest;
import android.app.Activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.papayacoders.allinonedownloader.R;
import com.papayacoders.allinonedownloader.VDApp;

import com.papayacoders.allinonedownloader.download_feature.DownloadPermissionHandler;
import com.papayacoders.allinonedownloader.download_feature.DownloadManager1;

import com.papayacoders.allinonedownloader.download_feature.DownloadVideo;
import com.papayacoders.allinonedownloader.download_feature.lists.DownloadQueues;
import com.papayacoders.allinonedownloader.utils.PermissionRequestCodes;
import com.papayacoders.allinonedownloader.utils.RenameDialog;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


public abstract class VideoList {
    private Activity activity;
    private RecyclerView view;
    private List<Video> videos;

    class Video {
        String size;
        String type;
        String link;
        String name;
        String page;
        String website;
        boolean chunked = false, checked = false, expanded = false;
        boolean audio;
    }

    abstract void onItemDeleted();

    abstract void onVideoPlayed(String url);

    VideoList(Activity activity, RecyclerView view) {
        this.activity = activity;
        this.view = view;

        view.setAdapter(new VideoListAdapter());
        view.setLayoutManager(new LinearLayoutManager(activity));
        view.setHasFixedSize(true);

        videos = Collections.synchronizedList(new ArrayList<Video>());
    }

    void recreateVideoList(RecyclerView view) {
        this.view = view;
        view.setAdapter(new VideoListAdapter());
        view.setLayoutManager(new LinearLayoutManager(activity));
        view.setHasFixedSize(true);
    }

    void addItem(String size, String type, String link, String name, String page,
                 boolean chunked, String website, boolean audio) {
        Video video = new Video();
        video.size = size;
        video.type = type;
        video.link = link;
        video.name = name;
        video.page = page;
        video.chunked = chunked;
        video.website = website;
        video.audio = audio;

        if (!audio) {
            boolean duplicate = false;
            for (ListIterator<Video> iterator = videos.listIterator(); iterator.hasNext(); ) {
                Video v = iterator.next();
                if (v.link.equals(video.link)) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                videos.add(video);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        view.getAdapter().notifyDataSetChanged();
                    }
                });
            }

        }
    }

    int getSize() {
        return videos.size();
    }

    void deleteAllItems() {
        for (int i = 0; i < videos.size(); ) {
            videos.remove(i);
        }
        ((VideoListAdapter) view.getAdapter()).expandedItem = -1;
        view.getAdapter().notifyDataSetChanged();
    }

    class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoItem> {
        int expandedItem = -1;

        List getVideos() {
            return videos;
        }


        @Override
        public VideoItem onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            return (new VideoItem(inflater.inflate(R.layout.videos_found_item, parent,
                    false)));
        }

        @Override
        public void onBindViewHolder(VideoItem holder, int position) {
            holder.bind(videos.get(position));
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }

        class VideoItem extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView size;
            TextView name;
            ImageView download;
            ImageView rename;
            ImageView play;

            VideoItem(View itemView) {
                super(itemView);
                size = itemView.findViewById(R.id.videoFoundSize);
                name = itemView.findViewById(R.id.videoFoundName);
                play = itemView.findViewById(R.id.videoFoundPlay);
                play.setOnClickListener(this);
                download = itemView.findViewById(R.id.videoFoundDownload);
                download.setOnClickListener(this);
                rename = itemView.findViewById(R.id.videoFoundRename);
                rename.setOnClickListener(this);
            }

            void bind(Video video) {
                if (video.size != null) {
                    String sizeFormatted = Formatter.formatShortFileSize(activity,
                            Long.parseLong(video.size));
                    size.setText(sizeFormatted);
                } else size.setText(" ");
                name.setText(video.name);
                if (video.website != null) {
                    if (video.website.equals("facebook.com") || video.website.equals("twitter.com") || video.website.equals("instagram.com") || video.website.equals("m.vlive.tv")) {
                        play.setVisibility(View.VISIBLE);
                    } else {
                        play.setVisibility(View.GONE);
                    }
                }

            }


            @Override
            public void onClick(View v) {
                if (v == itemView.findViewById(R.id.videoFoundRename)) {
                    new RenameDialog(activity, name.getText().toString()) {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }

                        @Override
                        public void onOK(String newName) {
                            videos.get(getAdapterPosition()).name = newName;
                            notifyItemChanged(getAdapterPosition());
                        }
                    };
                } else if (v == download) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        try {
                            startDownload();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        new DownloadPermissionHandler(activity) {
                            @Override
                            public void onPermissionGranted() {
                                try {
                                    startDownload();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }.checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                PermissionRequestCodes.DOWNLOADS);
                    } else {
                        try {
                            startDownload();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (v == play) {
                    Video video = videos.get(getAdapterPosition());
                    onVideoPlayed(video.link);
                }
            }

            void startDownload() throws IOException {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Video video = videos.get(getAdapterPosition());


                String url = video.link; // URL of the file to download
                String filename = video.name+".mp4"; // Name of the file to save

                String folderName = "Video Downloader"; // Name of the subfolder
                File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), folderName); // Create the subfolder
                if (!folder.exists()) {
                    folder.mkdir();
                }


                DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                    // Create a new download request
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(video.link));
                    // Set the title of the download
                    request.setTitle(video.name + " Video Download");
                    // Set the description of the download
                    request.setDescription("Downloading video file...");
                    request.allowScanningByMediaScanner();
                    Toast.makeText(activity, "Downloading video file...", Toast.LENGTH_SHORT).show();
                    // Set the destination directory for the downloaded file
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + File.separator + folderName  , "madh.mp4");
                    String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + folderName + "/" + filename;
                    request.setDestinationUri(Uri.parse("file://" + destination));
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    downloadManager.enqueue(request);


                } else {
                    Video video = videos.get(getAdapterPosition());
                    DownloadQueues queues = DownloadQueues.load(activity);
                    queues.insertToTop(video.size, video.type, video.link, video.name, video
                            .page, video.chunked, video.website);
                    queues.save(activity);
                    DownloadVideo topVideo = queues.getTopVideo();
                    Intent downloadService = VDApp.getInstance().getDownloadService();
//                  VDApp.getInstance().stopService(downloadService);
//                 DownloadManager.stopThread();
                    DownloadManager1.stop();
                    downloadService.putExtra("link", topVideo.link);
                    downloadService.putExtra("name", topVideo.name);
                    downloadService.putExtra("type", topVideo.type);
                    downloadService.putExtra("size", topVideo.size);
                    downloadService.putExtra("page", topVideo.page);
                    downloadService.putExtra("chunked", topVideo.chunked);
                    downloadService.putExtra("website", topVideo.website);
                    VDApp.getInstance().startService(downloadService);
                    videos.remove(getAdapterPosition());
                    expandedItem = -1;
                    notifyDataSetChanged();
                    onItemDeleted();
                    Toast.makeText(activity, "Downloading video in the background. Check the " +
                            "Downloads panel", Toast.LENGTH_LONG).show();
                }

            }

        }
    }

}
