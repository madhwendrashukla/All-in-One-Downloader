package com.papayacoders.allinonedownloader.AllDownload;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.papayacoders.allinonedownloader.R;
import com.papayacoders.allinonedownloader.databinding.ActivityTikTokDownloadWebviewBinding;
import com.zhkrb.cloudflare_scrape_webview.CfCallback;
import com.zhkrb.cloudflare_scrape_webview.Cloudflare;

import java.net.HttpCookie;
import java.util.List;
import java.util.Objects;

public class FacebookDownloadCloudBypassWebview_method_1 extends AppCompatActivity {


    public static Handler handler;
    static String myvidintenturlis = "";
    private static ValueCallback<Uri[]> mUploadMessageArr;
    String TAG = "whatsapptag";
    boolean doubleBackToExitPressedOnce = false;
    boolean isdownloadstarted = false;
    ProgressDialog progressDialog;
    private ActivityTikTokDownloadWebviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityTikTokDownloadWebviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.tool12);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

        InitHandler();

        try {


            progressDialog = new ProgressDialog(FacebookDownloadCloudBypassWebview_method_1.this);
            progressDialog.setMessage(getString(R.string.nodeifittakeslonger));
            progressDialog.show();
        } catch (Exception e) {

        }
        if (getIntent().getStringExtra("myvidurl") != null && !getIntent().getStringExtra("myvidurl").equals("")) {
            myvidintenturlis = getIntent().getStringExtra("myvidurl");
        }
        binding.opentiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/"));

                    intent.setPackage("com.zhiliaoapp.musically");

                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    iUtils.ShowToast(FacebookDownloadCloudBypassWebview_method_1.this, "Tiktok not Installed");
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 24) {

            binding.webViewscan.clearFormData();
            binding.webViewscan.getSettings().setSaveFormData(true);
            // binding.webViewscan.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            binding.webViewscan.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            // binding.webViewscan.setWebChromeClient(new webChromeClients());
            binding.webViewscan.setWebViewClient(new MyBrowser());
//            binding.webViewscan.getSettings().setAppCacheMaxSize(5242880);
            binding.webViewscan.getSettings().setAllowFileAccess(true);
//            binding.webViewscan.getSettings().setAppCacheEnabled(true);
            binding.webViewscan.getSettings().setJavaScriptEnabled(true);
            binding.webViewscan.getSettings().setDefaultTextEncodingName("UTF-8");
            binding.webViewscan.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            binding.webViewscan.getSettings().setDatabaseEnabled(true);
            binding.webViewscan.getSettings().setBuiltInZoomControls(false);
            binding.webViewscan.getSettings().setSupportZoom(true);
            binding.webViewscan.getSettings().setUseWideViewPort(true);
            binding.webViewscan.getSettings().setDomStorageEnabled(true);
            binding.webViewscan.getSettings().setAllowFileAccess(true);
            binding.webViewscan.getSettings().setLoadWithOverviewMode(true);
            binding.webViewscan.getSettings().setLoadsImagesAutomatically(true);
            binding.webViewscan.getSettings().setBlockNetworkImage(false);
            binding.webViewscan.getSettings().setBlockNetworkLoads(false);
            binding.webViewscan.getSettings().setLoadWithOverviewMode(true);


            binding.webViewscan.setWebChromeClient(new WebChromeClient() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    request.grant(request.getResources());
                }
            });
            binding.webViewscan.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {

                    String nametitle = "Facebook_video_" +
                            System.currentTimeMillis();

                    DownloadFileMain.startDownloading(FacebookDownloadCloudBypassWebview_method_1.this, url, nametitle, ".mp4");


                }
            });

            binding.webViewscan.setWebChromeClient(new WebChromeClient() {

                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && binding.progressBar.getVisibility() == View.GONE) {
                        binding.progressBar.setVisibility(View.VISIBLE);

                    }

                    binding.progressBar.setProgress(progress);
                    if (progress == 100) {
                        binding.progressBar.setVisibility(View.GONE);

                    }
                }
            });

            try {
                Cloudflare cf = new Cloudflare(FacebookDownloadCloudBypassWebview_method_1.this, "http://dlphpfd.infinityfreeapp.com");
                //   cf.setUser_agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
                cf.setUser_agent(binding.webViewscan.getSettings().getUserAgentString());
                cf.setCfCallback(new CfCallback() {
                    @Override
                    public void onSuccess(List<HttpCookie> cookieList, boolean hasNewUrl, String newUrl) {

                        binding.webViewscan.loadUrl(newUrl);


                    }

                    @Override
                    public void onFail(int code, String msg) {
                        //  Toast.makeText(TikTokDownloadCloudBypassWebview_method_3.this, "" + msg, Toast.LENGTH_SHORT).show();

                        binding.webViewscan.loadUrl("http://dlphpfd.infinityfreeapp.com");

                    }
                });
                cf.getCookies();
            } catch (Exception e) {
                e.printStackTrace();
                binding.webViewscan.loadUrl("http://dlphpfd.infinityfreeapp.com");

            }
        } else {

            binding.webViewscan.clearFormData();
            binding.webViewscan.getSettings().setSaveFormData(true);
            //  binding.webViewscan.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            binding.webViewscan.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            //   binding.webViewscan.setWebChromeClient(new webChromeClients());
            binding.webViewscan.setWebViewClient(new MyBrowser());
//            binding.webViewscan.getSettings().setAppCacheMaxSize(5242880);
            binding.webViewscan.getSettings().setAllowFileAccess(true);
//            binding.webViewscan.getSettings().setAppCacheEnabled(true);
            binding.webViewscan.getSettings().setJavaScriptEnabled(true);
            binding.webViewscan.getSettings().setDefaultTextEncodingName("UTF-8");
            binding.webViewscan.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            binding.webViewscan.getSettings().setDatabaseEnabled(true);
            binding.webViewscan.getSettings().setBuiltInZoomControls(false);
            binding.webViewscan.getSettings().setSupportZoom(false);
            binding.webViewscan.getSettings().setUseWideViewPort(true);
            binding.webViewscan.getSettings().setDomStorageEnabled(true);
            binding.webViewscan.getSettings().setAllowFileAccess(true);
            binding.webViewscan.getSettings().setLoadWithOverviewMode(true);
            binding.webViewscan.getSettings().setLoadsImagesAutomatically(true);
            binding.webViewscan.getSettings().setBlockNetworkImage(false);
            binding.webViewscan.getSettings().setBlockNetworkLoads(false);
            binding.webViewscan.getSettings().setLoadWithOverviewMode(true);
            binding.webViewscan.setWebChromeClient(new WebChromeClient() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    request.grant(request.getResources());
                }
            });

            binding.webViewscan.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {

                    String nametitle = "Facebook_video_" +
                            System.currentTimeMillis();

                    DownloadFileMain.startDownloading(FacebookDownloadCloudBypassWebview_method_1.this, url, nametitle, ".mp4");


                }
            });

            binding.webViewscan.setWebChromeClient(new WebChromeClient() {

                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && binding.progressBar.getVisibility() == View.GONE) {
                        binding.progressBar.setVisibility(View.VISIBLE);

                    }

                    binding.progressBar.setProgress(progress);
                    if (progress == 100) {
                        binding.progressBar.setVisibility(View.GONE);

                    }
                }
            });

            try {
                Cloudflare cf = new Cloudflare(FacebookDownloadCloudBypassWebview_method_1.this, "http://dlphpfd.infinityfreeapp.com");
                //   cf.setUser_agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
                cf.setUser_agent(binding.webViewscan.getSettings().getUserAgentString());
                cf.setCfCallback(new CfCallback() {
                    @Override
                    public void onSuccess(List<HttpCookie> cookieList, boolean hasNewUrl, String newUrl) {

                        binding.webViewscan.loadUrl(newUrl);


                    }

                    @Override
                    public void onFail(int code, String msg) {
                        //  Toast.makeText(TikTokDownloadCloudBypassWebview_method_3.this, "" + msg, Toast.LENGTH_SHORT).show();

                        binding.webViewscan.loadUrl("http://dlphpfd.infinityfreeapp.com");

                    }
                });
                cf.getCookies();
            } catch (Exception e) {
                e.printStackTrace();
                binding.webViewscan.loadUrl("http://dlphpfd.infinityfreeapp.com");

            }
        }
    }



    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && Build.VERSION.SDK_INT >= 21) {
            mUploadMessageArr.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(i2, intent));
            mUploadMessageArr = null;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        if (keyCode == 4) {
            try {
                if (binding.webViewscan.canGoBack()) {
                    binding.webViewscan.goBack();
                    return z;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
        z = super.onKeyDown(keyCode, event);
        return z;
    }

    @SuppressLint({"WrongConstant"})
    @RequiresApi(api = 21)
    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.pressagain), Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        binding.webViewscan.clearCache(true);
    }

    public void onDestroy() {
        super.onDestroy();
        binding.webViewscan.clearCache(true);
    }

    public void onStop() {
        binding.webViewscan.clearCache(true);
        super.onStop();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @SuppressLint({"HandlerLeak"})
    private void InitHandler() {
        handler = new btnInitHandlerListner();
    }

    @SuppressLint("HandlerLeak")
    private class btnInitHandlerListner extends Handler {
        @SuppressLint({"SetTextI18n"})
        public void handleMessage(Message msg) {
        }
    }

    private class webChromeClients extends WebChromeClient {
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.e("CustomClient", consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }

    private class MyBrowser extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            binding.progressBar.setVisibility(View.VISIBLE);
            Log.e(TAG, "binding.progressBar");
            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl(request);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(TAG, "binding.progressBar GONE");
            binding.progressBar.setVisibility(View.GONE);


            String jsscript = "javascript:(function() { "

                    + "document.getElementById(\"inputboxurl\").value ='" + myvidintenturlis + "';"
                    + "document.getElementsByTagName('button')[0].click();"
                    //    + "await new Promise(resolve => setTimeout(resolve, 3000)); "
                    //  + "javascript:document.getElementsByClassName(\"pure-button pure-button-primary is-center u-bl dl-button download_link without_watermark_direct\").click(); "
                    + "})();";

            view.evaluateJavascript(jsscript, new ValueCallback() {
                public void onReceiveValue(Object obj) {
                    Log.e(TAG, "binding.progressBar reciveing data " + obj.toString());


                }
            });
            try {


                Handler handler1 = new Handler();

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Log.e(TAG, "binding.progressBar reciveing data executed 1");


                        //    binding.webViewscan.loadUrl("javascript:window.HTMLOUT.showHTML('" + url + "',''+document.getElementsByTagName('audio')[0].getAttribute(\"src\"));");


                        view.evaluateJavascript("javascript:document.getElementsByTagName('a')[0].getAttribute('href')", new ValueCallback() {
                            public void onReceiveValue(Object obj) {
                                Log.e(TAG, "binding.progressBar reciveing data download " + obj.toString());
                                if (obj.toString() != null && obj.toString().contains("http")) {
                                    Log.e(TAG, "binding.progressBar reciveing data http " + obj.toString());

                                    handler1.removeCallbacksAndMessages(null);

                                    if (!isdownloadstarted) {
                                        DownloadFileMain.startDownloading(FacebookDownloadCloudBypassWebview_method_1.this, obj.toString(), "Facebook_" + System.currentTimeMillis(), ".mp4");
                                        isdownloadstarted = true;
                                    }

                                    //  startActivity(new Intent(TikTokDownloadWebview.this, MainActivity.class));
                                    finish();
                                }


                            }
                        });

                        handler1.postDelayed(this, 2000);

                    }
                }, 2000);
            } catch (Exception e) {

                finish();
            }


        }
    }


//        public void a(String str, int i) {
//            String str2 = "";
//            if (i == 1) {
//                str2 = "https://snaptik.app/";
//                i = "function get_snaptik(url){var input=document.querySelector('input');if(input){input.value=url;var button=document.querySelector('button[type=\"submit\"]');if(button){button.click();execute()}}}var phxVideoIsFound=false;var phxVideoSpiderTicks=0;var phxTimerId=-1;function execute(){if(phxVideoIsFound||phxVideoSpiderTicks>=15){clearTimeout(phxTimerId);phxTimerId=-1;phxVideoIsFound=false;phxVideoSpiderTicks=0;return}phxVideoSpiderTicks++;phxTimerId=setTimeout(\"getData()\",1000)}function getData(){var data={};var abuttons=document.querySelector('.abuttons');if(abuttons){var array=abuttons.querySelectorAll('a');for(var i=0;i<array.length;i++){if(array[i]){phxVideoIsFound=true;console.log('url==='+array[i].href);data.video_no_mark_url=array[i].href}}}if(phxVideoIsFound){phoenix.OnVideoFound(JSON.stringify(data))}if(!phxVideoIsFound){execute()}}";
//            } else if (i == 2) {
//                str2 = "https://ssstik.io/";
//                i = "function get_snaptik(url){var input=document.querySelector('#main_page_text');if(input){input.value=url;var button=document.querySelector('#submit');if(button){button.click();execute()}}}var phxVideoIsFound=false;var phxVideoSpiderTicks=0;var phxTimerId=-1;function execute(){if(phxVideoIsFound||phxVideoSpiderTicks>=15){clearTimeout(phxTimerId);phxTimerId=-1;phxVideoIsFound=false;phxVideoSpiderTicks=0;return}phxVideoSpiderTicks++;phxTimerId=setTimeout(\"getData()\",1000)}function getData(){var result_overlay=document.querySelector('.result');if(result_overlay){var data={};var array=result_overlay.querySelectorAll('a');for(var i=0;i<array.length;i++){var cls=array[i].className;if(cls.indexOf('without_watermark_direct')>0){data.video_no_mark_url_2=array[i].href;phxVideoIsFound=true}else if(cls.lastIndexOf('without_watermark')>0){data.video_no_mark_url=array[i].href;phxVideoIsFound=true}else if(cls.indexOf('music')>0){data.mp3_url=array[i].href}}if(phxVideoIsFound){phoenix.OnVideoFound(JSON.stringify(data))}}if(!phxVideoIsFound){execute()}}";
//            } else if (i == 3) {
//                str2 = "https://ttdownloader.com/";
//                i = "function get_snaptik(url){var input=document.querySelector('input');if(input){input.value=url;var button=document.querySelector('button[id=\"submit\"]');if(button){button.click();execute()}}}var phxVideoIsFound=false;var phxVideoSpiderTicks=0;var phxTimerId=-1;function execute(){if(phxVideoIsFound||phxVideoSpiderTicks>=15){clearTimeout(phxTimerId);phxTimerId=-1;phxVideoIsFound=false;phxVideoSpiderTicks=0;return}phxVideoSpiderTicks++;phxTimerId=setTimeout(\"getData()\",1000)}function getData(){var data={};var list=document.querySelector('.results-list');if(list){var a=list.querySelector('a');if(a){phxVideoIsFound=true;data.video_no_mark_url=a.href}}if(phxVideoIsFound){phoenix.OnVideoFound(JSON.stringify(data))}if(!phxVideoIsFound){execute()}}";
//            } else if (i == 4) {
//                str2 = "https://getvidtik.com/";
//                i = "function get_snaptik(url){var input=document.querySelector('input');if(input){input.value=url;var button=document.querySelector('button[type=\"submit\"]');if(button){button.click();execute()}}else{execute()}}var phxVideoIsFound=false;var phxVideoSpiderTicks=0;var phxTimerId=-1;function execute(){if(phxVideoIsFound||phxVideoSpiderTicks>=15){clearTimeout(phxTimerId);phxTimerId=-1;phxVideoIsFound=false;phxVideoSpiderTicks=0;return}phxVideoSpiderTicks++;phxTimerId=setTimeout(\"getData()\",1000)}function getData(){var data={};var array=document.querySelectorAll('a');for(var i=0;i<array.length;i++){if(array[i]){var url=array[i].href;if(url.indexOf('v1.musicallydown.com')>0){data.video_no_mark_url=url;phxVideoIsFound=true}}}var a=document.querySelector('a[rel=\"noreferrer\"]');if(a&&data.video_no_mark_url==undefined){phxVideoIsFound=true;data.video_no_mark_url=a.href}if(phxVideoIsFound){phoenix.OnVideoFound(JSON.stringify(data))}if(!phxVideoIsFound){execute()}}";
//            } else {
//                i = str2;
//            }
//            this.g = new d(str2, i);
//            a(str);
//        }


}
