package com.papayacoders.allinonedownloader.AllDownload.webservices;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WINDOW_SERVICE;
import static com.papayacoders.allinonedownloader.utils.Constants.DlApisUrl;
import static com.papayacoders.allinonedownloader.utils.Constants.DlApisUrl2;
import static com.papayacoders.allinonedownloader.utils.Constants.DlApisUrl3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.htetznaing.lowcostvideo.LowCostVideo;
import com.htetznaing.lowcostvideo.Model.XModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.papayacoders.allinonedownloader.AllDownload.CPU;
import com.papayacoders.allinonedownloader.AllDownload.DLDataParser;
import com.papayacoders.allinonedownloader.AllDownload.DownloadFileMain;
import com.papayacoders.allinonedownloader.AllDownload.FacebookDownloadCloudBypassWebview_method_1;
import com.papayacoders.allinonedownloader.AllDownload.Format;
import com.papayacoders.allinonedownloader.AllDownload.QualityBottomsheetAdapter;
import com.papayacoders.allinonedownloader.AllDownload.Video;
import com.papayacoders.allinonedownloader.AllDownload.iUtils;
import com.papayacoders.allinonedownloader.AllDownload.webservices.api.RetrofitApiInterface;
import com.papayacoders.allinonedownloader.AllDownload.webservices.api.RetrofitClient;
import com.papayacoders.allinonedownloader.R;
import com.papayacoders.allinonedownloader.utils.Constants;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

@SuppressLint("all")
public class DownloadVideosMain {

    private static final int DOWNLOAD_NOTIFICATION_ID = 231;

    public static Context Mcontext;
    public static ProgressDialog pd;
    public static Dialog dialog;
    public static SharedPreferences prefs;
    public static Boolean fromService;
    public static String VideoUrl;
    static String Title;
    static LinearLayout mainLayout;
    static WindowManager windowManager2;
    static WindowManager.LayoutParams params;
    static View mChatHeadView;
    static ImageView img_dialog;
    static String myURLIS = "";
    static Dialog dialog_quality_allvids;
    private static String newurl;
    private static Thread mytiktok_get_thread;

    public static StringBuilder m2858a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }



    /*
TODO To Add Supported Networks (Total 103)

//todo pending checking
FairTok
raask

//todo
vidoza
resso


TODO not avaliable
dtube
veer.tv
     */


    public static String m2854a(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static final String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 255);
                while (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getLocalizedMessage());
            return "";
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void Start(final Context context, String url, Boolean service) {
        try {
            Mcontext = context;
            fromService = service;
            Log.i("LOGClipboard111111 clip", "work 2");

            myURLIS = url;
            if (!fromService) {
                pd = new ProgressDialog(context);
                pd.setMessage(Mcontext.getResources().getString(R.string.genarating_download_link));
                pd.setCancelable(false);
                pd.show();
            }
            if (url.contains("tiktok")) {


            } else if (url.contains("sharechat.com")) {
                Log.i("LOGClipboard111111 clip", "work 66666");
                try {
                    // new callGetShareChatData().execute(url);

                    url = iUtils.extractUrls(url).get(0);
                    System.out.println("subssssssss11 " + url);

                    int index = url.lastIndexOf('/') + 1;
                    url = url.substring(index);
                    System.out.println("subssssssss " + url);

                    JSONObject jsonObject = new JSONObject("{\"bn\":\"broker3\",\"userId\":644045091,\"passCode\":\"52859d76753457f8dcae\",\"client\":\"web\",\"message\":{\"key\":\"" + url + "\",\"ph\":\"" + url + "\"}}");
                    AndroidNetworking.post("https://apis.sharechat.com/requestType45")
                            .addJSONObjectBody(jsonObject)
                            .addHeaders("Content-Type", "application/json")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    dismissMyDialog();
                                    String matag;
                                    try {

                                        JSONObject jsonObject = new JSONObject(response.toString());

                                        matag = jsonObject.getJSONObject("payload").getJSONObject("d").getString("v");
                                        System.out.println("wojfdjhfdjh " + matag);
                                        DownloadFileMain.startDownloading(context, matag, "Sharechat_" + System.currentTimeMillis(), ".mp4");


                                    } catch (Exception e) {
                                        matag = "";

                                        e.printStackTrace();
                                        dismissMyDialog();
                                    }


                                }

                                @Override
                                public void onError(ANError error) {
                                    dismissMyDialog();
                                }
                            });

                } catch (Exception e) {
                    dismissMyDialog();
                }


            } else if (url.contains("roposo.com")) {
                Log.i("LOGClipboard111111 clip", "work 66666");

                new callGetRoposoData().execute(url);
                Log.i("LOGClipboard111111 clip", "work 1111111");

            } else if (url.contains("snackvideo.com") || url.contains("sck.io")) {

                //    new callGetSnackAppData().execute(url);

                if (url.contains("snackvideo.com")) {
                    new callGetSnackAppData().execute(url);
                } else if (url.contains("sck.io")) {
                    getSnackVideoData(url, Mcontext);
                }

            } else if (url.contains("facebook.com") || url.contains("fb.watch")) {

                if (!((Activity) Mcontext).isFinishing()) {
                    Dialog dialog = new Dialog(Mcontext);

                    dialog.setContentView(R.layout.tiktok_optionselect_dialog);

                    String finalUrl1 = url;

                    Button methode0 = dialog.findViewById(R.id.dig_btn_met0);
                    Button methode1 = dialog.findViewById(R.id.dig_btn_met1);
                    Button methode2 = dialog.findViewById(R.id.dig_btn_met2);
                    Button methode3 = dialog.findViewById(R.id.dig_btn_met3);
                    Button methode4 = dialog.findViewById(R.id.dig_btn_met4);

                    Button dig_btn_cancel = dialog.findViewById(R.id.dig_btn_cancel);

                    methode0.setVisibility(View.GONE);

                    methode1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();


//                    FbVideoDownloader fbVideoDownloader = new FbVideoDownloader(Mcontext, finalUrl1, 0);
//                    fbVideoDownloader.DownloadVideo();
                            //    new CallGetFacebookData().execute(new String[]{finalUrl1});

                            try {

                                System.out.println("myurliss = " + finalUrl1);

                                new Thread() {

                                    @Override
                                    public void run() {
                                        super.run();
                                        try {
                                            Looper.prepare();
                                            OkHttpClient client = new OkHttpClient.Builder()
                                                    .connectTimeout(10, TimeUnit.SECONDS)
                                                    .writeTimeout(10, TimeUnit.SECONDS)
                                                    .readTimeout(30, TimeUnit.SECONDS)
                                                    .build();
                                            Request request = new Request.Builder()
                                                    .url(finalUrl1)
                                                    .method("GET", null)
                                                    .build();

                                            Response response = null;

                                            response = client.newCall(request).execute();

                                            Document document = Jsoup.parse(response.body().string());
                                            dismissMyDialog();

                                            String unused = document.select("meta[property=\"og:video\"]").last().attr("content");
                                            if (!unused.equals("")) {
                                                String nametitle = "Facebook_" +
                                                        System.currentTimeMillis();

                                                DownloadFileMain.startDownloading(Mcontext, unused, nametitle, ".mp4");

                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            dismissMyDialogErrortoast();
                                        }


                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                                dismissMyDialogErrortoast();
                            }

                        }
                    });

                    methode2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();


                            System.out.println("wojfdjhfdjh myfgsdyfsfus=" + finalUrl1);

                            dismissMyDialog();
                            Intent intent = new Intent(Mcontext, FacebookDownloadCloudBypassWebview_method_1.class);
                            intent.putExtra("myvidurl", finalUrl1);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Mcontext.startActivity(intent);
//                    FbVideoDownloader fbVideoDownloader = new FbVideoDownloader(Mcontext, finalUrl1, 1);
//                    fbVideoDownloader.DownloadVideo();


                        }
                    });


                    methode3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            FbVideoDownloader fbVideoDownloader = new FbVideoDownloader(Mcontext, finalUrl1, 1);
                            fbVideoDownloader.DownloadVideo();
                        }
                    });
                    methode4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            AndroidNetworking.get("https://dlphpapis21.herokuapp.com/api/info?url=" + finalUrl1 + "&flatten=True")
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            dismissMyDialog();

                                            System.out.println("fjhjfhjsdfsdhf " + response);

                                            String matag;
                                            try {

                                                JSONObject jsonObject = new JSONObject(response.toString());

                                                matag = jsonObject.getJSONArray("videos").getJSONObject(0).getString("url");
                                                System.out.println("wojfdjhfdjh " + matag);

                                                DownloadFileMain.startDownloading(Mcontext, matag, "Facebook_" + System.currentTimeMillis(), ".mp4");


                                            } catch (Exception e) {
                                                matag = "";
                                                Toast.makeText(Mcontext, "Error", Toast.LENGTH_SHORT).show();


                                            }


                                        }

                                        @Override/**/
                                        public void onError(ANError error) {

                                            dismissMyDialogErrortoast();


                                        }
                                    });
                        }
                    });


                    dig_btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            dismissMyDialog();
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();
                }

            } else if (url.contains("blogspot.com")) {


                CalldlApisDataData(url, true);
            } else if (url.contains("instagram.com")) {

                new GetInstagramVideo().execute(url);

            } else if (url.contains("bilibili.com")) {

                new callGetbilibiliAppData().execute(url);

            } else if (url.contains("mitron.tv")) {

                new CallMitronData().execute(url);
            } else if (url.contains("josh")) {

                new CallJoshData().execute(url);
            } else if (url.contains("triller")) {

                //  new CallTrillerData().execute(url);

                getTrillerData(url);
            } else if (url.contains("rizzle")) {

                new CallRizzleData().execute(url);
            } else if (url.contains("audioboom")) {

                AudioboomDownloader audioboomDownloader = new AudioboomDownloader(Mcontext, url);
                audioboomDownloader.DownloadVideo();

            } else if (url.contains("ifunny")) {

                new CallIfunnyData().execute(url);
            } else if (url.contains("trell.co")) {

                new CalltrellData().execute(url);
            } else if (url.contains("boloindya.com")) {

                new CallBoloindyaData().execute(url);
            } else if (url.contains("chingari")) {

                CallchingariData(url);
            } else if (url.contains("dubsmash")) {

                new CalldubsmashData().execute(url);
            } else if (url.contains("bittube")) {

                String myurlis1 = url;
                if (myurlis1.contains(".tv")) {
                    String str = "/";
                    myurlis1 = myurlis1.split(str)[myurlis1.split(str).length - 1];
                    myurlis1 = "https://bittube.video/videos/watch/" +
                            myurlis1;
                }
                new CallgdriveData().execute(myurlis1);

            } else if (url.contains("drive.google.com") ||
                    url.contains("mp4upload") ||

                    url.contains("ok.ru") ||

                    url.contains("mediafire") ||
                    url.contains("gphoto") ||
                    url.contains("uptostream") ||

                    url.contains("fembed") ||
                    url.contains("cocoscope") ||
                    url.contains("sendvid") ||

                    url.contains("vivo") ||
                    url.contains("fourShared")) {


                new CallgdriveData().execute(url);
            } else if (url.contains("hind")) {

                new CallhindData().execute(url);
            } else if (url.contains("vimeo.com")) {
                // VimeoVideoDownloader downloader = new VimeoVideoDownloader(Mcontext, url);
                //  downloader.DownloadVideo();
                CalldlApisDataData(url, true);

            } else if (url.contains("twitter.com")) {
                TwitterVideoDownloader downloader = new TwitterVideoDownloader(Mcontext, url);
                downloader.DownloadVideo();
                //  CallDailymotionData(url, true);
                //CalldlApisDataData(url, true);

            }
            //new
            //working
            else if (url.contains("gag.com")) {
//            https://9gag.com/gag/aXowVXz
                //  CalldlApisDataData(url, false);
                new Call9gagData().execute(url);


            } else if (url.contains("buzzfeed.com")) {


                dismissMyDialogErrortoast();
            }


            //TODO Add quality list
            else if (url.contains("flickr") && url.contains("flic.kr")) {
                // CallDailymotionData(url, true);
                CalldlApisDataData(url, true);

            } else if (url.contains("streamable")) {
                // CallDailymotionData(url, true);
                CalldlApisDataData(url, true);

            } else if (url.contains("vk.com")) {

                //    CalldlApisDataData(url, false);

                CallVKData(url, false);


            } else if (url.contains("redd.it") || url.contains("reddit")) {

                //  CalldlApisDataData(url, true);
                new CallRedditData().execute("https://redditsave.com/info?url=" + url);
                //  CallREditData(url, true);


            } else if (url.contains("soundcloud")) {


                // CallsoundData(url, false);


                if (Constants.showsoundcloud) {
                    url = url.replace("//m.", "//");

                    CalldlApisDataData(url, true);
                } else {
                    dismissMyDialogErrortoast();
                }


            } else if (url.contains("bandcamp")) {
//TODO Has multiple video json array
                CalldlApisDataData(url, true);
                //   CallsoundData(url, false);


            } else if (url.contains("mxtakatak")) {


                String finalUrl3 = url;
                if (finalUrl3.contains("share.mxtakatak.com")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            HttpURLConnection con = null;
                            try {
                                con = (HttpURLConnection) (new URL(finalUrl3).openConnection());

                                con.setInstanceFollowRedirects(false);
                                con.connect();
                                int responseCode = con.getResponseCode();
                                System.out.println(responseCode);
                                String location = con.getHeaderField("Location");
                                System.out.println(location);

                                if (location != null && !location.equals("") && location.contains("https://www.mxtakatak.com/")) {

                                    String urls = location.split("/")[5];
                                    urls = urls.substring(0, urls.indexOf("?"));


                                    String newuuu = "https://mxshorts.akamaized.net/video/" + urls + "/download/1/h264_high_720.mp4";


                                    String nametitle = "Mxtaktak_" +
                                            System.currentTimeMillis();

                                    DownloadFileMain.startDownloading(Mcontext, newuuu, nametitle, ".mp4");

                                }

                                dismissMyDialog();


                            } catch (Exception e) {
                                dismissMyDialogErrortoast();
                            }
                        }
                    }).start();
                } else {
                    try {


                        String urls = finalUrl3.split("/")[5];
                        urls = urls.substring(0, urls.indexOf("?"));


                        String newuuu = "https://mxshorts.akamaized.net/video/" + urls + "/download/1/h264_high_720.mp4";


                        String nametitle = "Mxtaktak_" +
                                System.currentTimeMillis();
                        dismissMyDialog();
                        DownloadFileMain.startDownloading(Mcontext, newuuu, nametitle, ".mp4");

                    } catch (Exception e) {
                        dismissMyDialogErrortoast();
                    }
                }

                //  new CallmxtaktakData().execute(url);


//            AndroidNetworking.post("http://mxtakatakvideodownloader.shivjagar.co.in/MXTakatak-service.php")
//                    .addBodyParameter("url", url)
//                    .setPriority(Priority.MEDIUM)
//                    .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            dismissMyDialog();
//                            String matag;
//                            try {
//
//                                JSONObject jsonObject = new JSONObject(response.toString());
//
//                                matag = jsonObject.getString("videourl");
//                                System.out.println("wojfdjhfdjh " + matag);
//                                DownloadFileMain.startDownloading(context, matag, "Mxtakatak_" + System.currentTimeMillis(), ".mp4");
//
//
//                            } catch (Exception e) {
//                                matag = "";
//
//                                e.printStackTrace();
//                               dismissMyDialog();
//                            }
//
//
//                        }
//
//                        @Override/**/
//                        public void onError(ANError error) {
//                           dismissMyDialog();
//                        }
//                    });


            } else if (url.contains("cocoscope")) {


                // CallsoundData(url, false);
                CalldlApisDataData(url, true);

            } else if (url.contains("test.com")) {

                new CallgaanaData().execute(url);
                // CallsoundData(url, false);
                //  CalldlApisDataData(url, true);

            } else if (url.contains("gaana")) {


                String finalUrl = url;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection con = null;
                        try {
                            con = (HttpURLConnection) (new URL("https://tinyurl.com/f67p797b").openConnection());

                            con.setInstanceFollowRedirects(false);
                            con.connect();
                            int responseCode = con.getResponseCode();
                            System.out.println(responseCode);
                            String location = con.getHeaderField("Location");
                            System.out.println(location);


                            AndroidNetworking.post(location)
                                    .addBodyParameter("url", finalUrl)
                                    .addBodyParameter("weburl", "https://video.infusiblecoder.com/")
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            dismissMyDialog();
                                            String matag;
                                            try {

                                                JSONObject jsonObject = new JSONObject(response.toString());

                                                matag = jsonObject.getJSONArray("songlinks").getJSONObject(0).getString("songurl");
                                                System.out.println("wojfdjhfdjh " + matag);
                                                DownloadFileMain.startDownloading(context, matag, "Gaana_" + System.currentTimeMillis(), ".mp3");


                                            } catch (Exception e) {
                                                matag = "";

                                                e.printStackTrace();
                                                dismissMyDialog();
                                            }


                                        }

                                        @Override/**/
                                        public void onError(ANError error) {

                                            System.out.println("wojfdjhfdjh error = " + error.getMessage());


                                            dismissMyDialog();
                                        }
                                    });


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            } else if (url.contains("izlesene")) {


                //CallsoundData(url, false);
                CalldlApisDataData(url, false);

            } else if (url.contains("linkedin")) {


                //   CalldlApisDataData(url, false);
                new CalllinkedinData().execute(url);

            } else if (url.contains("bitchute")) {


                CalldlApisDataData(url, false);


            } else if (url.contains("douyin")) {

                try {


                    String[] idis = url.split("/");


                    AndroidNetworking.get("https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=" + idis[idis.length - 1])
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    dismissMyDialog();
                                    String matag;
                                    try {

//                                $video_info["item_list"][0]["video"]["play_addr"]["url_list"][0];
                                        JSONObject jsonObject = new JSONObject(response.toString());

                                        JSONArray itemlist = jsonObject.getJSONArray("item_list");
                                        matag = itemlist.getJSONObject(0).getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").getString(0);


                                        System.out.println("wojfdjhfdjh " + matag);
                                        DownloadFileMain.startDownloading(context, matag, "Douyin_" + System.currentTimeMillis(), ".mp4");


                                    } catch (Exception e) {
                                        matag = "";

                                        e.printStackTrace();
                                        dismissMyDialog();
                                    }


                                }

                                @Override/**/
                                public void onError(ANError error) {
                                    dismissMyDialogErrortoast();
                                }
                            });


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (url.contains("espn.com")) {
                CalldlApisDataData(url, true);

            } else if (url.contains("coub")) {
                CalldlApisDataData(url, true);
//            CoubDownloader coubDownloader = new CoubDownloader(Mcontext, url);
//            coubDownloader.DownloadVideo();


            } else if (url.contains("aparat")) {
                AparatDownloader aparatDownloader = new AparatDownloader(Mcontext, url);
                aparatDownloader.DownloadVideo();


            } else if (url.contains("allocine.fr")) {
                AllocineDownloader allocineDownloader = new AllocineDownloader(Mcontext, url);
                allocineDownloader.DownloadVideo();


            } else if (url.contains("ted.com")) {

                CalldlApisDataData(url, true);
            } else if (url.contains("twitch")) {
                CalldlApisDataData(url, true);

            } else if (url.contains("imdb.com")) {
                CalldlApisDataData(url, false);

            } else if (url.contains("camdemy")) {
                CalldlApisDataData(url, false);

            } else if (url.contains("pinterest") || url.contains("pin.it")) {
                CalldlApisDataData(url, false);

            } else if (url.contains("imgur.com")) {
                url = url.replace("//m.", "//");
                CalldlApisDataData(url, false);

            } else if (url.contains("tumblr.com")) {

                new CalltumblerData().execute(url);
            }


//TODO youtube from here

            else if (url.contains("youtube.com") || url.contains("youtu.be")) {

                if (Constants.showyoutube) {
                    Log.i("LOGClipboard111111 clip", "work 3");
                    // getYoutubeDownloadUrl(url);
                    CalldlApisDataData(url, true);

                } else {
                    dismissMyDialogErrortoast();
                }


            }


//TODO Till Here
            else {


                //  CalldlApisDataData(url, true);


                dismissMyDialogErrortoast();
            }


            prefs = Mcontext.getSharedPreferences("AppConfig", MODE_PRIVATE);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void getTrillerData(String url1) {


//            if (url1.contains("v.triller")) {
//
//            } else {
//
//                newurl = "https://social.triller.co/v1.5/api/videos/" + url1.substring(url1.indexOf("/video/") + 7);
//
//            }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) (new URL(url1).openConnection());

                    con.setInstanceFollowRedirects(false);
                    con.connect();
                    int responseCode = con.getResponseCode();
                    System.out.println(responseCode);
                    String location = con.getHeaderField("Location");
                    System.out.println(location);


                    newurl = "https://social.triller.co/v1.5/api/videos/" + location.substring(location.indexOf("/video/") + 7);


                    System.out.println("mydnewurlis=" + newurl);

                    AndroidNetworking.get(newurl)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println("mydnewurlis res =" + response);

                                    dismissMyDialog();
                                    String matag;
                                    try {

                                        matag = response.getJSONArray("videos").getJSONObject(0).getString("video_url");


                                        DownloadFileMain.startDownloading(Mcontext, matag, "Triller_" + System.currentTimeMillis(), ".mp4");

                                    } catch (Exception e) {
                                        matag = "";

                                        e.printStackTrace();
                                        dismissMyDialog();
                                    }


                                }

                                @Override/**/
                                public void onError(ANError error) {

                                    System.out.println("wojfdjhfdjh error = " + error.getMessage());


                                    dismissMyDialog();
                                }
                            });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private static void CallchingariData(String url) {
        try {
            String[] urlstr = url.split("=");

            AndroidNetworking.get("https://api.chingari.io/post/post_details/" + urlstr[1])
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {


                            dismissMyDialog();
                            String matag = "";
                            try {
                                System.out.println("fjhjfhjsdfsdhf " + response);

                                JSONObject jsonObject = new JSONObject(response.toString());


                                JSONObject transcode = jsonObject.getJSONObject("data").getJSONObject("mediaLocation").getJSONObject("transcoded");


                                if (transcode.has("p1024")) {
                                    matag = transcode.getString("p1024");
                                } else if (transcode.has("p720")) {
                                    matag = transcode.getString("p720");

                                } else if (transcode.has("p480")) {
                                    matag = transcode.getString("p480");


                                }
                                matag = "https://media.chingari.io" + matag;
                                System.out.println("wojfdjhfdjh " + matag);
                                DownloadFileMain.startDownloading(Mcontext, matag, "Chingari_" + System.currentTimeMillis(), ".mp4");


                            } catch (Exception e) {
                                matag = "";
                                dismissMyDialog();
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(ANError error) {
                            dismissMyDialog();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    static void download_hlsganna(String urlis, String extension) {
//
//        //String M3U8URL = "https://68vod-adaptive.akamaized.net/exp=1616574881~acl=%2F115074667%2F%2A~hmac=a5f9ea0179c7aaeea7374a0a4bd565e600def9d380564e005141a36e93f4add5/115074667/sep/audio/319228990/playlist.m3u8";
//         String M3U8URL = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
//        //String M3U8URL = "https://videozmcdn.stz8.com:8091/20191127/PK7a0LKQ/index.m3u8";
//        //  String M3U8URL = "https://e1v-h.phncdn.com/hls/videos/202103/18/385313421/480P_2000K_385313421.mp4/master.m3u8?validfrom=1616502433&validto=1616509633&ip=52.211.71.244&hdl=-1&hash=Rglvlmx4WVSqIjxpRt8h2XEUXn8%3D";
//
//
//        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test";
//        File dir = new File(downloadPath);
//        if (!dir.exists()) {
//            //noinspection ResultOfMethodCallIgnored
//            dir.mkdirs();
//        }
//
//        //Run following command: $ ffmpeg -i audioInput.mp3 -i videoInput.avi -acodec copy -vcodec copy outputFile.avi This is it. outputFile.avi will be the resulting file.
//        String cmd = String.format("-i %s -acodec %s -bsf:a aac_adtstoasc -vcodec %s %s", M3U8URL, "copy", "copy", dir.toString() + "/" + MY_ANDROID_10_IDENTIFIER_OF_FILE + System.currentTimeMillis() + extension);
//        //  String cmd = String.format("-i %s -acodec %s -bsf:a aac_adtstoasc -vcodec %s %s", M3U8URL, "copy", "copy", dir.toString() + "/bigBuckBunny.mp4");
//        String[] command = cmd.split(" ");
//
//
//        long executionId = FFmpeg.executeAsync(command, new ExecuteCallback() {
//
//            @Override
//            public void apply(final long executionId, final int returnCode) {
//                if (returnCode == Config.RETURN_CODE_SUCCESS) {
//                    Log.i(Config.TAG, "Async command execution completed successfully.");
//                } else if (returnCode == Config.RETURN_CODE_CANCEL) {
//                    Log.i(Config.TAG, "Async command execution cancelled by user.");
//                } else {
//                    Log.i(Config.TAG, String.format("Async command execution failed with returnCode=%d.", returnCode));
//                }
//            }
//        });
//
//        Config.enableStatisticsCallback(new StatisticsCallback() {
//            public void apply(Statistics newStatistics) {
//
//               dismissMyDialog();
//
//
//                Log.d(Config.TAG+"usa", String.format("frame: %d, time: %d", newStatistics.getVideoFrameNumber(), newStatistics.getTime()));
//              //  showdownload_progress(newStatistics.getTime(),newStatistics.getSize());
//            }
//        });
//
//        //            System.out.println("downloadURL" + downloadUrl + "\tdownloaded "+ finished + "个\taltogether" + sum + "个\tcompleted" + percent + "%");
//
//
//    }
//    public static void download_video_with_audio_ytd(String urlis, String extension) {
//
//        String M3U8URL = "https://68vod-adaptive.akamaized.net/exp=1616574881~acl=%2F115074667%2F%2A~hmac=a5f9ea0179c7aaeea7374a0a4bd565e600def9d380564e005141a36e93f4add5/115074667/sep/audio/319228990/playlist.m3u8";
//        // String M3U8URL = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
//        //String M3U8URL = "https://videozmcdn.stz8.com:8091/20191127/PK7a0LKQ/index.m3u8";
//        //  String M3U8URL = "https://e1v-h.phncdn.com/hls/videos/202103/18/385313421/480P_2000K_385313421.mp4/master.m3u8?validfrom=1616502433&validto=1616509633&ip=52.211.71.244&hdl=-1&hash=Rglvlmx4WVSqIjxpRt8h2XEUXn8%3D";
//
//
//        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test";
//        File dir = new File(downloadPath);
//        if (!dir.exists()) {
//            //noinspection ResultOfMethodCallIgnored
//            dir.mkdirs();
//        }
//
//        //Run following command: $ ffmpeg -i audioInput.mp3 -i videoInput.avi -acodec copy -vcodec copy outputFile.avi This is it. outputFile.avi will be the resulting file.
//        String withaudio = String.format("-i %s -i %s -acodec %s -vcodec %s %s", urlis, urlis, "copy", "copy", dir.toString() + "/" + MY_ANDROID_10_IDENTIFIER_OF_FILE + System.currentTimeMillis() + extension);
//        //   String cmd = String.format("-i %s -acodec %s -bsf:a aac_adtstoasc -vcodec %s %s", urlis, "copy", "copy", dir.toString() + "/"+MY_ANDROID_10_IDENTIFIER_OF_FILE+System.currentTimeMillis()+extension);
//        //  String cmd = String.format("-i %s -acodec %s -bsf:a aac_adtstoasc -vcodec %s %s", M3U8URL, "copy", "copy", dir.toString() + "/bigBuckBunny.mp4");
//        String[] command = withaudio.split(" ");
//
//
//        long executionId = FFmpeg.executeAsync(command, new ExecuteCallback() {
//
//            @Override
//            public void apply(final long executionId, final int returnCode) {
//                if (returnCode == Config.RETURN_CODE_SUCCESS) {
//                    Log.i(Config.TAG, "Async command execution completed successfully.");
//                } else if (returnCode == Config.RETURN_CODE_CANCEL) {
//                    Log.i(Config.TAG, "Async command execution cancelled by user.");
//                } else {
//                    Log.i(Config.TAG, String.format("Async command execution failed with returnCode=%d.", returnCode));
//                }
//            }
//        });
//
//        Config.enableStatisticsCallback(new StatisticsCallback() {
//            public void apply(Statistics newStatistics) {
//                Log.d(Config.TAG, String.format("frame: %d, time: %d", newStatistics.getVideoFrameNumber(), newStatistics.getTime()));
//            }
//        });
//
//        //            System.out.println("downloadURL" + downloadUrl + "\tdownloaded "+ finished + "个\taltogether" + sum + "个\tcompleted" + percent + "%");
//
//
//    }


    private static void showdownload_progress(int timeelipsed, long filesize) {
        Thread thread = new Thread() {
            @Override
            public void run() {

                String filesize2 = iUtils.getStringSizeLengthFile(filesize);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Mcontext);

                String contentTitle = "Downloaded";
                Intent notifyIntent = new Intent();
                PendingIntent notifyPendingIntent = PendingIntent.getActivity(Mcontext, DOWNLOAD_NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder notificationBuilder = createNotificationBuilder("downloader_channel");
                notificationBuilder.setContentIntent(notifyPendingIntent);
                notificationBuilder.setTicker("Start downloading from the server");
                notificationBuilder.setOngoing(true);
                notificationBuilder.setAutoCancel(false);
                notificationBuilder.setSmallIcon(android.R.drawable.stat_sys_download);
                notificationBuilder.setContentTitle(contentTitle);
                //   notificationBuilder.setContentText("0%");
                //   notificationBuilder.setProgress(100, 0, false);
                notificationManagerCompat.notify(DOWNLOAD_NOTIFICATION_ID, notificationBuilder.build());


                long total = 0;
                int count, tmpPercentage = 0;


                System.out.println("mobile-ffmpegusa prog :" + "time elipsed: " + iUtils.formatDuration(timeelipsed) + " downloaded : " + filesize2 + "%");
                notificationBuilder.setContentText("time elipsed: " + iUtils.formatDuration(timeelipsed) + " downloaded : " + filesize2);
                notificationBuilder.setProgress(100, timeelipsed, false);
                notificationManagerCompat.notify(DOWNLOAD_NOTIFICATION_ID, notificationBuilder.build());


                notificationBuilder.setContentTitle(contentTitle);
                notificationBuilder.setSmallIcon(android.R.drawable.stat_sys_download_done);
                notificationBuilder.setOngoing(false);
                notificationBuilder.setAutoCancel(true);
                //notificationBuilder.setContentText("0");
                // notificationBuilder.setProgress(0, 0, false);
                notificationManagerCompat.notify(DOWNLOAD_NOTIFICATION_ID, notificationBuilder.build());

            }
        };

        thread.start();
    }


    private static NotificationCompat.Builder createNotificationBuilder(String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = Mcontext.getString(R.string.app_name);
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = (NotificationManager) Mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        return new NotificationCompat.Builder(Mcontext, channelId);
    }


//    private static ArrayList<VideoModel> DownloadFiles(String decryptedurl) {
//        ArrayList<VideoModel> videoModels1 = new ArrayList();
//
//        try {
////            URL url1 = new URL(decryptedurl);
////
////            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
////            connection.connect();
////            InputStream stream = connection.getInputStream();
////
////            HlsPlaylistParser hlsPlaylistParser = new HlsPlaylistParser();
////          HlsPlaylist hlsPlaylist= hlsPlaylistParser.parse(Uri.parse(decryptedurl),stream);
////
////            for (int i = 0; i < hlsPlaylist.tags.size(); i++) {
////                System.out.println("myfinalurlis 1 datat =" + hlsPlaylist.tags.get(i));
////
////            }
//
//
//            //
////            List<String> lines = Collections.emptyList();
////
////                lines =
////                        Files.readAllLines(Paths.get(file.toString()), StandardCharsets.UTF_8);
////
////                for (int i = 0; i < lines.size(); i++) {
////                    if (lines.get(i).contains("http")) {
////                        System.out.println("wojfdjhfdjh qqqq master data = " + lines.get(i));
////                    }
////                }
//
//
//            URL u2 = new URL(decryptedurl);
//            InputStream is2 = u2.openStream();
//
//            DataInputStream dis2 = new DataInputStream(is2);
//
//            byte[] buffer2 = new byte[1024];
//            int length2;
//
//            FileOutputStream fos2 = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/master.m3u8"));
//            while ((length2 = dis2.read(buffer2)) > 0) {
//                fos2.write(buffer2, 0, length2);
//            }
//
//            File masterfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/master.m3u8");
////
////                List<String> lines2 = Collections.emptyList();
////
////                    lines2 =
////                            Files.readAllLines(Paths.get(fileindex.toString()), StandardCharsets.UTF_8);
////
////
////                    for (int i = 0; i < lines2.size(); i++) {
////                        if (lines2.get(i).contains("http")) {
////                            System.out.println("wojfdjhfdjh qqqq datanew = " + lines2.get(i));
////                        } else {
////                            lines2.remove(i);
////                        }
////                    }
//
//
////                    new callM3u8Parser().execute(lines.get(2));
//
//
//            MasterPlaylistParser parser = new MasterPlaylistParser();
//
//// Parse playlist
//            MasterPlaylist playlist = parser.readPlaylist(Paths.get(masterfile.toString()));
//
//
//            MasterPlaylist updated = MasterPlaylist.builder()
//                    .from(playlist)
//                    .version(2)
//                    .build();
//
//
//            for (int i = 0; i < updated.variants().size(); i++) {
//
//                System.out.println("myfinalurlis playlist media =" + updated.variants().get(i).uri());
//
//
//                URL u3 = new URL(updated.variants().get(i).uri());
//                InputStream is3 = u3.openStream();
//
//                DataInputStream dis3 = new DataInputStream(is3);
//
//                byte[] buffer3 = new byte[1024];
//                int length3;
//
//                FileOutputStream fos3 = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/index_0_a.m3u8"));
//                while ((length3 = dis3.read(buffer3)) > 0) {
//                    fos3.write(buffer2, 0, length3);
//                }
//
//                File indexfilew = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/index_0_a.m3u8");
//
//
//                List<String> lines2 = Collections.emptyList();
//
//                lines2 =
//                        Files.readAllLines(Paths.get(indexfilew.toString()), StandardCharsets.UTF_8);
//
//
//                for (int j = 0; j < lines2.size(); j++) {
//                    if (lines2.get(j).contains("http")) {
//                        System.out.println("myfinalurlis qqqq datanew = " + lines2.get(j));
//                    } else {
//                        lines2.remove(j);
//                    }
//                }
//
//
////                MasterPlaylist playlist3 = parser.readPlaylist(Paths.get(indexfilew.toString()));
////
////
////                MasterPlaylist updated3 = MasterPlaylist.builder()
////                        .from(playlist3)
////                        .version(2)
////                        .build();
////
////                System.out.println("myfinalurlis playlist media 222 ="+updated3.variants().get(0).uri());
//
////
////                MasterPlaylist masterPlaylist = MasterPlaylist.builder()
////                        .version(4)
////                        .independentSegments(true)
////                        .addAlternativeRenditions(AlternativeRendition.builder()
////                                .type(MediaType.AUDIO)
////                                .name("Default audio")
////                                .groupId("AUDIO")
////                                .build())
////                        .addVariants(
////                                Variant.builder()
////                                        .addCodecs("avc1.4d401f", "mp4a.40.2")
////                                        .bandwidth(900000)
////                                        .uri("v0.m3u8")
////                                        .build(),
////                                Variant.builder()
////                                        .addCodecs("avc1.4d401f", "mp4a.40.2")
////                                        .bandwidth(900000)
////                                        .uri("v1.m3u8")
////                                        .resolution(1280, 720)
////                                        .build())
////                        .build();
////
////                MasterPlaylistParser masterPlaylistParser2 = new MasterPlaylistParser();
////                System.out.println(masterPlaylistParser2.writePlaylistAsString(masterPlaylist));
//
//
//            }
//
//
//        } catch (Exception e) {
//            Log.e("MyTag", e.toString());
//        }
//        return videoModels1;
//
//
//    }
//

//    private static class callM3u8Parser extends AsyncTask<String, Void, Playlist> {
//        Playlist ShareChatDoc;
//
//        callM3u8Parser() {
//        }
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        protected Playlist doInBackground(String... strArr) {
//            InputStream in = null;
//            try {
//                in = new FileInputStream(strArr.toString());
//                ShareChatDoc = Playlist.parse(in);
//                in.close();
//                return ShareChatDoc;
//            } catch (Exception e) {
//
//            }
//            return this.ShareChatDoc;
//        }
//
//        protected void onPostExecute(Playlist document) {
//            String charSequence = "";
//
//            try {
//
//              dismissMyDialog();
//
//                charSequence = new PlaylistFormat(document).format();
//
//                System.out.println("dsahjdasdagdasd =" + charSequence);
//
//            } catch (Exception document22) {
//                document22.printStackTrace();
//                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    static class PlaylistFormat {
//        private final Playlist pl;
//
//        PlaylistFormat(Playlist pl) {
//            if (pl == null) {
//                throw new NullPointerException("pl");
//            }
//            this.pl = pl;
//        }
//
//        public String format() {
//            StringBuffer buf = new StringBuffer(100);
//
//            String NEW_LINE = "\n";
//            String ELEMENT = "\n\t";
//
//            buf.append("Playlist").append(NEW_LINE).append("Media Sequence No: ").append(pl.getMediaSequenceNumber())
//                    .append(" Target Duration: ").append(pl.getTargetDuration()).append(NEW_LINE);
//
//
//            int index = 0;
//            for (net.chilicat.m3u8.Element el : pl) {
//                buf.append(ELEMENT).append(index).append(": ").append(" Dur: ").append(el.getExactDuration()).append(" DIS: ").append(el.isDiscontinuity()).append(" URI: ").append(el.getURI()).append(" Title: ").append(el.getTitle());
//            }
//            return buf.toString();
//        }
//    }


    //TODO youtube comment them from here

//    private static void getYoutubeDownloadUrl(String youtubeLink) {
//
//        new YouTubeExtractor(Mcontext) {
//
//            @Override
//            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
//                //    mainProgressBar.setVisibility(View.GONE);
//
//                if (ytFiles != null) {
//
//                   dismissMyDialog();
//
//
//                    windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
//                    LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                    mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);
//
//                    mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);
//
//                    img_dialog = mChatHeadView.findViewById(R.id.img_dialog);
//
//
//                    dialogquality = new Dialog(Mcontext);
//                    dialogquality.setContentView(R.layout.dialog_quality_ytd);
//                    mainLayout = dialogquality.findViewById(R.id.linlayout_dialog);
//                    img_dialog = dialogquality.findViewById(R.id.img_dialog);
//                    show_ytd_inpip = dialogquality.findViewById(R.id.show_ytd_inpip);
//
//                    show_ytd_inpip.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            for (int i = 0, itag; i < ytFiles.size(); i++) {
//                                itag = ytFiles.keyAt(i);
//                                // ytFile represents one file with its url and meta data
//                                YtFile ytFile = ytFiles.get(itag);
//
//                                // Just add videos in a decent format => height -1 = audio
//                                if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
//                                    // addButtonToMainLayouttest(vMeta.getTitle(), ytFile);
//
//                                    Mcontext.startActivity(new Intent(Mcontext, PlayActivity.class).putExtra("videourl", ytFile.getUrl()).putExtra(AppMeasurementSdk.ConditionalUserProperty.NAME, vMeta.getTitle()));
//                                    return;
//                                }
//                            }
//
//
//                        }
//                    });
//
//
//                    int size = 0;
//
//                    try {
//                        DisplayMetrics displayMetrics = new DisplayMetrics();
//                        ((Activity) Mcontext).getWindowManager()
//                                .getDefaultDisplay()
//                                .getMetrics(displayMetrics);
//
//                        int height = displayMetrics.heightPixels;
//                        int width = displayMetrics.widthPixels;
//
//                        size = width / 2;
//
//                    } catch (Exception e) {
//                        size = WindowManager.LayoutParams.WRAP_CONTENT;
//                    }
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        params = new WindowManager.LayoutParams(
//                                size,
//                                WindowManager.LayoutParams.WRAP_CONTENT,
//                                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                PixelFormat.TRANSLUCENT);
//
//                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                        params.x = 0;
//                        params.y = 100;
//                    } else {
//                        params = new WindowManager.LayoutParams(
//                                size,
//                                WindowManager.LayoutParams.WRAP_CONTENT,
//                                WindowManager.LayoutParams.TYPE_PHONE,
//                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                PixelFormat.TRANSLUCENT);
//
//                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                        params.x = 0;
//                        params.y = 100;
//                    }
//
//
//                    // mainLayout.setLayoutParams(params);
//
//
//                    for (int i = 0, itag; i < ytFiles.size(); i++) {
//                        itag = ytFiles.keyAt(i);
//                        // ytFile represents one file with its url and meta data
//                        YtFile ytFile = ytFiles.get(itag);
//
//                        // Just add videos in a decent format => height -1 = audio
//                        if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
//                            addButtonToMainLayouttest(vMeta.getTitle(), ytFile);
//                        }
//                    }
//
//                    img_dialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialogquality.dismiss();
//                        }
//                    });
//
//                    dialogquality.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//                    dialogquality.getWindow().setAttributes(params);
//                    //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));
//
//                    dialogquality.show();
//
//                } else {
//                   dismissMyDialog();
//
//                    Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
//
//
//                }
//
//
//            }
//        }.extract(youtubeLink, true, false);
//    }
//
//    private static void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
//
//
//        // Display some buttons and let the user choose the format
//        String btnText = (ytfile.getFormat().getHeight() == -1) ? "Audio " +
//                ytfile.getFormat().getAudioBitrate() + " kbit/s" :
//                ytfile.getFormat().getHeight() + "p";
//        btnText += (ytfile.getFormat().isDashContainer()) ? " dash" : "";
//        Button btn = new Button(Mcontext);
//
//        btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//
//        btn.setText(btnText);
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (windowManager2 != null) {
//                    try {
//                        windowManager2.removeView(mChatHeadView);
//                    } catch (Exception e) {
//                        Log.i("LOGClipboard111111", "error is " + e.getMessage());
//
//                    }
//                }
//
//                String filename;
//                if (videoTitle.length() > 55) {
//                    filename = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
//                } else {
//                    filename = videoTitle + "." + ytfile.getFormat().getExt();
//                }
//                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
//
////                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
////
////
////                String downloadUrl = ytFiles.get(itag).getUrl();
//
//
//                new downloadFile().Downloading(Mcontext, ytfile.getUrl(), filename, ".mp4");
//
//
//                dialogquality.dismiss();
//            }
//        });
//        mainLayout.addView(btn);
//    }
//
//    private static void addButtonToMainLayouttest(final String videoTitle, final YtFile ytfile) {
//
//
//        // Display some buttons and let the user choose the format
//        String btnText = (ytfile.getFormat().getHeight() == -1) ? "MP3 " +
//                ytfile.getFormat().getAudioBitrate() + " kbit/s" :
//                ytfile.getFormat().getHeight() + "p";
//        btnText += (ytfile.getFormat().isDashContainer()) ? " No Audio" : "";
//        Button btn = new Button(Mcontext);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        params.setMargins(8, 8, 8, 8);
//        btn.setLayoutParams(params);
//
//        // btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        btn.setBackground(Mcontext.getResources().getDrawable(R.drawable.btn_bg_download_screen));
//        btn.setTextColor(Color.WHITE);
//
//        btn.setText(btnText);
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (windowManager2 != null) {
//                    try {
//                        windowManager2.removeView(mChatHeadView);
//                    } catch (Exception e) {
//                        Log.i("LOGClipboard111111", "error is " + e.getMessage());
//
//                    }
//                }
//
//                String filename;
//                if (videoTitle.length() > 55) {
//                    filename = videoTitle.substring(0, 55);
//                } else {
//                    filename = videoTitle;
//                }
//                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
//
////                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
////
////
////                String downloadUrl = ytFiles.get(itag).getUrl();
//
//
//                if (ytfile.getFormat().getExt().equals("m4a")) {
//                    new downloadFile().Downloading(Mcontext, ytfile.getUrl(), filename, ".mp3");
//                } else {
//                    new downloadFile().Downloading(Mcontext, ytfile.getUrl(), filename, "." + ytfile.getFormat().getExt());
//
//                }
//
//                dialogquality.dismiss();
//            }
//        });
//        mainLayout.addView(btn);
//    }


    //TODO youtube comment till here


    public static void dismissMyDialog() {

        if (pd != null && pd.isShowing() && !fromService && !((Activity) Mcontext).isFinishing()) {
            pd.dismiss();
        }
    }

    public static void dismissMyDialogErrortoast() {

        if (pd != null && pd.isShowing() && !fromService && !((Activity) Mcontext).isFinishing()) {
            pd.dismiss();
            Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();

        }
    }

    public static void download(String url12) {
        String readLine;
        URL url = null;
        try {
            url = new URL(url12);


            Log.d("ThumbnailURL11111_1 ", url12);


//        URLConnection openConnection = url.openConnection();
//        openConnection.setRequestProperty("ModelUserInstagram-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            //       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));


            URL url1 = new URL(url12);
            URLConnection connection = url1.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));


            while ((readLine = bufferedReader.readLine()) != null) {
                //  readLine = bufferedReader.readLine();
                Log.d("ThumbnailURL11111_2  ", readLine);


                readLine = readLine.substring(readLine.indexOf("VideoObject"));
                String substring = readLine.substring(readLine.indexOf("thumbnailUrl") + 16);
                substring = substring.substring(0, substring.indexOf("\""));

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ThumbnailURL: ");
                stringBuilder.append(substring);

                Log.d("ThumbnailURL", substring);
                readLine = readLine.substring(readLine.indexOf("contentUrl") + 13);
                readLine = readLine.substring(0, readLine.indexOf("?"));
                stringBuilder = new StringBuilder();
                stringBuilder.append("ContentURL: ");
                stringBuilder.append(readLine);

                Log.d("ContentURL1111 thumb  ", substring);
                Log.d("ContentURL1111", stringBuilder.toString());


                if (readLine == null) {
                    break;
                }
            }
            bufferedReader.close();

        } catch (Exception e) {
//            Log.d("ContentURL1111 errrr", e.getMessage());
            e.printStackTrace();
        }
        // new downloadFile().Downloading(Mcontext, URL, Title, ".mp4");
        //   new DownloadFileFromURL().execute(new String[]{readLine});
    }


//    @Keep
//    public static void getAllDataForLikee2(String url, boolean hasQualityOption) {
//
//        AndroidNetworking.get(LikeeApiUrl + url)
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        System.out.println("reccccc VVKK " + response);
//
//
//                        System.out.println("rescccccccc " + response + "     myurl is " + LikeeApiUrl + url);
//
//                        ArrayList<VideoModel> videoModelArrayList = new ArrayList<>();
//
//                        try {
//                            // JSONObject jSONObject = new JSONObject(response);
//
//                            String videotitleis = response.getString("title");
//
//                            JSONArray str = response.getJSONArray("links");
//
//
//                            for (int i = 0; i < str.length(); i++) {
//                                VideoModel videoModel = new VideoModel();
//                                JSONObject jSONObject2 = str.getJSONObject(i);
//                                videoModel.setTitle(videotitleis);
//                                videoModel.setUrl(jSONObject2.getString("url"));
//
//
//                                System.out.println("reccccc VVKK URLLL " + jSONObject2.getString("url"));
//
//
//                                videoModel.setType(jSONObject2.getString("type"));
//                                videoModel.setSize(jSONObject2.getString("size"));
//                                videoModel.setQuality(jSONObject2.getString("quality"));
//
//                                videoModelArrayList.add(videoModel);
//
//                            }
//
//
//                            if (hasQualityOption) {
//
//                                dialog_quality_allvids = new Dialog(Mcontext);
//
//
//                               dismissMyDialog();
//
//
//                                windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
//                                LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                                mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);
//
//
//                                dialog_quality_allvids.setContentView(mChatHeadView);
//
//
//                                mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);
//
//                                img_dialog = mChatHeadView.findViewById(R.id.img_dialog);
//
//                                mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
//                                img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);
//
//
//                                int size = 0;
//
//                                try {
//                                    DisplayMetrics displayMetrics = new DisplayMetrics();
//                                    ((Activity) Mcontext).getWindowManager()
//                                            .getDefaultDisplay()
//                                            .getMetrics(displayMetrics);
//
//                                    int height = displayMetrics.heightPixels;
//                                    int width = displayMetrics.widthPixels;
//
//                                    size = width / 2;
//
//                                } catch (Exception e) {
//                                    size = WindowManager.LayoutParams.WRAP_CONTENT;
//                                }
//
//
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    params = new WindowManager.LayoutParams(
//                                            size,
//                                            WindowManager.LayoutParams.WRAP_CONTENT,
//                                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                            PixelFormat.TRANSLUCENT);
//
//                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                                    params.x = 0;
//                                    params.y = 100;
//                                } else {
//                                    params = new WindowManager.LayoutParams(
//                                            size,
//                                            WindowManager.LayoutParams.WRAP_CONTENT,
//                                            WindowManager.LayoutParams.TYPE_PHONE,
//                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                            PixelFormat.TRANSLUCENT);
//
//                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                                    params.x = 0;
//                                    params.y = 100;
//                                }
//
//
//                                // mainLayout.setLayoutParams(params);
//
//
//                                for (int i = 0; i < videoModelArrayList.size(); i++) {
//
//
//
//                                    addButtonToMainLayouttest_allvideo(videoModelArrayList.get(i).getQuality(), videoModelArrayList.get(i).getUrl(), videoModelArrayList.get(i).getTitle());
//
//                                }
//
//                                img_dialog.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialog_quality_allvids.dismiss();
//                                    }
//                                });
//
//                                dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//                                dialog_quality_allvids.getWindow().setAttributes(params);
//                                //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));
//
//                                dialog_quality_allvids.show();
//
//
//                                dialog_quality_allvids.show();
//                            } else {
//                                if (url.contains("tiktok")) {
//
//                                    String outputFileName = MY_ANDROID_10_IDENTIFIER_OF_FILE + getFilenameFromURL("https://www.tiktok.com/@beauty_0f_nature/video/6825315100933639426") + ".mp4";
//                                    String output = "";
//
//                                    if (outputFileName.length() > 100)
//                                        outputFileName = outputFileName.substring(0, 100);
//
//
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                                        output = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + outputFileName;
//
//                                    } else {
//                                        output = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + outputFileName;
//
//                                        //   output = new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + DOWNLOAD_DIRECTORY + "/" + outputFileName));
//
//                                    }
//
//
//                                    //  new DownloadFileFromURL1().execute("https://v16-web-newkey.tiktokcdn.com/99818623022622f41b12900e3d086eb4/5f6e1cf9/video/tos/useast2a/tos-useast2a-ve-0068c003/590e2a192cb8428ca4b2509167b01bfa/?a=1988&br=4926&bt=2463&cr=0&cs=0&cv=1&dr=0&ds=3&er=&l=202009251038010101151510600F065F52&lr=tiktok_m&mime_type=video_mp4&qs=0&rc=ajVqbW52cWdzdTMzZzczM0ApNTQ3ZTo2aDxoNzVoaDs6NWdsMWwzajNnc29fLS1fMTZzc19hYGExNTUxX2MxLl8zMDY6Yw%3D%3D&vl=&vr=");
//                                    AndroidNetworking
//                                            .download("https://v16-web-newkey.tiktokcdn.com/99818623022622f41b12900e3d086eb4/5f6e1cf9/video/tos/useast2a/tos-useast2a-ve-0068c003/590e2a192cb8428ca4b2509167b01bfa/?a=1988&br=4926&bt=2463&cr=0&cs=0&cv=1&dr=0&ds=3&er=&l=202009251038010101151510600F065F52&lr=tiktok_m&mime_type=video_mp4&qs=0&rc=ajVqbW52cWdzdTMzZzczM0ApNTQ3ZTo2aDxoNzVoaDs6NWdsMWwzajNnc29fLS1fMTZzc19hYGExNTUxX2MxLl8zMDY6Yw%3D%3D&vl=&vr="
//                                                    , output, outputFileName)
//                                            .setTag("downloadTest")
//                                            .setPriority(Priority.MEDIUM)
//                                            .build()
//                                            .setDownloadProgressListener(new DownloadProgressListener() {
//                                                @Override
//                                                public void onProgress(long bytesDownloaded, long totalBytes) {
//                                                    // do anything with progress
//                                                }
//                                            })
//                                            .startDownload(new DownloadListener() {
//                                                @Override
//                                                public void onDownloadComplete() {
//                                                    dismissMyDialogErrortoast();
//                                                }
//
//                                                @Override
//                                                public void onError(ANError error) {
//                                             dismissMyDialog();
//                                                }
//                                            });
//                                } else {
//
//                                    new downloadFile().Downloading(Mcontext, videoModelArrayList.get(0).getUrl(), getFilenameFromURL(videoModelArrayList.get(0).getUrl()), ".mp4");
//                                }
//                           dismissMyDialog();
//                            }
//
//
//                        } catch (Exception str2) {
//                            str2.printStackTrace();
//                            // Toast.makeText(Mcontext, "Invalid URL", 0).show();
//
//                           dismissMyDialogErrortoast();
//                        }
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        System.out.println("reccccc VVKK error " + error);
//
//                    }
//                });
//    }
//
//


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private static void addButtonToMainLayouttest_allvideo(final String videoTitle, String ytfile, String video_title) {
        try {

            // Display some buttons and let the user choose the format
            String btnText = videoTitle;
            Button btn = new Button(Mcontext);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            btn.setLayoutParams(params);

            // btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setBackground(Mcontext.getResources().getDrawable(R.drawable.btn_bg_download_screen));
            btn.setTextColor(Color.WHITE);

            btn.setText(btnText);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (windowManager2 != null) {
                        try {
                            windowManager2.removeView(mChatHeadView);
                        } catch (Exception e) {
                            Log.i("LOGClipboard111111", "error is " + e.getMessage());

                        }
                    }


//                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
//
//
//                String downloadUrl = ytFiles.get(itag).getUrl();

                    if (btnText.equals("audio/mp4")) {
                        DownloadFileMain.startDownloading(Mcontext, ytfile, video_title + "_" + videoTitle, ".mp3");
                    } else {
                        DownloadFileMain.startDownloading(Mcontext, ytfile, video_title + "_" + videoTitle, ".mp4");

                    }
                    dialog_quality_allvids.dismiss();
                }
            });
            mainLayout.addView(btn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callSnackVideoResult(String URL, String shortKey, String os, String sig, String client_key) {


        RetrofitApiInterface apiService = RetrofitClient.getClient().create(RetrofitApiInterface.class);


        Call<JsonObject> callResult = apiService.getsnackvideoresult(URL + "&" + shortKey + "&" + os + "&sig=" + sig + "&" + client_key);


        callResult.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {


                assert response.body() != null;
                VideoUrl = response.body().getAsJsonObject("photo").get("main_mv_urls").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();


                System.out.println("response1122334455worURL:   " + VideoUrl);

                if (!VideoUrl.equals("")) {


                    try {

                        dismissMyDialog();


                        String myurldocument = VideoUrl;


                        String nametitle = "snackvideo_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                System.out.println("response1122334455:   " + "Failed0 " + call);

                dismissMyDialog();

            }
        });

    }

    @SuppressLint("NewApi")
    public static void getSnackVideoData(String str, Context vc) {
        URI uri;
        try {
            uri = new URI(str);
        } catch (Exception e) {
            e.printStackTrace();
            uri = null;
            dismissMyDialog();
        }
        assert uri != null;
        String[] uripath = uri.getPath().split("/");
        String uripath2 = uripath[uripath.length - 1];
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("mod=OnePlus(ONEPLUS A5000)");
        arrayList.add("lon=0");
        arrayList.add("country_code=in");
        String mydid = "did=" +
                "ANDROID_" + Settings.Secure.getString(vc.getContentResolver(), "android_id");

        arrayList.add(mydid);
        arrayList.add("app=1");
        arrayList.add("oc=UNKNOWN");
        arrayList.add("egid=");
        arrayList.add("ud=0");
        arrayList.add("c=GOOGLE_PLAY");
        arrayList.add("sys=KWAI_BULLDOG_ANDROID_9");
        arrayList.add("appver=2.7.1.153");
        arrayList.add("mcc=0");
        arrayList.add("language=en-in");
        arrayList.add("lat=0");
        arrayList.add("ver=2.7");


        ArrayList arrayList2 = new ArrayList(arrayList);

        String shortKey = "shortKey=" +
                uripath2;
        arrayList2.add(shortKey);

        String os = "os=" +
                "android";
        arrayList2.add(os);
        String client_key = "client_key=" +
                "8c46a905";
        arrayList2.add(client_key);

        try {
            Collections.sort(arrayList2);

        } catch (Exception str225) {
            str225.printStackTrace();
            dismissMyDialog();
        }


        String clockData = CPU.getClockData(Mcontext, TextUtils.join("", arrayList2).getBytes(StandardCharsets.UTF_8), 0);

        String nowaterurl = "https://g-api.snackvideo.com/rest/bulldog/share/get?" + TextUtils.join("&", arrayList);

        System.out.println("respossss112212121q " + nowaterurl + "_______" + shortKey + os + clockData + client_key);


        callSnackVideoResult(nowaterurl, shortKey, os, clockData, client_key);


    }

    @Keep
    public static void CallVKData(String url, boolean hasQualityOption) {
        if (hasQualityOption) {
            AndroidNetworking.get("https://api.vk.com/method/video.search?q=" + url + "&from=wall-51189706_396016&oauth=1&search_own=0&adult=0&search_own=0&count=1&extended=1&files=1&access_token=d9f1c406aeec6341131a62556d9eb76c7fe6d53defca0d9ce54535299664abf46e0a37af79004c30eb9b3&v=5.124")
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println("reccccc VVKK " + response);

                            try {
                                JSONObject reponsobj = response.getJSONObject("response");
                                JSONObject itemsarr = reponsobj.getJSONArray("items").getJSONObject(0);
                                JSONObject filesobj = itemsarr.getJSONObject("files");


                                ArrayList<String> mp4List = new ArrayList<>();
                                ArrayList<String> qualitylist = new ArrayList<>();


                                if (!filesobj.getString("mp4_240").isEmpty()) {
                                    String mp4_240 = filesobj.getString("mp4_240");
                                    mp4List.add(mp4_240);
                                    qualitylist.add("240p");
                                }
                                if (!filesobj.getString("mp4_360").isEmpty()) {
                                    String mp4_360 = filesobj.getString("mp4_360");
                                    mp4List.add(mp4_360);
                                    qualitylist.add("360p");

                                }
                                if (!filesobj.getString("mp4_480").isEmpty()) {
                                    String mp4_480 = filesobj.getString("mp4_480");
                                    mp4List.add(mp4_480);
                                    qualitylist.add("480p");

                                }
                                if (!filesobj.getString("mp4_720").isEmpty()) {
                                    String mp4_720 = filesobj.getString("mp4_720");
                                    mp4List.add(mp4_720);
                                    qualitylist.add("720p");

                                }
                                if (!filesobj.getString("mp4_1080").isEmpty()) {
                                    String mp4_1080 = filesobj.getString("mp4_1080");
                                    mp4List.add(mp4_1080);
                                    qualitylist.add("1080p");

                                }


                                if (hasQualityOption) {
                                    if (!((Activity) Mcontext).isFinishing()) {

                                        dialog_quality_allvids = new Dialog(Mcontext);


                                        dismissMyDialog();


                                        windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
                                        LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                        mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);


                                        dialog_quality_allvids.setContentView(mChatHeadView);


                                        mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);

                                        img_dialog = mChatHeadView.findViewById(R.id.img_dialog);

                                        mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
                                        img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);


                                        int size = 0;

                                        try {
                                            DisplayMetrics displayMetrics = new DisplayMetrics();
                                            ((Activity) Mcontext).getWindowManager()
                                                    .getDefaultDisplay()
                                                    .getMetrics(displayMetrics);

                                            int height = displayMetrics.heightPixels;
                                            int width = displayMetrics.widthPixels;

                                            size = width / 2;

                                        } catch (Exception e) {
                                            size = WindowManager.LayoutParams.WRAP_CONTENT;
                                        }


                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            params = new WindowManager.LayoutParams(
                                                    size,
                                                    WindowManager.LayoutParams.WRAP_CONTENT,
                                                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                                    PixelFormat.TRANSLUCENT);

                                            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                            params.x = 0;
                                            params.y = 100;
                                        } else {
                                            params = new WindowManager.LayoutParams(
                                                    size,
                                                    WindowManager.LayoutParams.WRAP_CONTENT,
                                                    WindowManager.LayoutParams.TYPE_PHONE,
                                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                                    PixelFormat.TRANSLUCENT);

                                            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                            params.x = 0;
                                            params.y = 100;
                                        }


                                        // mainLayout.setLayoutParams(params);


                                        for (int i = 0; i < mp4List.size(); i++) {


                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4List.get(i), "VK_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        }

                                        img_dialog.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog_quality_allvids.dismiss();
                                            }
                                        });

                                        dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                                        dialog_quality_allvids.getWindow().setAttributes(params);
                                        //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));

                                        dialog_quality_allvids.show();


                                        dialog_quality_allvids.show();


                                    }
                                } else {


                                    DownloadFileMain.startDownloading(Mcontext, mp4List.get(0), "VK_240p" + System.currentTimeMillis(), ".mp4");

                                    dismissMyDialog();
                                }


                            } catch (Exception str2) {
                                str2.printStackTrace();
                                // Toast.makeText(Mcontext, "Invalid URL", 0).show();

                                dismissMyDialogErrortoast();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            System.out.println("reccccc VVKK error " + error);

                        }
                    });

        } else {
            AndroidNetworking.get(DlApisUrl2 + url + "&flatten=True")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println("reccccc VVKK " + response);
                            // Log.e("myresponse ",response.toString());
                            ArrayList<String> mp4List = new ArrayList<>();
                            ArrayList<String> qualitylist = new ArrayList<>();
                            try {
                                String url1 = response.getJSONArray("videos").getJSONObject(0).getJSONArray("formats").getJSONObject(0).getString("url");
                                String name1 = response.getJSONArray("videos").getJSONObject(0).getJSONArray("formats").getJSONObject(0).getString("format");

                                mp4List.add(url1);
                                qualitylist.add(name1);

                                String url2 = response.getJSONArray("videos").getJSONObject(0).getJSONArray("formats").getJSONObject(1).getString("url");
                                String name2 = response.getJSONArray("videos").getJSONObject(0).getJSONArray("formats").getJSONObject(1).getString("format");

                                mp4List.add(url2);
                                qualitylist.add(name2);

                                String url3 = response.getJSONArray("videos").getJSONObject(0).getJSONArray("formats").getJSONObject(2).getString("url");
                                String name3 = response.getJSONArray("videos").getJSONObject(0).getJSONArray("formats").getJSONObject(2).getString("format");

                                mp4List.add(url3);
                                qualitylist.add(name3);


                                dialog_quality_allvids = new Dialog(Mcontext);


                                dismissMyDialog();

                                windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
                                LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);


                                dialog_quality_allvids.setContentView(mChatHeadView);


                                mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);

                                img_dialog = mChatHeadView.findViewById(R.id.img_dialog);

                                mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
                                img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);


                                int size = 0;

                                try {
                                    DisplayMetrics displayMetrics = new DisplayMetrics();
                                    ((Activity) Mcontext).getWindowManager()
                                            .getDefaultDisplay()
                                            .getMetrics(displayMetrics);

                                    int height = displayMetrics.heightPixels;
                                    int width = displayMetrics.widthPixels;

                                    size = width / 2;

                                } catch (Exception e) {
                                    size = WindowManager.LayoutParams.WRAP_CONTENT;
                                }


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    params = new WindowManager.LayoutParams(
                                            size,
                                            WindowManager.LayoutParams.WRAP_CONTENT,
                                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                            PixelFormat.TRANSLUCENT);

                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                    params.x = 0;
                                    params.y = 100;
                                } else {
                                    params = new WindowManager.LayoutParams(
                                            size,
                                            WindowManager.LayoutParams.WRAP_CONTENT,
                                            WindowManager.LayoutParams.TYPE_PHONE,
                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                            PixelFormat.TRANSLUCENT);

                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                    params.x = 0;
                                    params.y = 100;
                                }


                                // mainLayout.setLayoutParams(params);


                                for (int i = 0; i < mp4List.size(); i++) {


                                    addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4List.get(i), "VK_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                }

                                img_dialog.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_quality_allvids.dismiss();
                                    }
                                });

                                dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                                dialog_quality_allvids.getWindow().setAttributes(params);
                                //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));

                                dialog_quality_allvids.show();


                                dialog_quality_allvids.show();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(ANError error) {
                            System.out.println("reccccc VVKK error " + error.getErrorBody());
                            dismissMyDialogErrortoast();
                        }
                    });
        }
    }

    private static void splitDataToVideoAndAudio_video(List<Video> videoList, RecyclerView recyclerView_video, RecyclerView recyclerView_audio, QualityBottomsheetAdapter qualityBottomsheetAdapter, String extractor) {

        List<Video> videoList_sub = new ArrayList<>();
        List<Video> videoList_sub_video = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {

            if (videoList.get(i).getProtocol().contains("http") && !videoList.get(i).getProtocol().contains("http_dash_segments") && !videoList.get(i).getURL().contains(".m3u8")) {

                System.out.println("fhnsdkjhffjhsfhsdfjksdfsdhfkjs vifro= " + videoList.get(i).getURL());


                if (videoList.get(i).getEXT().equals("m4a") ||
                        videoList.get(i).getEXT().equals("mp3") ||
                        videoList.get(i).getEXT().equals("wav")) {
                    videoList_sub.add(videoList.get(i));
                } else if (videoList.get(i).getEXT().equals("mp4") || videoList.get(i).getEXT().equals("mpeg")) {

                    videoList_sub_video.add(videoList.get(i));

                }
            }
        }

        Collections.reverse(videoList_sub_video);


        qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, videoList_sub_video.get(0).getExtractor(), false, videoList_sub_video, true);
        recyclerView_video.setAdapter(qualityBottomsheetAdapter);

        qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, videoList_sub.get(0).getExtractor(), false, videoList_sub, true);
        recyclerView_audio.setAdapter(qualityBottomsheetAdapter);


    }

    private static void splitDataToVideoAndAudio_format(List<Format> formatList, RecyclerView recyclerView_video, RecyclerView recyclerView_audio, QualityBottomsheetAdapter qualityBottomsheetAdapter, String extractor) {


        List<Format> formatList_sub = new ArrayList<>();
        List<Format> formatList_sub_video = new ArrayList<>();
        for (int i = 0; i < formatList.size(); i++) {


            if (formatList.get(i).getProtocol().contains("http") && !formatList.get(i).getProtocol().contains("http_dash_segments") && !formatList.get(i).getURL().contains(".m3u8")) {
                System.out.println("fhnsdkjhffjhsfhsdfjksdfsdhfkjs formate= " + formatList.get(i).getAcodec());

                if (formatList.get(i).getAcodec() != null && !formatList.get(i).getAcodec().equals("none")) {

                    if (formatList.get(i).getEXT().equals("m4a") ||
                            formatList.get(i).getEXT().equals("mp3") ||
                            formatList.get(i).getEXT().equals("wav")) {
                        formatList_sub.add(formatList.get(i));
                    } else if (formatList.get(i).getEXT().equals("mp4") || formatList.get(i).getEXT().equals("mpeg")) {

                        formatList_sub_video.add(formatList.get(i));

                    }
                } else {

                    if (formatList.get(i).getEXT().equals("m4a") ||
                            formatList.get(i).getEXT().equals("mp3") ||
                            formatList.get(i).getEXT().equals("wav")) {
                        formatList_sub.add(formatList.get(i));
                    } else if (formatList.get(i).getEXT().equals("mp4") || formatList.get(i).getEXT().equals("mpeg")) {

                        formatList_sub_video.add(formatList.get(i));

                    }

                    formatList.get(i).setFormat("(no audio) " + formatList.get(i).getFormat());


                }

            }
        }

        Collections.reverse(formatList_sub_video);


        qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, formatList_sub_video, extractor, false);
        recyclerView_video.setAdapter(qualityBottomsheetAdapter);

        qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, formatList_sub, extractor, false);
        recyclerView_audio.setAdapter(qualityBottomsheetAdapter);


    }

    @Keep
    public static void CalldlApisDataData(String url, boolean hasQualityOption) {

        Random rand = new Random();
        int rand_int1 = rand.nextInt(3);
        System.out.println("randonvalueis = " + rand_int1);
        System.out.println("reccc " + url);

        if (rand_int1 == 0) {
            AndroidNetworking.get(DlApisUrl + url + "&flatten=True")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println("reccccc VVKK " + response);
                            // Log.e("myresponse ",response.toString());

                            parseCalldlApisDataData(response);


                        }

                        @Override
                        public void onError(ANError error) {
                            System.out.println("reccccc VVKK error " + error.getErrorBody());
                            dismissMyDialogErrortoast();
                        }
                    });
        } else if (rand_int1 == 1) {
            AndroidNetworking.get(DlApisUrl2 + url + "&flatten=True")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println("reccccc VVKK " + response);
                            // Log.e("myresponse ",response.toString());

                            parseCalldlApisDataData(response);


                        }

                        @Override
                        public void onError(ANError error) {
                            System.out.println("reccccc VVKK error " + error.getErrorBody());
                            dismissMyDialogErrortoast();
                        }
                    });
        } else if (rand_int1 == 2) {
            AndroidNetworking.get(DlApisUrl3 + url + "&flatten=True")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println("reccccc VVKK " + response);
                            // Log.e("myresponse ",response.toString());

                            parseCalldlApisDataData(response);


                        }

                        @Override
                        public void onError(ANError error) {
                            System.out.println("reccccc VVKK error " + error.getErrorBody());
                            dismissMyDialogErrortoast();
                        }
                    });
        }
    }

    @Keep
    public static void parseCalldlApisDataData(JSONObject response) {
        try {

            try {
                if (!response.getString("error").equals("")) {
                    // Toast.makeText(Mcontext, "Invalid URL", 0).show();

                    dismissMyDialogErrortoast();
                }

            } catch (Exception e) {

                Gson gson = new Gson();

                DLDataParser gsonObj = gson.fromJson(response.toString(), DLDataParser.class);

                System.out.println("reccccc VVKK " + gsonObj.getURL());


                View view = LayoutInflater.from(Mcontext).inflate(R.layout.bottomsheet_quality_layout, null);


                Button btncancel_bottomsheet = view.findViewById(R.id.btncancel_bottomsheet);
                Button btnopen_bottomsheet = view.findViewById(R.id.btnopen_bottomsheet);
                TextView source_bottomsheet = view.findViewById(R.id.source_bottomsheet);
                TextView title_bottomsheet = view.findViewById(R.id.bottomsheet_title);
                TextView duration_bottomsheet = view.findViewById(R.id.bottomsheet_duration);
                ImageView thumb_bottomsheet = view.findViewById(R.id.bottomsheet_thumbnail);

                RecyclerView recyclerView = view.findViewById(R.id.recqualitybottomsheet);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Mcontext));

                RecyclerView recyclerView_audio = view.findViewById(R.id.recqualitybottomsheet_aud);
                recyclerView_audio.setHasFixedSize(true);
                recyclerView_audio.setLayoutManager(new LinearLayoutManager(Mcontext));

                QualityBottomsheetAdapter qualityBottomsheetAdapter = null;

                System.out.println("reccc lengthe iss= " + response.getJSONArray("videos").length());

                if (response.getJSONArray("videos").length() > 1) {

                    System.out.println("reccccc VVKK 0 ");


                    System.out.println("reccccc VVKK 1 ");

                    if (response.getJSONArray("videos").getJSONObject(0).has("protocol")) {

                        System.out.println("reccccc VVKK 2");

//                                            String ishttp = response.getJSONArray("videos").getJSONObject(0).getString("protocol");
//                                            if (ishttp.contains("http")) {

                        System.out.println("reccccc VVKK 3 ");

                        splitDataToVideoAndAudio_video(gsonObj.getVideos(), recyclerView, recyclerView_audio, qualityBottomsheetAdapter, gsonObj.getVideos().get(0).getExtractor());
//                                        qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, gsonObj.getVideos().get(0).getExtractor(), false, gsonObj.getVideos(), true);
//                                        recyclerView.setAdapter(qualityBottomsheetAdapter);

//                                            } else {
//                                                qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, gsonObj.getVideos().get(0).getFormats(), gsonObj.getVideos().get(0).getExtractor(), false);
//                                                recyclerView.setAdapter(qualityBottomsheetAdapter);
//
//                                            }


                    }
                    //                                   else {
//                                        System.out.println("reccccc VVKK 4 ");
//
//                                        if (response.getJSONArray("videos").getJSONObject(0).getJSONObject("formats").has("protocol")) {
//
////                                            String ishttp = response.getJSONArray("videos").getJSONObject(0).getString("protocol");
////                                            if (ishttp.contains("http")) {
//                                                qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, gsonObj.getVideos().get(0).getFormats(), gsonObj.getVideos().get(0).getExtractor(), true);
//                                                recyclerView.setAdapter(qualityBottomsheetAdapter);
//
////                                            } else {
////                                                qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, gsonObj.getVideos().get(0).getURL(), gsonObj.getVideos().get(0).getExtractor(), true);
////                                                recyclerView.setAdapter(qualityBottomsheetAdapter);
////                                            }
//
//                                        }
//                                    }


                    BottomSheetDialog dialog = new BottomSheetDialog(Mcontext);

                    if (response.getJSONArray("videos").getJSONObject(0).has("extractor")) {
                        String styledText = "Source: <font color='red'>" + gsonObj.getVideos().get(0).getExtractor() + "</font>";
                        source_bottomsheet.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    }

                    if (response.getJSONArray("videos").getJSONObject(0).has("duration")) {

                        String mystring = gsonObj.getVideos().get(0).getDuration() + "";
                        String[] correctstring = mystring.split("\\.");

                        long hours = Long.parseLong(correctstring[0]) / 3600;
                        long minutes = (Long.parseLong(correctstring[0]) % 3600) / 60;
                        long seconds = Long.parseLong(correctstring[0]) % 60;

                        String DurationstyledText = "Duration: <font color='red'>" + String.format("%02d:%02d:%02d", hours, minutes, seconds) + "</font>";
                        duration_bottomsheet.setText(Html.fromHtml(DurationstyledText), TextView.BufferType.SPANNABLE);
                    }

                    if (response.getJSONArray("videos").getJSONObject(0).has("title")) {

                        System.out.println("reccccc VVKKtttt " + gsonObj.getVideos().get(0).getTitle());


                        String titletyledText = "Title: <font color='red'>" + String.format("%s", gsonObj.getVideos().get(0).getTitle()) + "</font>";
                        title_bottomsheet.setText(Html.fromHtml(titletyledText), TextView.BufferType.SPANNABLE);
                    }


                    if (response.getJSONArray("videos").getJSONObject(0).has("thumbnail")) {

                        Glide.with(Mcontext)
                                .load(gsonObj.getVideos().get(0).getThumbnail())
                                .into(thumb_bottomsheet);

                    }
                    // source_bottomsheet.setText(String.format("Source: %s", gsonObj.getVideos().get(0).getExtractor()));
                    btncancel_bottomsheet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    btnopen_bottomsheet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
                            dialog.getBehavior().setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
                        }
                    });

                    dialog.setContentView(view);
                    dialog.show();


                    dismissMyDialog();


                } else {

                    System.out.println("reccccc VVKK 6 ");

                    if (response.getJSONArray("videos").getJSONObject(0).has("formats")) {
                        System.out.println("reccccc VVKK 7 ");

                        //     if (response.getJSONArray("videos").getJSONObject(0).getJSONArray("formats").getJSONObject(0).has("protocol")) {


                        System.out.println("reccccc VVKK 8 ");
//
//
//                                            String ishttp = response.getJSONArray("videos").getJSONObject(0).getString("protocol");
//                                            if (ishttp.contains("http")) {
                        System.out.println("reccccc VVKK 9 ");
//                                               (Context context, List<Format> filesList, String source, boolean issingle)

                        splitDataToVideoAndAudio_format(gsonObj.getVideos().get(0).getFormats(), recyclerView, recyclerView_audio, qualityBottomsheetAdapter, gsonObj.getVideos().get(0).getExtractor());

//                                        qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, gsonObj.getVideos().get(0).getFormats(), gsonObj.getVideos().get(0).getExtractor(), false);
//                                        recyclerView.setAdapter(qualityBottomsheetAdapter);

//                                            } else {
//                                                qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, gsonObj.getVideos().get(0).getExtractor(), false, gsonObj.getVideos(), true);
//                                                recyclerView.setAdapter(qualityBottomsheetAdapter);
//
//                                            }

                        //    }

                    } else {
                        if (response.getJSONArray("videos").getJSONObject(0).has("protocol")) {

                            String ishttp = response.getJSONArray("videos").getJSONObject(0).getString("protocol");
                            if (ishttp.contains("http")) {
                                qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, gsonObj.getVideos().get(0).getURL(), gsonObj.getVideos().get(0).getExtractor(), true);
                                recyclerView.setAdapter(qualityBottomsheetAdapter);

                            }
//                                            else {
//                                                qualityBottomsheetAdapter = new QualityBottomsheetAdapter(Mcontext, gsonObj.getVideos().get(0).getURL(), gsonObj.getVideos().get(0).getExtractor(), true);
//                                                recyclerView.setAdapter(qualityBottomsheetAdapter);
//                                            }

                        }
                    }

                    if (!((Activity) Mcontext).isFinishing()) {

                        BottomSheetDialog dialog = new BottomSheetDialog(Mcontext);

                        if (response.getJSONArray("videos").getJSONObject(0).has("extractor")) {
                            String styledText = "Source: <font color='red'>" + gsonObj.getVideos().get(0).getExtractor() + "</font>";
                            source_bottomsheet.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                        }

                        if (response.getJSONArray("videos").getJSONObject(0).has("duration")) {

                            String mystring = gsonObj.getVideos().get(0).getDuration() + "";
                            String[] correctstring = mystring.split("\\.");

                            long hours = Long.parseLong(correctstring[0]) / 3600;
                            long minutes = (Long.parseLong(correctstring[0]) % 3600) / 60;
                            long seconds = Long.parseLong(correctstring[0]) % 60;

                            String DurationstyledText = "Duration: <font color='red'>" + String.format("%02d:%02d:%02d", hours, minutes, seconds) + "</font>";
                            duration_bottomsheet.setText(Html.fromHtml(DurationstyledText), TextView.BufferType.SPANNABLE);
                        }

                        if (response.getJSONArray("videos").getJSONObject(0).has("title")) {

                            System.out.println("reccccc VVKKtttt " + gsonObj.getVideos().get(0).getTitle());


                            String titletyledText = "Title: <font color='red'>" + String.format("%s", gsonObj.getVideos().get(0).getTitle()) + "</font>";
                            title_bottomsheet.setText(Html.fromHtml(titletyledText), TextView.BufferType.SPANNABLE);
                        }


                        if (response.getJSONArray("videos").getJSONObject(0).has("thumbnail")) {

                            Glide.with(Mcontext)
                                    .load(gsonObj.getVideos().get(0).getThumbnail())
                                    .into(thumb_bottomsheet);

                        }
                        // source_bottomsheet.setText(String.format("Source: %s", gsonObj.getVideos().get(0).getExtractor()));
                        btncancel_bottomsheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        dialog.setContentView(view);
                        dialog.show();

                    }
                    dismissMyDialog();

                }

            }
        } catch (Exception str2) {
            // str2.printStackTrace();
            // Toast.makeText(Mcontext, "Invalid URL", 0).show();
            System.out.println("reccccc VVKK Error= " + str2);

            dismissMyDialogErrortoast();
        }

    }

    private static class GetInstagramVideo extends AsyncTask<String, Void, Document> {
        Document doc;

        @Override
        protected Document doInBackground(String... urls) {

            System.out.println("mydahjsdgadashas2244  " + urls[0]);

            try {
                doc = Jsoup.connect(urls[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: Error" + e.getMessage());
            }
            return doc;

        }

        protected void onPostExecute(Document result) {
            dismissMyDialog();

            try {
                String URL = result.select("meta[property=\"og:video\"]").last().attr("content");
                Title = result.title();
                System.out.println("mydahjsdgadashas  " + Title);

                DownloadFileMain.startDownloading(Mcontext, URL, Title + ".mp4", ".mp4");

            } catch (Exception e) {
                System.out.println("mydahjsdgadashas22  " + e.getMessage());
                e.printStackTrace();

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private static class callGetShareChatData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Document doInBackground(String... strArr) {
            //https://sharechat.com/post/rqM9Z47

            try {
                String myurlqq = strArr[0].replace("/post/", "/video/") + "?referrer=url";
                Log.d("ContentValues", "" + myurlqq);

                this.ShareChatDoc = Jsoup.connect(myurlqq).get();
            } catch (Exception strArr2) {
                strArr2.printStackTrace();
                Log.d("ContentValues", "" + strArr2);
            }
            return this.ShareChatDoc;
        }

        protected void onPostExecute(Document document) {

            try {

                VideoUrl = document.select("video").last().attr("src");
                Log.e("onPostExecute: ", VideoUrl);
                if (!VideoUrl.equals("")) {

                    dismissMyDialog();
                    try {
                        String myurldocument = VideoUrl;


                        String nametitle = "sharechat_" +
                                System.currentTimeMillis() +
                                ".mp4";

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        dismissMyDialog();

                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception document22) {
                dismissMyDialog();
                document22.printStackTrace();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class callGetRoposoData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;


        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Document doInBackground(String... strArr) {
            try {
                this.ShareChatDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception strArr2) {
                strArr2.printStackTrace();
                Log.d("ContentValues roposo_", "doInBackground: Error");
            }
            return this.ShareChatDoc;
        }

        protected void onPostExecute(Document document) {
            String charSequence = "";

            try {

                dismissMyDialog();


                VideoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
                Log.e("onPostExecute:roposo_ ", VideoUrl);
                if (!VideoUrl.equals(charSequence)) {


                    try {
                        String myurldocument = VideoUrl;


                        String nametitle = "roposo_" +
                                System.currentTimeMillis() +
                                ".mp4";

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = charSequence;
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        dismissMyDialog();
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception document22) {
                document22.printStackTrace();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                dismissMyDialog();
            }
        }
    }

    public static class CallMitronData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            String str;
            try {
                String str2 = strArr[0];
                if (str2.contains("api.mitron.tv")) {
                    String[] split = str2.split("=");
                    str = "https://web.mitron.tv/video/" + split[split.length - 1];
                } else {
                    str = strArr[0];
                }
                this.RoposoDoc = Jsoup.connect(str).get();
            } catch (IOException e) {
                e.printStackTrace();
                dismissMyDialog();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {

            //   System.out.println("myresponseis111 " + document.html());

            try {

                dismissMyDialog();

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(html).getJSONObject("props").getJSONObject("pageProps").getJSONObject("video").get("videoUrl"));
                    if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                        try {


                            String myurldocument = VideoUrl;


                            String nametitle = "mitron_" +
                                    System.currentTimeMillis();

                            DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());
                            dismissMyDialog();
                            document2.printStackTrace();
                            Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }
                    return;
                }
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CallJoshData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(html)
                            .getJSONObject("props")
                            .getJSONObject("pageProps")
                            .getJSONObject("detail")
                            .getJSONObject("data")
                            .get("download_url"));

                    if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                        try {


                            String myurldocument = VideoUrl.replace("_wmj_480.mp4", "_480.mp4");


                            String nametitle = "joshvideo_" +
                                    System.currentTimeMillis();

                            DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());
                            dismissMyDialog();
                            document2.printStackTrace();
                            Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }
                    return;
                }
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

//    public static class CallTrillerData extends AsyncTask<String, Void, JSONObject> {
//
//
////                                                                    OkHttpClient client = new OkHttpClient.Builder()
//                                                                    .connectTimeout(10, TimeUnit.SECONDS)
//                                                                    .writeTimeout(10, TimeUnit.SECONDS)
//                                                                    .readTimeout(30, TimeUnit.SECONDS)
//                                                                    .build();
////        Request request = new Request.Builder()
////                .url("https://social.triller.co/v1.5/api/videos/c8ff1535-316f-47ef-937b-e6588aa77216")
////                .method("GET", null)
////                .build();
////        Response response = client.newCall(request).execute();
//
//
//        //        https://triller.co/@shraddhakapoor/video/821e874c-3519-4115-9a88-0cb3f303ca88
//        String VideoUrl = "";
//
//        public JSONObject doInBackground(String... strArr) {
//            String response;
//            String newurl = "";
//            try {
//                if (strArr[0].contains("v.triller")) {
//
//                    HttpURLConnection con = null;
//                    try {
//                        con = (HttpURLConnection) (new URL(strArr[0]).openConnection());
//
//                        con.setInstanceFollowRedirects(false);
//                        con.connect();
//                        int responseCode = con.getResponseCode();
//                        System.out.println(responseCode);
//                        String location = con.getHeaderField("Location");
//
//                        newurl = "https://social.triller.co/v1.5/api/videos/" + location.substring(location.indexOf("/video/") + 7);
//                    } catch (Exception e) {
//
//                    }
//
//                } else {
//
//                    newurl = "https://social.triller.co/v1.5/api/videos/" + strArr[0].substring(strArr[0].indexOf("/video/") + 7);
//
//                }
//
//                HttpClient httpclient = new DefaultHttpClient();
//
//                HttpGet httppost = new HttpGet(newurl);
//
//                HttpResponse responce = httpclient.execute(httppost);
//
//                HttpEntity httpEntity = responce.getEntity();
//
//                response = EntityUtils.toString(httpEntity);
//
//                Log.d("response is", response);
//
//                return new JSONObject(response);
//
//            } catch (Exception ex) {
//
//                ex.printStackTrace();
//
//            }
//
//            return null;
//        }
//
//        public void onPostExecute(JSONObject document) {
//            System.out.println("myresponseis111 2exp1 " + document);
//
//
//            try {
//
//             dismissMyDialog();
//
//                this.VideoUrl = document.getJSONArray("videos").getJSONObject(0).getString("video_url");
//
//
//                if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
//                    try {
//
//
//                        String myurldocument = VideoUrl;
//
//
//                        String nametitle = "trillervideo_" +
//                                System.currentTimeMillis();
//
//                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");
//
//                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
//                        VideoUrl = "";
//                        //   binding.etText.setText(charSequence);
//
//                    } catch (Exception document2) {
//                        System.out.println("myresponseis111 exp1 " + document2.getMessage());
//
//                        document2.printStackTrace();
//                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
//                    }
//
//                    return;
//                }
//
//                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
//            } catch (Exception unused) {
//                System.out.println("myresponseis111 exp " + unused.getMessage());
//
//
//                dismissMyDialog();
//                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
//    }

    public static class CallRizzleData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(html).getJSONObject("props").getJSONObject("pageProps").getJSONObject("post").getJSONObject("video").get("originalUrl"));
                    if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                        try {


                            String myurldocument = VideoUrl;


                            String nametitle = "rizzlevideo_" +
                                    System.currentTimeMillis();

                            DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());
                            dismissMyDialog();
                            document2.printStackTrace();
                            Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }
                    return;
                }
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CallIfunnyData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36").get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();
                this.VideoUrl = document.select("meta[property=\"og:video:url\"]").last().attr("content");


//                String html = document.select("script[class=\"js-media-template\"]").first().html();
//                new Element(html);
//                Matcher matcher = Pattern.compile("<video[^>]+poster\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(html);
//                while (matcher.find()) {
//                    this.VideoUrl = matcher.group(1).replace("jpg", "mp4").replace("images", "videos").replace("_3", "_1");
//                }
                if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "ifunnyvideo_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());
                        dismissMyDialog();
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CallLikeeDataOld extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                String str = strArr[0];
                if (str.contains("com")) {
                    str = str.replace("com", "video");
                }
                this.RoposoDoc = Jsoup.connect("https://likeedownloader.com/results").data("id", str).userAgent("Mozilla").post();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();

                this.VideoUrl = document.select("a.without_watermark").last().attr("href");

                if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "Likeevideo_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());
                        dismissMyDialog();
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());

                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CalltrellData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {
                dismissMyDialog();

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(new JSONObject(html)
                            .getJSONObject("props")
                            .getJSONObject("pageProps")
                            .getJSONObject("result")
                            .getJSONObject("result").getJSONObject("trail")
                            .getJSONArray("posts").get(0).toString())
                            .get("video"));

                    System.out.println("myresponseis111 exp991 " + VideoUrl);


                    if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                        try {


                            String myurldocument = VideoUrl;


                            String nametitle = "trellvideo_" +
                                    System.currentTimeMillis();

                            DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());
                            dismissMyDialog();
                            document2.printStackTrace();
                            Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }
                    return;
                }

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());

                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CallBoloindyaData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();

                Iterator it = document.getElementsByTag("script").iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Element element = (Element) it.next();
                    if (element.data().contains("videoFileCDN")) {
                        for (String str : element.data().split(StringUtils.LF)) {
                            if (str.contains("var videoFileCDN=\"https")) {
                                this.VideoUrl = str.split("=")[1]
                                        .replace("\"", "")
                                        .replace("\"", "")
                                        .replace(";", "");
                            }
                        }
                    }
                }
                if (this.VideoUrl.startsWith("//")) {
                    this.VideoUrl = "https:" + this.VideoUrl;
                }
                if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "Boloindyavideo_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());
                        dismissMyDialog();
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());

                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CallhindData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {
                dismissMyDialog();

                Iterator it = document.getElementsByTag("script").iterator();
                while (it.hasNext()) {
                    Element element = (Element) it.next();
                    if (element.data().contains("window.__STATE__")) {
                        String replace = element.html().replace("window.__STATE__", "").replace(";", "")
                                .replace("=", "");
                        this.VideoUrl = String.valueOf(new JSONObject(new JSONObject(new JSONArray("[" + replace + "]")
                                .get(0).toString()).getJSONObject("feed").getJSONArray("feed")
                                .get(0).toString()).get("download_media"));
                        if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                            try {


                                String myurldocument = VideoUrl;


                                String nametitle = "hindvideo_" +
                                        System.currentTimeMillis();

                                DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                                //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                                VideoUrl = "";
                                //   binding.etText.setText(charSequence);

                            } catch (Exception document2) {
                                System.out.println("myresponseis111 exp1 " + document2.getMessage());
                                dismissMyDialog();
                                document2.printStackTrace();
                                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                            }

                            return;
                        }
                    }
                }
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());

                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CalldubsmashData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();

                this.VideoUrl = document.select("video").last().attr("src");
                if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "dubsmashvideo_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());
                        dismissMyDialog();
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());

                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }


    public static class CalltumblerData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();
                // System.out.println("myresponseis111 exp166 " + document);
//                System.out.println("myresponseis111 exp166 " + document.select("source[src]").first().attr("src"));
//                System.out.println("myresponseis111 exp177 " + document.select("video[src]").first().attr("src"));

                this.VideoUrl = document.select("source").last().attr("src");

                System.out.println("myresponseis111 exp1 " + VideoUrl);

                if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                    try {


                        String myurldocument = VideoUrl;

                        String nametitle = "tumbler_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());
                        dismissMyDialog();
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }


    public static class CallRedditData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();
                System.out.println("myresponseis111 exp166 " + document.select("a"));
                //System.out.println("myresponseis111 exp166 " + document.select("a[class=\"downloadbutton \"]").last().attr("src"));
//                System.out.println("myresponseis111 exp177 " + document.select("video[src]").first().attr("src"));

                this.VideoUrl = document.select("a").get(10).attr("href");

                System.out.println("myresponseis111 exp1 " + VideoUrl);

                if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                    try {


                        String myurldocument = VideoUrl;

                        String nametitle = "Reddit_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());
                        dismissMyDialog();
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp12 " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CalllinkedinData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();
                // System.out.println("myresponseis111 exp166 " + document);
//                System.out.println("myresponseis111 exp166 " + document.select("source[src]").first().attr("src"));
//                System.out.println("myresponseis111 exp177 " + document.select("video[src]").first().attr("src"));

                this.VideoUrl = document.select("video").last().attr("data-sources");


                JSONArray jsonArray = new JSONArray(VideoUrl);
                System.out.println("myresponseis111 exp1 " + jsonArray.getJSONObject(0).getString("src"));


                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(jsonArray.getJSONObject(0).getString("src"));
                arrayList.add(jsonArray.getJSONObject(1).getString("src"));

                CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];

                charSequenceArr[0] = "Low quality";
                charSequenceArr[1] = "High quality";

                new AlertDialog.Builder(Mcontext).setTitle("Quality!").setItems(charSequenceArr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DownloadFileMain.startDownloading(Mcontext, arrayList.get(i), "Linkedin_" + System.currentTimeMillis(), ".mp4");

                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismissMyDialog();
                    }
                }).setCancelable(false).show();


                //   Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class CallgaanaData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                //   this.RoposoDoc = Jsoup.connect(strArr[0]).get();
                //   download_hlsganna("", ".mp4");

            } catch (Exception e) {

                System.out.println("jskdfhksdhfkshdfkhsdj " + e.getMessage());
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {

            dismissMyDialog();


        }
    }

    public static class CallmxtaktakData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
                //   download_hlsganna("", ".mp4");

            } catch (Exception e) {

                System.out.println("jskdfhksdhfkshdfkhsdj " + e.getMessage());
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {

            String charSequence = "";

            try {


                String data = "";

                Iterator<Element> documentitrator = document.select("script").iterator();

                do {
                    if (!documentitrator.hasNext()) {

                        break;
                    }
                    data = documentitrator.next().data();
                    Log.e("onP4342424te:datais ", data);


                } while (!data.contains("window._state"));


                String stringbuil = data.substring(data.indexOf("{"), data.indexOf("};")) + "}";
                Log.e("onPostbjnkjh:oso_11 ", stringbuil);


                if (!document.equals("")) {
                    try {
                        JSONObject jSONObject = new JSONObject(stringbuil);
                        VideoUrl = jSONObject.getJSONObject("sharePhoto").getString("mp4Url");

                        Log.e("onPostExecute:roposo_ ", VideoUrl);

                        getSnackVideoData(jSONObject.getString("shortUrl"), Mcontext);
                        VideoUrl = charSequence;

                    } catch (Exception document2) {
                        document2.printStackTrace();

                        System.out.println("respossss112212121qerrr " + document2.getMessage());

                        dismissMyDialog();
                    }
                }


            } catch (Exception document22) {
                dismissMyDialog();
                document22.printStackTrace();
                System.out.println("respossss112212121qerrr " + document22.getMessage());

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }


        }
    }

    public static class CallgdriveData extends AsyncTask<String, Void, String> {
        String VideoUrl = "";
        LowCostVideo xGetter;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            this.xGetter = new LowCostVideo(Mcontext);
            this.xGetter.onFinish(new LowCostVideo.OnTaskCompleted() {
                public void onTaskCompleted(ArrayList<XModel> arrayList, boolean z) {
                    if (!z) {

                        System.out.println("myresponseis111 exp122 " + arrayList.get(0));

                        CallgdriveData.this.done(arrayList.get(0));


                    } else if (arrayList != null) {
                        System.out.println("myresponseis111 exp133 " + arrayList.get(0));

                        CallgdriveData.this.multipleQualityDialog(arrayList);
                    } else {

                        dismissMyDialog();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }
                }

                public void onError() {


                    dismissMyDialog();
                    Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                }
            });

        }

        public String doInBackground(String... strArr) {
            return strArr[0];
        }

        public void onPostExecute(String str) {
            System.out.println("myresponseis111 exp13344 " + str);

            if (xGetter != null) {
                this.xGetter.find(str);
                //   System.out.println("myresponseis111 exp13344 " + xGetter.find(str));

            } else {
                this.xGetter = new LowCostVideo(Mcontext);
                this.xGetter.onFinish(new LowCostVideo.OnTaskCompleted() {
                    public void onTaskCompleted(ArrayList<XModel> arrayList, boolean z) {

                        System.out.println("myresponseis111 exp133 " + arrayList.get(0));

                        if (!z) {

                            System.out.println("myresponseis111 exp122 " + arrayList.get(0));

                            CallgdriveData.this.done(arrayList.get(0));


                        } else if (arrayList != null) {
                            System.out.println("myresponseis111 exp133 " + arrayList.get(0));

                            CallgdriveData.this.multipleQualityDialog(arrayList);
                        } else {

                            dismissMyDialog();
                            Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                        }


                    }

                    public void onError() {


                        dismissMyDialog();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }
                });
                this.xGetter.find(str);
            }
        }

        public void multipleQualityDialog(final ArrayList<XModel> arrayList) {
            CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                charSequenceArr[i] = arrayList.get(i).getQuality();
            }
            new AlertDialog.Builder(Mcontext).setTitle("Quality!").setItems(charSequenceArr, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    CallgdriveData.this.done(arrayList.get(i));
                }
            }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dismissMyDialog();
                }
            }).setCancelable(false).show();
        }


        public void done(XModel xModel) {


            try {

                dismissMyDialog();

                this.VideoUrl = xModel.getUrl();
                if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "Allvideo_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());
                        dismissMyDialog();
                        document2.printStackTrace();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }

        }

    }


    public static class callGetSnackAppDataV2 extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                dismissMyDialog();

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(html)
                            .getJSONObject("props")
                            .getJSONObject("pageProps")
                            .getJSONObject("detail")
                            .getJSONObject("data")
                            .get("download_url"));

                    if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                        try {


                            String myurldocument = VideoUrl.replace("_wmj_480.mp4", "_480.mp4");


                            String nametitle = "joshvideo_" +
                                    System.currentTimeMillis();

                            DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());
                            dismissMyDialog();
                            document2.printStackTrace();
                            Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }
                    return;
                }
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }


    private static class callGetSnackAppData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;
        private Iterator<Element> abk;

        callGetSnackAppData() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Document doInBackground(String... strArr) {
            try {
                this.ShareChatDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception strArr2) {
                strArr2.printStackTrace();
                Log.d("ContentValues roposo_", "doInBackground: Error");
            }
            return this.ShareChatDoc;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        protected void onPostExecute(Document document) {
            String charSequence = "";

            try {


                String data = "";

                Iterator<Element> documentitrator = document.select("script").iterator();

                do {
                    if (!documentitrator.hasNext()) {

                        break;
                    }
                    data = documentitrator.next().data();
                    //  Log.e("onP4342424te:datais ", data);


                } while (!data.contains("window.__INITIAL_STATE__"));


                String stringbuil = data.substring(data.indexOf("{"), data.indexOf("}}}};")) + "}}}}";


                if (!document.equals("")) {
                    try {
                        JSONObject jSONObject = new JSONObject(stringbuil);
                        VideoUrl = jSONObject.getJSONObject("initData").getString("mp4Url");

                        Log.e("onPostExecute:snackvv_ ", VideoUrl);

                        // getSnackVideoData(jSONObject.getString("shortUrl"), Mcontext);
                        String nametitle = "snackvideo_" +
                                System.currentTimeMillis();

                        DownloadFileMain.startDownloading(Mcontext, VideoUrl, nametitle, ".mp4");
                        VideoUrl = charSequence;
                        dismissMyDialog();
                    } catch (Exception document2) {
                        document2.printStackTrace();

                        System.out.println("respossss112212121qerrr " + document2.getMessage());
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();

                        dismissMyDialog();
                    }
                }


                //   VideoUrl = document.select("video[src]").first().attr("src");


                //  VideoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
//                Log.e("onPostExecute:roposo_ ", VideoUrl);
//                if (!VideoUrl.equals(charSequence)) {
//
//
//                    try {
//                        String myurldocument = VideoUrl;
//
//
//                        String nametitle = "snackvideo_" +
//                                System.currentTimeMillis() +
//                                ".mp4";
//
//                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");
//
//                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
//                        VideoUrl = charSequence;
//                        //   binding.etText.setText(charSequence);
//
//                    } catch (Exception document2) {
//                        document2.printStackTrace();
//                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
//                    }
//                }


            } catch (Exception document22) {
                dismissMyDialog();
                document22.printStackTrace();
                System.out.println("respossss112212121qerrr " + document22.getMessage());

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class callGetbilibiliAppData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;
        private Iterator<Element> abk;

        callGetbilibiliAppData() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Document doInBackground(String... strArr) {
            try {
                this.ShareChatDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception strArr2) {
                strArr2.printStackTrace();
                Log.d("ContentValues roposo_", "doInBackground: Error");
            }
            return this.ShareChatDoc;
        }

        protected void onPostExecute(Document document) {
            String charSequence = "";

            try {

                ArrayList<String> mp4List = new ArrayList<>();
                ArrayList<String> qualitylist = new ArrayList<>();

                String data = "";

                Iterator<Element> documentitrator = document.select("script").iterator();

                do {
                    if (!documentitrator.hasNext()) {

                        break;
                    }
                    data = documentitrator.next().data();
                    Log.e("onP4342424te:datais ", data);


                } while (!data.contains("window.__playinfo__="));


                String stringbuil = data.substring(data.indexOf("{"), data.lastIndexOf("}"));

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(stringbuil);
                stringBuilder.append("}");

                Log.e("onPostbjnkjhoso_11 ", stringBuilder.toString());
                if (!document.equals("")) {
                    try {
                        JSONObject jSONObject = new JSONObject(stringBuilder.toString());
                        JSONObject datajSONObject = jSONObject.getJSONObject("data");
                        JSONObject dashjSONObject1 = datajSONObject.getJSONObject("dash");
                        JSONArray videojSONObject1 = dashjSONObject1.getJSONArray("video");


                        System.out.println("respossss112212121URL)) " + videojSONObject1.getJSONObject(0).getString("base_url"));


                        for (int i = 0; i < videojSONObject1.length(); i++) {


                            JSONObject jsonObject12 = videojSONObject1.getJSONObject(i);
                            mp4List.add(jsonObject12.getString("base_url"));
                            qualitylist.add(jsonObject12.getString("width"));


                            System.out.println("respossss112212121URL " + jsonObject12.getString("base_url"));

                        }

                        try {
                            JSONArray audiojSONObject1 = dashjSONObject1.getJSONArray("audio");
                            for (int i = 0; i < audiojSONObject1.length(); i++) {


                                JSONObject jsonObject12 = audiojSONObject1.getJSONObject(i);
                                mp4List.add(jsonObject12.getString("base_url"));
                                qualitylist.add(jsonObject12.getString("mime_type"));


                                System.out.println("respossss112212121URL " + jsonObject12.getString("base_url"));

                            }

                        } catch (Exception e) {
                            dismissMyDialog();
                        }


                        if (videojSONObject1.length() > 0) {
                            if (!((Activity) Mcontext).isFinishing()) {

                                dialog_quality_allvids = new Dialog(Mcontext);


                                dismissMyDialog();

                                windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
                                LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);


                                dialog_quality_allvids.setContentView(mChatHeadView);


                                mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);

                                img_dialog = mChatHeadView.findViewById(R.id.img_dialog);

                                mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
                                img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);


                                int size = 0;

                                try {
                                    DisplayMetrics displayMetrics = new DisplayMetrics();
                                    ((Activity) Mcontext).getWindowManager()
                                            .getDefaultDisplay()
                                            .getMetrics(displayMetrics);

                                    int height = displayMetrics.heightPixels;
                                    int width = displayMetrics.widthPixels;

                                    size = width / 2;

                                } catch (Exception e) {
                                    size = WindowManager.LayoutParams.WRAP_CONTENT;
                                }


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    params = new WindowManager.LayoutParams(
                                            size,
                                            WindowManager.LayoutParams.WRAP_CONTENT,
                                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                            PixelFormat.TRANSLUCENT);

                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                    params.x = 0;
                                    params.y = 100;
                                } else {
                                    params = new WindowManager.LayoutParams(
                                            size,
                                            WindowManager.LayoutParams.WRAP_CONTENT,
                                            WindowManager.LayoutParams.TYPE_PHONE,
                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                            PixelFormat.TRANSLUCENT);

                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                    params.x = 0;
                                    params.y = 100;
                                }


                                // mainLayout.setLayoutParams(params);


                                for (int i = 0; i < mp4List.size(); i++) {


                                    addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4List.get(i), "Bilibili_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                }

                                img_dialog.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_quality_allvids.dismiss();
                                    }
                                });

                                dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                                dialog_quality_allvids.getWindow().setAttributes(params);
                                //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));

                                dialog_quality_allvids.show();


                                dialog_quality_allvids.show();
                            }
                        } else {


                            DownloadFileMain.startDownloading(Mcontext, mp4List.get(0), "Bilibili_" + System.currentTimeMillis(), ".mp4");

                            dismissMyDialog();
                        }


                    } catch (Exception document2) {
                        document2.printStackTrace();

                        System.out.println("respossss112212121qerrr " + document2.getMessage());

                        dismissMyDialog();
                        Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                    }
                }


            } catch (Exception document22) {
                dismissMyDialog();
                document22.printStackTrace();
                System.out.println("respossss112212121qerrr " + document22.getMessage());

                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class Call9gagData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://9gag.com/gag/aXowVXz")
                    .method("GET", null)
                    .build();
            try {
                okhttp3.Response response = client.newCall(request).execute();


                System.out.println("mybodyhh1111>>> " + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {
            System.out.println("myresponseis111 exp12222 " + document);


            try {
                dismissMyDialog();

                Iterator it = document.getElementsByTag("script").iterator();
                while (it.hasNext()) {
                    Element element = (Element) it.next();
                    if (element.data().contains("window.__STATE__")) {
                        String replace = element.html().replace("window.__STATE__", "").replace(";", "")
                                .replace("=", "");
                        this.VideoUrl = String.valueOf(new JSONObject(new JSONObject(new JSONArray("[" + replace + "]")
                                .get(0).toString()).getJSONObject("feed").getJSONArray("feed")
                                .get(0).toString()).get("download_media"));
                        if (this.VideoUrl != null && !this.VideoUrl.equals("")) {
                            try {


                                String myurldocument = VideoUrl;


                                String nametitle = "hindvideo_" +
                                        System.currentTimeMillis();

                                DownloadFileMain.startDownloading(Mcontext, myurldocument, nametitle, ".mp4");

                                //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                                VideoUrl = "";
                                //   binding.etText.setText(charSequence);

                            } catch (Exception document2) {
                                System.out.println("myresponseis111 exp1 " + document2.getMessage());
                                dismissMyDialog();
                                document2.printStackTrace();
                                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
                            }

                            return;
                        }
                    }
                }
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                dismissMyDialog();
                Toast.makeText(Mcontext, Mcontext.getResources().getString(R.string.somthing), Toast.LENGTH_SHORT).show();
            }
        }


    }


}
