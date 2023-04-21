package com.papayacoders.allinonedownloader.WhatsappDownloader.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.FullScreenContentCallback;


import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.common.ConnectionResult;

import com.papayacoders.allinonedownloader.MainActivity;
import com.papayacoders.allinonedownloader.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class PreviewActivity extends AppCompatActivity {

    private ImageView ivBack, ivPreviewImage, ivPlay;
    private LinearLayout lWhatsapp, lDownload, lShare;
    String previewUrl, outPutSave;
    String fileName = null;
    Boolean isPause = false;
    ProgressDialog mPrDialog;
    String actionType = "Download", contentType = null;
    InterstitialAd mInterstitialAd;
    FrameLayout frameBanner;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private MediaSource mediaSource;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private int position = 0;
    private  ImageView downloads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        Drawable background = getResources().getDrawable(R.drawable.bg_status);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);
        setContentView(R.layout.activity_preview);

        Intent intent = getIntent();
        previewUrl = intent.getStringExtra("preview");

        playerView = findViewById(R.id.video_view);
        downloads = findViewById(R.id.ivCreation);
        mediaSource = buildMediaSource(Uri.parse(previewUrl));
        init();

        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PreviewActivity.this, "Opening Downloads", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.putExtra("type",1);
                startActivity(intent1);
            }
        });

        ivBack.setOnClickListener(view -> finish());

//        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

    public File createDirectory() {
        File rootPath = new File(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), getResources().getString(R.string.app_name)).toString());
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        return rootPath;
    }

    public void init() {
        ivBack = findViewById(R.id.ivBack);
        ivPreviewImage = findViewById(R.id.ivPreviewImage);
        ivPlay = findViewById(R.id.ivPlay);
        lWhatsapp = findViewById(R.id.lWhatsapp);
        lDownload = findViewById(R.id.lDownload);
        lShare = findViewById(R.id.lShare);
        frameBanner = findViewById(R.id.frameBanner);
        mPrDialog = new ProgressDialog(PreviewActivity.this);
        mPrDialog.setMessage("Please Wait..");
        Uri uriParse = Uri.parse(previewUrl);
        loadGoogleAdmobBanner();
        InterstitialAd.load(this, getString(R.string.interstitial_ID), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;

            }
        });
        Log.e("previewUrl--)", "" + previewUrl + "--)" + uriParse);


        Glide.with(PreviewActivity.this)
                .load(uriParse)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(ivPreviewImage);


        if (previewUrl.endsWith(".mp4")) {
            contentType = "VIDEO";
            ivPlay.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.VISIBLE);

        } else {
            contentType = "IMAGE";
            ivPlay.setVisibility(View.GONE);
            playerView.setVisibility(View.GONE);

        }

        playerView.setOnClickListener(v -> {
//            if (isPause){
//                Toast.makeText(this, "paused", Toast.LENGTH_SHORT).show();
//                startPlayer();
//
//            }else
//                Toast.makeText(this, "resume", Toast.LENGTH_SHORT).show();
//                pausePlayer();
        });

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                if (isPause){
//                    pausePlayer();
//                }else
//                    startPlayer();
//                try {
//
//                    ivPlay.setVisibility(View.GONE);
//                    ivPreviewImage.setVisibility(View.GONE);
//                    videoView.setVisibility(View.VISIBLE);
//
//                    videoView.setVideoPath(uriParse.getPath());
//                    videoView.start();
//                    MediaController mediaController = new MediaController(PreviewActivity.this);
//                    videoView.setMediaController(mediaController);
//                    try {
//                        videoView.setOnPreparedListener(mp -> {
//                            videoView.seekTo(position);
//                            if (position == 0) {
//                                videoView.start();
//                            } else {
//                                videoView.resume();
//
//                            }
//                        });
//                    } catch (Exception ex) {}
//
//                } catch (Exception e) {}
            }
        });
        lDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                actionType = "Download";

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(PreviewActivity.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            downloadData();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            downloadData();
                        }



                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd = null;
                        }
                    });

                } else {
                    downloadData();
                }


            }
        });

        lShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionType = "Share";
                downloadData();

            }
        });

        lWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionType = "WhatShare";
                downloadData();
            }
        });

    }

    public void shareVia(String type, String path) {


        Uri imageUri = Uri.parse(path);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Download App now \n https://play.google.com/store/apps/details?id=" + getPackageName());
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType(type);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share Via"));


    }


    public void loadGoogleAdmobBanner() {

        if (frameBanner != null) {
            frameBanner.setVisibility(View.VISIBLE);
            AdView adView = new AdView(PreviewActivity.this);
            adView.setAdUnitId(getResources().getString(R.string.banner_ID));

            frameBanner.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize(PreviewActivity.this);
            adView.setAdSize(adSize);
            adView.loadAd(adRequest);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);

                }
            });
        }
    }

    private AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public void downloadData() {
        createDirectory();

        String saveFilePath = createDirectory() + File.separator;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            try {
                if (previewUrl.toString().endsWith(".mp4")) {
                    fileName = "STATUS_" + System.currentTimeMillis() + ".mp4";
                    new DownloadFileTask().execute(previewUrl);
                } else {
                    fileName = "STATUS_" + System.currentTimeMillis() + ".png";
                    new DownloadFileTask().execute(previewUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (actionType.matches("Share")) {
                if (previewUrl.toString().endsWith(".mp4")) {
                    shareVia("video/mp4", getIntent().getStringExtra("filePath"));

                } else {
                    shareVia("image/jpg", getIntent().getStringExtra("filePath"));

                }

            } else if (actionType.matches("Download")) {
                copyFileOrDirectory(getIntent().getStringExtra("filePath"), "/storage/emulated/0/Download/WhatsSaver/");

            } else if (actionType.matches("WhatShare")) {
                if (previewUrl.toString().endsWith(".mp4")) {
                    shareViaWhatsApp("image/mp4", getIntent().getStringExtra("filePath"), "com.whatsapp");

                } else {
                    shareViaWhatsApp("image/jpg", getIntent().getStringExtra("filePath"), "com.whatsapp");

                }
            }
//
//            shareVia("video/mp4", getIntent().getStringExtra("filePath"));
//
//
//
//
//            final String path = previewUrl;
//            String filename = path.substring(path.lastIndexOf("/") + 1);
//            final File file = new File(path);
//            File destFile = new File(saveFilePath);
//            try {
//                FileUtils.copyFileToDirectory(file, destFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String fileNameChange = filename.substring(12);
//            File newFile = new File(saveFilePath + fileNameChange);
//            String contentType = "image/*";
//            if (previewUrl.endsWith(".mp4")) {
//                contentType = "video/*";
//            } else {
//                contentType = "image/*";
//            }
//            MediaScannerConnection.scanFile(PreviewActivity.this, new String[]{newFile.getAbsolutePath()}, new String[]{contentType},
//                    new MediaScannerConnection.MediaScannerConnectionClient() {
//                        public void onMediaScannerConnected() {
//                            //NA
//                        }
//
//                        public void onScanCompleted(String path, Uri uri) {
//                            //NA
//                        }
//                    });
//
//            File from = new File(saveFilePath, filename);
//            File to = new File(saveFilePath, fileNameChange);
//            from.renameTo(to);
//
//            outPutSave = newFile.getAbsolutePath();

//
//            if (actionType.matches("Download")) {
//                Toast.makeText(PreviewActivity.this, "Saved SuccessFully", Toast.LENGTH_LONG).show();
//            } else if (actionType.matches("Share")) {
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= 24) {
//                    uri = FileProvider.getUriForFile(PreviewActivity.this, getPackageName(), new File(outPutSave));
//                } else {
//                    uri = Uri.fromFile(new File(outPutSave));
//                }
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                if (contentType == "IMAGE") {
//                    intent.setType("image/*");
//                } else if (contentType == "VIDEO") {
//                    intent.setType("video/*");
//                }
//                intent.putExtra(Intent.EXTRA_STREAM, uri);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                StringBuilder sb4 = new StringBuilder();
//                sb4.append(getResources().getString(R.string.app_name) + " " + getResources().getString(R.string.Created_By_));
//                sb4.append("https://play.google.com/store/apps/details?id=" + getPackageName());
//                intent.putExtra(Intent.EXTRA_TEXT, sb4.toString());
//                startActivity(Intent.createChooser(intent, getResources().getString(R.string.Share_With)));
//
//            } else if (actionType.matches("WhatShare")) {
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= 24) {
//                    uri = FileProvider.getUriForFile(PreviewActivity.this, getPackageName(), new File(outPutSave));
//                } else {
//                    uri = Uri.fromFile(new File(outPutSave));
//                }
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                if (contentType.matches("IMAGE")) {
//                    intent.setType("image/*");
//                } else if (contentType.matches("VIDEO")) {
//                    intent.setType("video/*");
//                }
//                intent.putExtra(Intent.EXTRA_STREAM, uri);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.setPackage("com.whatsapp");
//                try {
//                    startActivity(intent);
//                } catch (ActivityNotFoundException unused) {
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Msg_WhatsApp_Not_Installed), Toast.LENGTH_LONG).show();
//                }
//            }

        }


    }

    public void shareViaWhatsApp(String type, String path, String package_name) {

        try {


            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType(type);
            File fileToShare = new File(path);
            Uri uri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", fileToShare);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        } catch (Exception e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }


    }


    public void copyFileOrDirectory(String srcDir, String dstDir) {


        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            String pathName = dstDir+src.getName();
            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);
                }
            } else {

                copyFile(src, dst, pathName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void copyFile(File sourceFile, File destFile, String pathName) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                //change mCurrentPhotoPath for your imagepath
                File f = new File(pathName);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

            Toast.makeText(this, "Saved....", Toast.LENGTH_SHORT).show();
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }


    class DownloadFileTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            mPrDialog.show();
        }

        @Override
        protected String doInBackground(String... furl) {
            try {

                InputStream in = getContentResolver().openInputStream(Uri.parse(furl[0]));
                File f = null;
                File rootPath = new File(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), getResources().getString(R.string.app_name)).toString());

                f = new File(rootPath + File.separator + fileName);
                outPutSave = f.getAbsolutePath();
                f.setWritable(true, false);
                OutputStream outputStream = new FileOutputStream(f);
                byte buffer[] = new byte[1024];
                int length = 0;

                while ((length = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                in.close();
            } catch (IOException e) {

                Log.e("Exception--)", "" + e.getMessage());
                System.out.println("error in creating a file");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String fileUrl) {

            try {
                if (mPrDialog != null && mPrDialog.isShowing()) {
                    mPrDialog.dismiss();
                }


                if (Build.VERSION.SDK_INT >= 19) {
                    MediaScannerConnection.scanFile(PreviewActivity.this, new String[]
                                    {new File(outPutSave).getAbsolutePath()},
                            null, (path, uri) -> {
                                //no action
                            });
                } else {
                    sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED",
                            Uri.fromFile(new File(outPutSave))));
                }


                Log.e("outPutSave--)", "" + outPutSave);
                if (actionType.matches("Download")) {
                    Toast.makeText(getApplicationContext(), "Download Complated", Toast.LENGTH_SHORT).show();
                } else if (actionType.matches("Share")) {
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(PreviewActivity.this, getPackageName() + ".fileprovider", new File(outPutSave));
                    } else {
                        uri = Uri.fromFile(new File(outPutSave));
                    }

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    if (contentType == "IMAGE") {
                        intent.setType("image/*");
                    } else if (contentType == "VIDEO") {
                        intent.setType("video/*");
                    }
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(getResources().getString(R.string.app_name) + " " + getResources().getString(R.string.Created_By_));
                    sb4.append("https://play.google.com/store/apps/details?id=" + getPackageName());
                    intent.putExtra(Intent.EXTRA_TEXT, sb4.toString());
                    startActivity(Intent.createChooser(intent, getResources().getString(R.string.Share_With)));

                } else if (actionType.matches("WhatShare")) {
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(PreviewActivity.this, getPackageName() + ".fileprovider", new File(outPutSave));
                    } else {
                        uri = Uri.fromFile(new File(outPutSave));
                    }

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    if (contentType.matches("IMAGE")) {
                        intent.setType("image/*");
                    } else if (contentType.matches("VIDEO")) {
                        intent.setType("video/*");
                    }
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setPackage("com.whatsapp");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Msg_WhatsApp_Not_Installed), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {

                if (mPrDialog != null && mPrDialog.isShowing()) {
                    mPrDialog.dismiss();
                }

                Log.e("Exception1--)", "" + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    // Zoom img
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        scaleGestureDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }

//    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            FACTOR *= detector.getScaleFactor();
//            FACTOR = Math.max(0.1f, Math.min(FACTOR, 10.0f));
//            ivPreviewImage.setScaleX(FACTOR);
//            ivPreviewImage.setScaleY(FACTOR);
//            return true;
//        }
//    }

    // vid
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("Position", videoView.getCurrentPosition());
//        videoView.pause();
//
//    }
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        position = savedInstanceState.getInt("Position");
//        videoView.seekTo(position);
//    }

    private boolean firstcall = true;

    public Boolean playing;

    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            releaseplayer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    private void releaseplayer() {
        try {
            SimpleExoPlayer simpleExoPlayer = this.player;
            if (simpleExoPlayer != null) {
                simpleExoPlayer.release();
                this.player = null;
                //            this.trackSelector = null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }



    private void pausePlayer() {
        isPause = true;
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }

    private void startPlayer() {
        isPause = false;
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ProgressiveMediaSource.Factory(
                new DefaultDataSourceFactory(this, "exoplayer-demo"))
                .createMediaSource(uri);
    }
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource);
    }
    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }
}