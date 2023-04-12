package com.papayacoders.allinonedownloader.WhatsappDownloader.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.papayacoders.allinonedownloader.R;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Adapter.ImageAdapter;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Adapter.VideoAdapter;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Other.Const;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Other.FileListClickInterface;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Other.Utils;
import com.papayacoders.allinonedownloader.WhatsappDownloader.model.StoryModel;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class WhatsappActivity extends AppCompatActivity implements FileListClickInterface {

    private ImageView ivBack, ivSetting, ivCreation, ivIcon;
    private LinearLayout lPhotos, lVideos, btnAllow, btnCancel;
    private TextView txtPhotos, txtVideos, txtNoData, txtToolName;
    RecyclerView rvData;
    Dialog dailogPermission;
    private View vBotomPhoto, vBotomVideo;
    ProgressDialog progressDialog;
    private ArrayList<Uri> fileArrayImages = new ArrayList<>();
    private ArrayList<Uri> fileArrayVideos = new ArrayList<>();
    private File[] allFilePath;
    ImageAdapter mImageAdapter;
    VideoAdapter mVideoAdapter;
    String which, strQpath, strNormalPath;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getResources().getDrawable(R.drawable.bg_status);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
        setContentView(R.layout.activity_whatsapp);

        Intent intent = getIntent();
        which = intent.getStringExtra("which");
        init();
        setDailog();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {

            if (which.matches("WA")) {
                int size = getContentResolver().getPersistedUriPermissions().size();
                if (size > 0) {

                    String path = getContentResolver().getPersistedUriPermissions().get(0).getUri().getPath();

                    String path2 = "shubh" ;

                    if (size > 1){
                        path2 = getContentResolver().getPersistedUriPermissions().get(1).getUri().getPath();
                    }

                    if (path.contains("com.whatsapp/WhatsApp") || path2.contains("com.whatsapp/WhatsApp")) {
                        if (dailogPermission != null && dailogPermission.isShowing()) {
                            dailogPermission.dismiss();
                        }
                        progressDialog.show();
                        new LoadAllDataQImages().execute();

                    }else {
                        if (dailogPermission != null) {
                            dailogPermission.show();
                        }
                    }
                } else {
                    if (dailogPermission != null) {
                        dailogPermission.show();
                    }
                }
            } else {
                int size = getContentResolver().getPersistedUriPermissions().size();

                if (size > 0) {
                    String path = getContentResolver().getPersistedUriPermissions().get(0).getUri().getPath();
                    String path2 = "shubh" ;

                    if (size > 1){
                        path2 = getContentResolver().getPersistedUriPermissions().get(1).getUri().getPath();
                    }


                    if (path.contains("Business") || path2.contains("Business")) {
                        if (dailogPermission != null && dailogPermission.isShowing()) {
                            dailogPermission.dismiss();
                        }
                        progressDialog.show();
                        new LoadAllDataQImages().execute();
                    } else {
                        if (dailogPermission != null) {
                            dailogPermission.show();
                        }
                    }
                } else {
                    if (dailogPermission != null) {
                        dailogPermission.show();
                    }
                }
            }

        } else {
            progressDialog.show();
            new LoadAllImageData().execute();
        }


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        ivSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(WhatsappActivity.this, SettingActivity.class));
//            }
//        });

        ivCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(WhatsappActivity.this, CreationActivity.class));
            }
        });

        lPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtPhotos.setTextColor(getResources().getColor(R.color.black));
                txtVideos.setTextColor(getResources().getColor(R.color.unseltxt));
                vBotomPhoto.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
                vBotomVideo.setBackground(getResources().getDrawable(R.drawable.bg_round_white));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    if (getContentResolver().getPersistedUriPermissions().size() > 0) {
                        if (dailogPermission != null && dailogPermission.isShowing()) {
                            dailogPermission.dismiss();
                        }

                        progressDialog.show();
                        new LoadAllDataQImages().execute();

                    } else {
                        if (dailogPermission != null) {
                            dailogPermission.show();
                        }
                    }
                } else {
                    progressDialog.show();
                    new LoadAllImageData().execute();
                }


            }
        });

        lVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPhotos.setTextColor(getResources().getColor(R.color.unseltxt));
                txtVideos.setTextColor(getResources().getColor(R.color.black));
                vBotomPhoto.setBackground(getResources().getDrawable(R.drawable.bg_round_white));
                vBotomVideo.setBackground(getResources().getDrawable(R.drawable.bg_round_green));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    if (dailogPermission != null && dailogPermission.isShowing()) {
                        dailogPermission.dismiss();
                    }

                    progressDialog.show();
                    new LoadAllDataQVideos().execute();
                } else {
                    progressDialog.show();
                    new LoadAllVideoData().execute();
                }


            }
        });
    }

    private void setDailog() {

        dailogPermission = new Dialog(WhatsappActivity.this, R.style.WideDialog);
        dailogPermission.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dailogPermission.setCanceledOnTouchOutside(false);
        dailogPermission.setCancelable(false);
        dailogPermission.setContentView(R.layout.dialog_permission);
        dailogPermission.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        progressDialog = new ProgressDialog(WhatsappActivity.this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading Status. Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);

        btnAllow = dailogPermission.findViewById(R.id.btnAllow);
        btnCancel = dailogPermission.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(view -> {
            if (dailogPermission != null && dailogPermission.isShowing()) {
                dailogPermission.dismiss();
            }
        });

        btnAllow.setOnClickListener(view -> {
            try {

                if (dailogPermission != null && dailogPermission.isShowing()) {
                    dailogPermission.dismiss();
                }

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    StorageManager sm = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
                    Intent intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                    String startDir = strQpath;
                    Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");
                    String scheme = uri.toString();
                    scheme = scheme.replace("/root/", "/document/");
                    scheme += "%3A" + startDir;
                    uri = Uri.parse(scheme);
                    intent.putExtra("android.provider.extra.INITIAL_URI", uri);
                    startActivityForResult(intent, 2001);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void init() {
        utils = new Utils(WhatsappActivity.this);
        ivBack = findViewById(R.id.ivBack);
        ivSetting = findViewById(R.id.ivSetting);
        ivCreation = findViewById(R.id.ivCreation);
        lPhotos = findViewById(R.id.lPhotos);
        lVideos = findViewById(R.id.lVideos);
        txtPhotos = findViewById(R.id.txtPhotos);
        rvData = findViewById(R.id.rvData);
        txtVideos = findViewById(R.id.txtVideos);
        txtNoData = findViewById(R.id.txtNoData);
        vBotomPhoto = findViewById(R.id.vBotomPhoto);
        vBotomVideo = findViewById(R.id.vBotomVideo);
        ivIcon = findViewById(R.id.ivIcon);
        txtToolName = findViewById(R.id.txtToolName);


        if (which.matches("WA")) {
            strQpath = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses";
            strNormalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";

            ivIcon.setImageDrawable(getResources().getDrawable(R.drawable.iv_wp_shadow));
            txtToolName.setText(getResources().getString(R.string.titleWhatsapp));
        } else {
            strQpath = "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses";
            strNormalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses";


            ivIcon.setImageDrawable(getResources().getDrawable(R.drawable.iv_wb_shadow));
            txtToolName.setText(getResources().getString(R.string.titleWB));
        }
    }

    public void setImageAdapter() {

        if (Build.VERSION.SDK_INT >= 30) {

            if (fileArrayImages.size() == 0) {
                txtNoData.setVisibility(View.VISIBLE);
            } else {
                mImageAdapter = new ImageAdapter(WhatsappActivity.this, fileArrayImages, WhatsappActivity.this);
                txtNoData.setVisibility(View.GONE);
                rvData.setAdapter(mImageAdapter);
                rvData.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            }
        }else {

            if (which.matches("WA")) {
                if (fileArrayImages.size() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else {
                    txtNoData.setVisibility(View.GONE);
                    mImageAdapter = new ImageAdapter(WhatsappActivity.this, getWAData(), WhatsappActivity.this);
                    rvData.setAdapter(mImageAdapter);
                    rvData.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                }
            } else {
                if (fileArrayImages.size() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else {
                    txtNoData.setVisibility(View.GONE);
                    mImageAdapter = new ImageAdapter(WhatsappActivity.this, getWBData(), WhatsappActivity.this);
                    rvData.setAdapter(mImageAdapter);
                    rvData.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                }
            }

        }
    }

    public void setVideoAdapter() {
        mVideoAdapter = new VideoAdapter(WhatsappActivity.this, fileArrayVideos, WhatsappActivity.this);
        if (fileArrayVideos.size() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else {
            txtNoData.setVisibility(View.GONE);
            rvData.setAdapter(mVideoAdapter);
            rvData.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2001 && resultCode == RESULT_OK) {
                Uri dataUri = data.getData();

                if (which.matches("WA")) {
                    utils.setWAUri(dataUri.toString());
                } else {
                    utils.setWBUri(dataUri.toString());
                }
                if (dataUri.toString().contains(".Statuses")) {
                    getContentResolver().takePersistableUriPermission(dataUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    progressDialog.show();
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        new LoadAllDataQImages().execute();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.txtWrongfolder), Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void getImagePosition(int position) {

        String path = fileArrayImages.get(position).toString();

        Log.e("pathImage--)", "" + path);
        Intent intent = new Intent(WhatsappActivity.this, PreviewActivity.class);
        intent.putExtra("preview", path);
        intent.putExtra("filePath", fileArrayImages.get(position).getPath());
        startActivity(intent);
    }

    @Override
    public void getVideoPosition(int position) {
        String path = fileArrayVideos.get(position).toString();
        Log.e("pathVideo--)", "" + path);
        Intent intent = new Intent(WhatsappActivity.this, PreviewActivity.class);
        intent.putExtra("preview", path);
        intent.putExtra("filePath", fileArrayVideos.get(position).getPath());
        startActivity(intent);
    }


    class LoadAllDataQImages extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            fileArrayImages = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... furl) {
            DocumentFile documentFile;
            try {
                if (which.matches("WA")) {
                    Uri uriParse = Uri.parse(utils.getWAUri());
                    documentFile = DocumentFile.fromTreeUri(WhatsappActivity.this, uriParse);
                } else {
                    Uri uriParse = Uri.parse(utils.getWBUri());
                    documentFile = DocumentFile.fromTreeUri(WhatsappActivity.this, uriParse);
                }

                for (DocumentFile file : documentFile.listFiles()) {
                    if (file.isDirectory()) {

                    } else {
                        if (!file.getName().equals(".nomedia")) {
                            if (file.getUri().toString().endsWith(".png") || file.getUri().toString().endsWith(".jpg")) {
                                fileArrayImages.add(file.getUri());
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String fileUrl) {
            progressDialog.dismiss();
            setImageAdapter();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }
    }

    class LoadAllDataQVideos extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            fileArrayVideos = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... furl) {
            fileArrayVideos.clear();
            DocumentFile documentFile = DocumentFile.fromTreeUri(WhatsappActivity.this, getContentResolver().getPersistedUriPermissions().get(0).getUri());
            for (DocumentFile file : documentFile.listFiles()) {
                if (file.isDirectory()) {

                } else {
                    if (!file.getName().equals(".nomedia")) {

                        if (file.getUri().toString().endsWith(".mp4")) {
                            fileArrayVideos.add(file.getUri());
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String fileUrl) {
            progressDialog.dismiss();
            setVideoAdapter();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }
    }

    class LoadAllImageData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String targetPath = strNormalPath;

            File targetDirector = new File(targetPath);
            allFilePath = targetDirector.listFiles();
        }

        @Override
        protected String doInBackground(String... furl) {


            try {
//                Arrays.sort(allFilePath, (Comparator) (o1, o2) -> {
//                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
//                        return -1;
//                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
//                        return +1;
//                    } else {
//                        return 0;
//                    }
//                });

                for (int i = 0; i < allFilePath.length; i++) {
                    File file = allFilePath[i];
                    if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                        fileArrayImages.add(Uri.fromFile(file));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String fileUrl) {
            progressDialog.dismiss();
            setImageAdapter();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }
    }

    class LoadAllVideoData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String targetPath = strNormalPath;

            File targetDirector = new File(targetPath);
            allFilePath = targetDirector.listFiles();
        }

        @Override
        protected String doInBackground(String... furl) {
            fileArrayVideos.clear();


            try {
                Arrays.sort(allFilePath, (Comparator) (o1, o2) -> {
                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                        return -1;
                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                });

                for (int i = 0; i < allFilePath.length; i++) {
                    File file = allFilePath[i];
                    if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                        fileArrayVideos.add(Uri.fromFile(file));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(String fileUrl) {
            progressDialog.dismiss();
            setVideoAdapter();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }
    }


    // below android  30
    private File[] files;

    private ArrayList<Uri> getWAData() {
        fileArrayImages.clear();

        StoryModel f;

        String mainWP_B = Environment.getExternalStorageDirectory().getAbsolutePath() + Const.FOLDER_NAME + "Media/.Statuses";
        String mainWP_B_11 = Environment.getExternalStorageDirectory().getAbsolutePath() + Const.FOLDER_NAME_Whatsapp_and11 + "Media/.Statuses";

        File targetDirector3 = new File(mainWP_B);
        File targetDirector4 = new File(mainWP_B_11);

        ArrayList<File> aList = new ArrayList<File>(Arrays.asList(targetDirector3.listFiles() != null ? targetDirector3.listFiles() : new File[]{new File("")}));
        aList.addAll(Arrays.asList(targetDirector4.listFiles() != null ? targetDirector4.listFiles() : new File[]{new File("")}));

        File[] n = new File[aList.size()];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Arrays.setAll(n, aList::get);
            files = n;
        } else {
            files = targetDirector3.listFiles();
        }

        try {
            try {
                Arrays.sort(files, new Comparator() {
                    public int compare(Object o1, Object o2) {

                        if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                            return -1;
                        } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                            return +1;
                        } else {
                            return 0;
                        }

                    }

                });
            } catch(Exception e) {
                Log.d("SHUBH", "getData: "+e.getMessage());
            }

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                f = new StoryModel();
                f.setName("Download");
                f.setUri(Uri.fromFile(file));
                f.setPath(files[i].getAbsolutePath());
                f.setFilename(file.getName());
                String tt = file.getAbsolutePath();
                f.setPath(tt);

                if (!file.getName().equals(".nomedia") && !file.getPath().equals("")) {
                    if (file.toString().endsWith(".jpg")) {
                        fileArrayImages.add(f.getUri());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileArrayImages;
    }

    private ArrayList<Uri> getWBData() {
        fileArrayImages.clear();

        StoryModel f;

        String mainWP_B = Environment.getExternalStorageDirectory().getAbsolutePath() + Const.FOLDER_NAME_Whatsappbusiness + "Media/.Statuses";
        String mainWP_B_11 = Environment.getExternalStorageDirectory().getAbsolutePath() + Const.FOLDER_NAME_Whatsapp_and11_B + "Media/.Statuses";

        File targetDirector3 = new File(mainWP_B);
        File targetDirector4 = new File(mainWP_B_11);

        ArrayList<File> aList = new ArrayList<File>(Arrays.asList(targetDirector3.listFiles() != null ? targetDirector3.listFiles() : new File[]{new File("")}));
        aList.addAll(Arrays.asList(targetDirector4.listFiles() != null ? targetDirector4.listFiles() : new File[]{new File("")}));

        File[] n = new File[aList.size()];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Arrays.setAll(n, aList::get);
            files = n;
        } else {
            files = targetDirector3.listFiles();
        }

        try {
            try {
                Arrays.sort(files, new Comparator() {
                    public int compare(Object o1, Object o2) {

                        if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                            return -1;
                        } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                            return +1;
                        } else {
                            return 0;
                        }

                    }

                });
            } catch(Exception e) {
                Log.d("SHUBH", "getData: "+e.getMessage());
            }

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                f = new StoryModel();
                f.setName("Download");
                f.setUri(Uri.fromFile(file));
                f.setPath(files[i].getAbsolutePath());
                f.setFilename(file.getName());
                String tt = file.getAbsolutePath();
                f.setPath(tt);

                if (!file.getName().equals(".nomedia") && !file.getPath().equals("")) {
                    if (file.toString().endsWith(".jpg")) {
                        fileArrayImages.add(f.getUri());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileArrayImages;
    }


}