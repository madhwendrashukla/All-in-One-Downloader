package com.papayacoders.allinonedownloader.WhatsappDownloader.Activity.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.papayacoders.allinonedownloader.R;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Activity.PreviewActivity;
import com.papayacoders.allinonedownloader.WhatsappDownloader.Activity.WhatsappActivity;
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


public class ImageFragment extends Fragment implements FileListClickInterface {
    private TextView txtNoData;
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
    GestureDetector gestureDetector;
    private LinearLayout lPhotos, lVideos, btnAllow, btnCancel;
    Utils utils;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_image, container, false);
        which = getActivity().getIntent().getStringExtra("which");
        init(view);
        setDailog();


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            if (getContext().getContentResolver().getPersistedUriPermissions().size() > 0) {
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


        return view;
    }


    private void setDailog() {

        dailogPermission = new Dialog(getContext(), R.style.WideDialog);
        dailogPermission.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dailogPermission.setCanceledOnTouchOutside(false);
        dailogPermission.setCancelable(false);
        dailogPermission.setContentView(R.layout.dialog_permission);
        dailogPermission.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        progressDialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
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
                    StorageManager sm = (StorageManager) getContext().getSystemService(Context.STORAGE_SERVICE);
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

    public void init(View view) {
        utils = new Utils((Activity) getContext());


        rvData = view.findViewById(R.id.rvData);

        txtNoData = view.findViewById(R.id.txtNoData);


        if (which.matches("WA")) {
            strQpath = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses";
            strNormalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";


        } else {
            strQpath = "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses";
            strNormalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses";


        }
    }

    public void setImageAdapter() {

        if (Build.VERSION.SDK_INT >= 30) {

            if (fileArrayImages.size() == 0) {
                txtNoData.setVisibility(View.VISIBLE);
            } else {
                mImageAdapter = new ImageAdapter((Activity) getContext(), fileArrayImages, this);
                txtNoData.setVisibility(View.GONE);
                rvData.setAdapter(mImageAdapter);
                rvData.setLayoutManager(new GridLayoutManager(getContext(), 3));
            }
        } else {

            if (which.matches("WA")) {
                if (fileArrayImages.size() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else {
                    txtNoData.setVisibility(View.GONE);
                    mImageAdapter = new ImageAdapter((Activity) getContext(), fileArrayImages, this);
                    rvData.setAdapter(mImageAdapter);
                    rvData.setLayoutManager(new GridLayoutManager(getContext(), 3));
                }
            } else {
                if (fileArrayImages.size() == 0) {
                    txtNoData.setVisibility(View.VISIBLE);
                } else {
                    txtNoData.setVisibility(View.GONE);
                    mImageAdapter = new ImageAdapter((Activity) getContext(), fileArrayImages, this);
                    rvData.setAdapter(mImageAdapter);
                    rvData.setLayoutManager(new GridLayoutManager(getContext(), 3));
                }
            }

        }
    }

    public void setVideoAdapter() {
        mVideoAdapter = new VideoAdapter((Activity) getContext(), fileArrayVideos, this);
        if (fileArrayVideos.size() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
        } else {
            txtNoData.setVisibility(View.GONE);
            rvData.setAdapter(mVideoAdapter);
            rvData.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
                    getContext().getContentResolver().takePersistableUriPermission(dataUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    progressDialog.show();
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        new LoadAllDataQImages().execute();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.txtWrongfolder), Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(getContext(), PreviewActivity.class);
        intent.putExtra("preview", path);
        intent.putExtra("filePath", fileArrayImages.get(position).getPath());
        startActivity(intent);
    }

    @Override
    public void getVideoPosition(int position) {
        String path = fileArrayVideos.get(position).toString();
        Log.e("pathVideo--)", "" + path);
        Intent intent = new Intent(getContext(), PreviewActivity.class);
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
                    documentFile = DocumentFile.fromTreeUri(getContext(), uriParse);
                } else {
                    Uri uriParse = Uri.parse(utils.getWBUri());
                    documentFile = DocumentFile.fromTreeUri(getContext(), uriParse);
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
            DocumentFile documentFile = DocumentFile.fromTreeUri(getContext(), getContext().getContentResolver().getPersistedUriPermissions().get(0).getUri());
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
            } catch (Exception e) {
                Log.d("SHUBH", "getData: " + e.getMessage());
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
            } catch (Exception e) {
                Log.d("SHUBH", "getData: " + e.getMessage());
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