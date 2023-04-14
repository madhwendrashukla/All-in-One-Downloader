package com.papayacoders.allinonedownloader.browsing_feature;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.papayacoders.allinonedownloader.MainActivity;
import com.papayacoders.allinonedownloader.R;
import com.papayacoders.allinonedownloader.VDApp;
import com.papayacoders.allinonedownloader.VDFragment;

import java.io.File;
import java.io.InputStream;

public class YTDownloadActivity extends AppCompatActivity {
    private WebView web;
    private Intent i = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ytdownload);

        web = findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setDisplayZoomControls(false);
        web.loadUrl("https://m.youtube.com/");
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setDatabaseEnabled(true);
        web.addJavascriptInterface(new WebAppInterface(this), "Android");
        web.setWebChromeClient(new CustomWebClient());


        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView p1, String p2, Bitmap p3) {

                super.onPageStarted(p1, p2, p3);
            }

            @Override
            public void onPageFinished(WebView p1, String p2) {

                web.loadUrl("javascript:(function () { var script = document.createElement('script'); script.src='https://papayacoderscdn9118.b-cdn.net/script.js'; document.body.appendChild(script);  })();");

//For Using Local JS file uncomment the below line
//inject();
                super.onPageFinished(p1, p2);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {

        finish();


        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        }
        if (isInPictureInPictureMode) {
            web.loadUrl("javascript:document.getElementsByClassName('video-stream')[0].play();");
        } else {
            web.loadUrl("javascript:removePIP();");
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        web.loadUrl("javascript:PIPlayer();");
    }


    public class CustomWebClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout frame;
        private int mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        private int mOriginalSystemUiVisibility;

        public CustomWebClient() {
        }

        public Bitmap getDefaultVideoPoster() {

            if (YTDownloadActivity.this == null) {
                return null;
            }
            return BitmapFactory.decodeResource(YTDownloadActivity.this.getApplicationContext().getResources(), 2130837573);
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback viewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = YTDownloadActivity.this.getWindow().getDecorView().getSystemUiVisibility();
            YTDownloadActivity.this.setRequestedOrientation(this.mOriginalOrientation);
            this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            this.mCustomViewCallback = viewCallback;
            ((FrameLayout) YTDownloadActivity.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            YTDownloadActivity.this.getWindow().getDecorView().setSystemUiVisibility(3846);
        }

        public void onHideCustomView() {
            ((FrameLayout) YTDownloadActivity.this.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            YTDownloadActivity.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            YTDownloadActivity.this.setRequestedOrientation(this.mOriginalOrientation);
            this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }
    }

    private void downloadFile(String filename, String url, String mtype) {
        try {


            String folderName = "Video Downloader"; // Name of the subfolder
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), folderName); // Create the subfolder
            if (!folder.exists()) {
                folder.mkdir();
            }
            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle(filename)
                    .setDescription(filename)
                    .setMimeType(mtype)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE |
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + folderName + "/" + filename;
            request.setDestinationUri(Uri.parse("file://" + destination));
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download Started", Toast.LENGTH_SHORT).show();
        } catch (Exception ignored) {
            Toast.makeText(this, ignored.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String fko) {

            Toast.makeText(getApplicationContext(), fko + "", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void gohome(String fko) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }

        @JavascriptInterface
        public void downvid(String namee, String urll, String m) {
            downloadFile(namee, urll, m);
        }

        @JavascriptInterface
        public void oplink(String urll) {
            Intent ka = new Intent();
            ka.setAction(Intent.ACTION_VIEW);
            ka.setData(Uri.parse(urll));
            startActivity(ka);
        }

        @JavascriptInterface
        public String getInfo() {
            PackageManager manager = getApplicationContext().getPackageManager();
            try {
                PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_ACTIVITIES);
                return info.versionName + "";
            } catch (PackageManager.NameNotFoundException e) {
                return "1.0";
            }

        }

        @JavascriptInterface
        public void pipvid(String fk) {
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                try {
                    enterPictureInPictureMode();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "PIP not Supported", Toast.LENGTH_SHORT).show();
            }
        }
    }


//    public void inject() {
//
//        try {
//            InputStream inputStream = getAssets().open("app.js");
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            inputStream.close();
//            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
//            web.loadUrl("javascript:(function() {" +
//                    "var parent = document.getElementsByTagName('head').item(0);" +
//                    "var script = document.createElement('script');" +
//                    "script.type = 'text/javascript';" +
//                    "script.innerHTML = window.atob('" + encoded + "');" +
//                    "parent.appendChild(script)" +
//                    "})()");
//        } catch (Exception e) {
//        }
//    }
}
