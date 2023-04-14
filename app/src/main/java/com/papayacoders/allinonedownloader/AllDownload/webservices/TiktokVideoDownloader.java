package com.papayacoders.allinonedownloader.AllDownload.webservices;



import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.fromService;
import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.pd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;


import com.papayacoders.allinonedownloader.AllDownload.DownloadFileMain;
import com.papayacoders.allinonedownloader.AllDownload.interfaces.VideoDownloader;
import com.papayacoders.allinonedownloader.R;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TiktokVideoDownloader implements VideoDownloader {

    private static final Pattern a = Pattern.compile("window\\._INIT_PROPS_\\s*=\\s*(\\{.+\\})");
    private static final Pattern without_watermark = Pattern.compile("/vid:([a-zA-Z0-9]+)/");
    private Context context;
    private String VideoURL;
    private String VideoTitle;

    public TiktokVideoDownloader(Context context, String videoURL) {
        this.context = context;
        VideoURL = videoURL;
    }

    private static int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    public static void down(String str) {
        String obj = "obj";
        String str2 = "";

        try {
            Matcher str1 = a.matcher(str);
            while (str1.find()) {
                JSONObject jSONObject = new JSONObject(str.replaceAll("window\\._INIT_PROPS_\\s*=\\s*", str2).replace("/vid:([a-zA-Z0-9]+)/", obj)).getJSONObject(obj).getJSONObject("videoData");

                Log.e("gdhsgfjsdjfgsjfsd ", jSONObject.toString());

                JSONObject jSONObject2 = jSONObject.getJSONObject("itemInfos");
                jSONObject.getJSONObject("authorInfos");
                String obj2 = jSONObject2.getJSONObject("video").getJSONArray("urls").get(0).toString();
                String obj3 = jSONObject2.getJSONArray("covers").get(0).toString();

            }
        } catch (Exception e2) {
            e2.printStackTrace();

        }

    }

    public static String a() {
        return a(new int[]{103896, 115884, 115884, 111888, 114885, 57942, 46953, 46953, 118881, 118881, 118881, 45954, 106893, 96903, 115884, 96903, 113886, 108891, 96903, 107892, 45954, 104895, 109890, 46953, 117882, 48951, 46953, 111888, 113886, 110889, 98901, 100899, 114885, 114885, 84915, 113886, 107892});
    }

    public static String a(int[] iArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : iArr) {
            stringBuilder.append((char) (i / 999));
        }
        return stringBuilder.toString();
    }

    public static String b() {
        return a(new int[]{103896, 115884, 115884, 111888, 114885, 57942, 46953, 46953, 96903, 111888, 104895, 45954, 99900, 110889, 118881, 109890, 107892, 110889, 96903, 99900, 115884, 104895, 106893, 115884, 110889, 106893, 117882, 104895, 99900, 100899, 110889, 114885, 45954, 98901, 110889, 108891, 46953, 96903, 111888, 104895, 94905, 111888, 110889, 114885, 115884, 46953, 104895, 109890, 99900, 100899, 119880, 45954, 111888, 103896, 111888});
    }


    @Override
    public String getVideoId(String link) {
        if (!link.contains("https")) {
            link = link.replace("http", "https");
        }
        return link;
    }

    @Override
    public void DownloadVideo() {
        Log.i("LOGClipboard111111 clip", "work vvv" + VideoURL);

        //   String newurl =   withoutWatermark(VideoURL);

        // new callGetTikTokData().execute(VideoURL);

        String str = "https://vm.tiktok.com/3QmCLr";
        // String str = "http://mitron.tv/0pn81p2k";

        new Data().execute(VideoURL);
    }

    public String withoutWatermark(String url) {
        String str = "";
        String str2 = "vid:";
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int responseCode = httpConn.getResponseCode();
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            StringBuilder result = new StringBuilder();
            while (true) {
                String str3 = rd.readLine();
                if (str3 != null) {
                    result.append(str3);
                    if (result.toString().contains(str2)) {
                        try {
                            if (result.substring(result.indexOf(str2)).substring(0, 4).equals(str2)) {
                                String VideoId = result.substring(result.indexOf(str2));
                                String FinalVID = VideoId.substring(4, VideoId.indexOf("%")).replaceAll("[^A-Za-z0-9]", str).trim();
                                String sb = "http://api2.musical.ly/aweme/v1/play/?video_id=" +
                                        FinalVID;
                                return sb;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return str;
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class Data extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection;
            BufferedReader reader;
            try {

                if (!fromService) {

                    pd.dismiss();
                }
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String Line;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Log.e("Hello_myurl_is0 ", String.valueOf(reader.lines()));
                }
                while ((Line = reader.readLine()) != null) {
                    Log.e("Hello_myurl_is ", Line);
                    if (Line.contains("videoData")) {


                        Line = Line.substring(Line.indexOf("videoData"));
                        Log.e("Hello_myurl_is1 ", Line);
                        Line = Line.substring(Line.indexOf("urls"));
                        Log.e("Hello_myurl_is2 ", Line);
                        VideoTitle = Line.substring(Line.indexOf("text"));
                        if (VideoTitle.contains("#")) {
                            VideoTitle = VideoTitle.substring(ordinalIndexOf(VideoTitle, "\"", 1) + 1, ordinalIndexOf(VideoTitle, "#", 0));
                        } else {
                            VideoTitle = VideoTitle.substring(ordinalIndexOf(VideoTitle, "\"", 1) + 1, ordinalIndexOf(VideoTitle, "\"", 2));
                        }
                        System.out.println("myvideo titljklfjdfd " + VideoTitle);
                        Line = Line.substring(ordinalIndexOf(Line, "\"", 1) + 1, ordinalIndexOf(Line, "\"", 2));
                        Log.e("Hello_myurl_is3 ", Line);
                        if (!Line.contains("https")) {
                            Line = Line.replace("http", "https");
                        }
                        Log.e("Hello_myurl_is4 ", Line);

                        buffer.append(Line);
                        break;
                    }
                }
                return buffer.toString();
            } catch (Exception e) {
                if (!fromService) {

                    pd.dismiss();
                }
                return "Invalid Video URL or Check Internet Connection";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (URLUtil.isValidUrl(s)) {
                if (VideoTitle == null || VideoTitle.equals("")) {
                    VideoTitle = "TiktokVideo" + new Date().toString() + ".mp4";
                } else {
                    VideoTitle = VideoTitle + ".mp4";
                }


                DownloadFileMain.startDownloading(context, s, VideoTitle, ".mp4");

            } else {
                if (Looper.myLooper() == null)
                    Looper.prepare();
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DataNoWaterMark extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection;
            BufferedReader reader;
            try {

                if (!fromService) {

                    pd.dismiss();
                }
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String Line;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Log.e("Hello_myurl_is0 ", String.valueOf(reader.lines()));
                }
                while ((Line = reader.readLine()) != null) {
                    Log.e("Hello_myurl_is ", Line);
                    if (Line.contains("videoObject")) {


                        //  Line = reader.readLine();
                        Log.d("ThumbnailURL11111_2  ", Line);


                        Line = Line.substring(Line.indexOf("VideoObject"));
                        String substring = Line.substring(Line.indexOf("thumbnailUrl") + 16);
                        VideoTitle = substring.substring(0, substring.indexOf("\""));
//
                        StringBuilder stringBuilder = new StringBuilder();
//                        stringBuilder.append("ThumbnailURL: ");
//                        stringBuilder.append(substring);
//
//                        Log.d("ThumbnailURL", substring);
                        Line = Line.substring(Line.indexOf("contentUrl") + 13);

                        Log.d("ThumbnailURL11111_222  ", Line);


                        Line = Line.substring(0, Line.indexOf("?"));
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("ContentURL: ");
                        stringBuilder.append(Line);

                        //  Log.d("ContentURL1111 thumb  ", substring);
                        Log.d("ContentURL1111", stringBuilder.toString());


                        buffer.append(Line);
                        break;
                    }
                }
                return buffer.toString();
            } catch (IOException e) {
                if (!fromService) {

                    pd.dismiss();
                }
                return context.getString(R.string.invalid_url);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (URLUtil.isValidUrl(s)) {
                if (VideoTitle == null || VideoTitle.equals("")) {
                    VideoTitle = "TiktokVideo" + new Date().toString() + ".mp4";
                } else {
                    VideoTitle = VideoTitle + ".mp4";
                }

                DownloadFileMain.startDownloading(context, s, VideoTitle, ".mp4");

            } else {
                if (Looper.myLooper() == null)
                    Looper.prepare();
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }
    }


    protected class yourDataTask extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            // https://vm.tiktok.com/3QmCLr/
            // String str = "https://www.tiktok.com/@beauty_0f_nature/video/6825315100933639426";
            String str = "http://mitron.tv/0pn81p2k";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());
            } catch (Exception ex) {
                Log.e("App", "yourDataTask", ex);
                return null;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            if (response != null) {
                try {
                    Log.e("Appfgdghdfghfg dgdfg", "Success: " + response);
                } catch (Exception ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }


    class callGetTikTokData extends AsyncTask<String, Void, Document> {
        Document tikDoc;

        callGetTikTokData() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Document doInBackground(String... urls) {
            try {
                this.tikDoc = Jsoup.connect(urls[0]).get();

                Log.d("ContentValues", "" + tikDoc);

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ContentValues", "doInBackground: Error");
            }
            return this.tikDoc;
        }


        @Override
        protected void onPostExecute(Document result) {
            try {
                String URL = result.select("script[id=\"videoObject\"]").last().html();
                String URL1 = result.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!URL.equals("")) {
                    try {
                        JSONObject jsonObject = new JSONObject(URL);
                        new JSONObject(URL1);
                        VideoURL = jsonObject.getString("contentUrl");
                        Log.d("ContentValuesurl", "do " + VideoURL);


                        new GetDownloadLinkWithoutWatermark().execute();
                    } catch (Exception e) {
                        e.printStackTrace();

                        Log.d("ContentValuesurl", "do " + e.getMessage());

                    }
                }
            } catch (NullPointerException e2) {
                e2.printStackTrace();
            }
        }
    }


    private class GetDownloadLinkWithoutWatermark extends AsyncTask<String, String, String> {
        String resp;

        private GetDownloadLinkWithoutWatermark() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                this.resp = withoutWatermark(VideoURL);
                Log.d("ContentValuesurl", "do " + this.resp);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.resp;
        }

        @Override
        protected void onPostExecute(String newurl) {

            if (VideoTitle == null || VideoTitle.equals("")) {
                VideoTitle = "TiktokVideo" + new Date().toString() + ".mp4";
            } else {
                VideoTitle = VideoTitle + ".mp4";
            }


            DownloadFileMain.startDownloading(context, newurl, VideoTitle, ".mp4");

        }
    }


}
