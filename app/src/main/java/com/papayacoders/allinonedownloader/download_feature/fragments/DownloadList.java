package com.papayacoders.allinonedownloader.download_feature.fragments;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.papayacoders.allinonedownloader.MainActivity;
import com.papayacoders.allinonedownloader.R;

import com.papayacoders.allinonedownloader.VDFragment;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Adapter.CreationAdapter;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Other.VideoModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DownloadList extends VDFragment implements MainActivity.OnBackPressedListener{
    ImageView ivBack;
    LinearLayout lPhotos,lVideos;
    TextView txtPhotos,txtVideos;
    View vBotomPhoto,vBotomVideo;
    RecyclerView rvData;
    private TextView txtNodata;
    private ArrayList<VideoModel> strVideoList = new ArrayList<>();
    private ArrayList<VideoModel> strlistImages = new ArrayList<>();
    private ArrayList<VideoModel> strlistVideo = new ArrayList<>();
    CreationAdapter mCreationAdapter;
    ProgressDialog mPrDialog;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    FrameLayout frameBanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_download_list, container, false);

        init(view);

        GetMediaVideo video = new GetMediaVideo("");
        video.execute();

        return view;
    }


    public void setImageAdapter()
    {
        if (strlistImages.size() == 0) {
            txtNodata.setVisibility(View.VISIBLE);
        } else {
            txtNodata.setVisibility(View.GONE);
            mCreationAdapter = new CreationAdapter(getActivity(), strlistImages);
            rvData.setAdapter(mCreationAdapter);
            rvData.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }
    public void setVideoAdapter()
    {
        if (strlistVideo.size() == 0) {
            txtNodata.setVisibility(View.VISIBLE);
        } else {
            txtNodata.setVisibility(View.GONE);
            mCreationAdapter = new CreationAdapter(getActivity(), strlistVideo);
            rvData.setAdapter(mCreationAdapter);
            rvData.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }
    public void init(View view)
    {
        ivBack=view.findViewById(R.id.ivBack);
        lPhotos=view.findViewById(R.id.lPhotos);
        lVideos=view.findViewById(R.id.lVideos);
        txtPhotos=view.findViewById(R.id.txtPhotos);
        txtVideos=view.findViewById(R.id.txtVideos);
        vBotomPhoto=view.findViewById(R.id.vBotomPhoto);
        vBotomVideo=view.findViewById(R.id.vBotomVideo);
        rvData=view.findViewById(R.id.rvData);
        txtNodata=view.findViewById(R.id.txtNoData);
        mPrDialog = new ProgressDialog(getActivity());
        mPrDialog.setMessage("Getting Data..");
        frameBanner = view.findViewById(R.id.frameBanner);
        loadGoogleAdmobBanner();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPhotos.setTextColor(getResources().getColor(R.color.black));
                txtVideos.setTextColor(getResources().getColor(R.color.unseltxt));
                vBotomPhoto.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
                vBotomVideo.setBackground(getResources().getDrawable(R.drawable.bg_round_white));
                strlistVideo.clear();
                strVideoList.clear();
                GetMediaVideo video = new GetMediaVideo("");
                video.execute();

                setImageAdapter();
            }
        });

        lVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtPhotos.setTextColor(getResources().getColor(R.color.unseltxt));
                txtVideos.setTextColor(getResources().getColor(R.color.black));
                vBotomPhoto.setBackground(getResources().getDrawable(R.drawable.bg_round_white));
                vBotomVideo.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
                strlistImages.clear();
//                GetMediaVideo video = new GetMediaVideo("");
//                video.execute();

                setVideoAdapter();
            }
        });
    }
    public void loadGoogleAdmobBanner() {

        if (frameBanner != null) {
            frameBanner.setVisibility(View.VISIBLE);
            AdView adView = new AdView(getActivity());
            adView.setAdUnitId(getResources().getString(R.string.banner_ID));

            frameBanner.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize(getActivity());
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

    @Override
    public void onBackpressed() {
        getVDActivity().getBrowserManager().unhideCurrentWindow();
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    private class GetMediaVideo extends AsyncTask<String, ArrayList<VideoModel>, ArrayList<VideoModel>> {

        String from;

        public GetMediaVideo(String from) {
            this.from = from;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            strlistImages = new ArrayList<>();
            strlistVideo = new ArrayList<>();
            mPrDialog.show();
        }

        @Override
        protected ArrayList<VideoModel> doInBackground(String... voids) {

            ArrayList<VideoModel> videos = new ArrayList<>();

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), getResources().getString(R.string.app_name));
            if (!file.exists()) {
                return videos;
            }

            File[] files = file.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File ff = files[i];
                    if (ff.getName().toLowerCase().endsWith(".jpg") || ff.getName().toLowerCase().endsWith(".png") &&  !ff.getName().toLowerCase().endsWith(".temp")) {
                        VideoModel model = new VideoModel();
                        model.setVideoPath(ff.getPath());
                        model.setThumbNail(getThumblineImage(ff.getPath()));
                        model.setVideoFile(ff);
                        model.setVideoName(ff.getName());
                        model.setSize(formatFileSize(ff.length()));
                        model.setDate(simpleDateFormat.format(ff.lastModified()));
                        Log.e("DateVideo--)", "" + model.getDate());
                        strlistImages.add(model);
                    }
                    else if (ff.getName().toLowerCase().endsWith(".mp4") )
                    {
                        VideoModel model = new VideoModel();
                        model.setVideoPath(ff.getPath());
                        model.setThumbNail(getThumblineImage(ff.getPath()));
                        model.setVideoFile(ff);
                        model.setVideoName(ff.getName());
                        model.setSize(formatFileSize(ff.length()));
                        model.setDate(simpleDateFormat.format(ff.lastModified()));
                        Log.e("DateVideo--)", "" + model.getDate());
                        strlistVideo.add(model);
                    }
                }
            }
            return videos;
        }

        @Override
        protected void onPostExecute(ArrayList<VideoModel> list) {
            super.onPostExecute(list);

            if (mPrDialog != null && mPrDialog.isShowing()) {
                mPrDialog.dismiss();
            }

            setImageAdapter();
        }


    }
    public Bitmap getThumblineImage(String videoPath) {
        return ThumbnailUtils.createVideoThumbnail(videoPath, MINI_KIND);
    }
    public static String formatFileSize(long size) {
        String sFileSize = "";
        if (size > 0) {
            double dFileSize = (double) size;

            double kiloByte = dFileSize / 1024;
            if (kiloByte < 1 && kiloByte > 0) {
                return size + " Byte";
            }
            double megaByte = kiloByte / 1024;
            if (megaByte < 1) {
                sFileSize = String.format("%.2f", kiloByte);
                return sFileSize + " KB";
            }

            double gigaByte = megaByte / 1024;
            if (gigaByte < 1) {
                sFileSize = String.format("%.2f", megaByte);
                return sFileSize + " MB";
            }

            double teraByte = gigaByte / 1024;
            if (teraByte < 1) {
                sFileSize = String.format("%.2f", gigaByte);
                return sFileSize + " GB";
            }

            sFileSize = String.format("%.2f", teraByte);
            return sFileSize + " TB";
        }
        return "0K";
    }
}