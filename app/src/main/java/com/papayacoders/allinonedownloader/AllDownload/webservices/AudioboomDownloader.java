package com.papayacoders.allinonedownloader.AllDownload.webservices;


import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.Mcontext;
import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.fromService;
import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.pd;

import com.papayacoders.allinonedownloader.AllDownload.DownloadFileMain;
import com.papayacoders.allinonedownloader.AllDownload.iUtils;
import com.papayacoders.allinonedownloader.R;

import android.content.Context;
import android.os.AsyncTask;


import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AudioboomDownloader {

    private Context context;
    private String FinalURL;
    private String VideoURL;

    public AudioboomDownloader(Context context, String vid) {
        this.context = context;
        VideoURL = vid;
    }

    public void DownloadVideo() {
        new CallAudioboomData().execute(VideoURL);
    }


    public static class CallAudioboomData extends AsyncTask<String, Void, Document> {
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
            boolean isSecon = false;

            try {

                if (!fromService) {

                    pd.dismiss();
                }
                System.out.println("myresponseis111 exp166 " + document);


                String data = "";

                Elements elements = document.select("div");
                for (Element element : elements) {
                    if (element.attr("data-auto-play-primary-item").equals("1")) {
                        //Save As you want to

                        String list_of_qualities = element.getElementsByAttribute("data-new-clip-store").attr("data-new-clip-store").replace("&quot;", "\"");


                        JSONObject obj = new JSONObject(list_of_qualities);


                        String replaceString = obj.getJSONArray("clips").getJSONObject(0).getString("clipURLPriorToLoading");

                        System.out.println("myresponseis111 list_of_qualities" + replaceString);

                        DownloadFileMain.startDownloading(Mcontext, replaceString, "Audioboom_" + System.currentTimeMillis(), ".mp3");


                    }
                }


            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }


}
