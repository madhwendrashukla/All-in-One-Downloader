package com.papayacoders.allinonedownloader.AllDownload.webservices;


import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.Mcontext;
import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.fromService;
import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.pd;

import com.papayacoders.allinonedownloader.AllDownload.DownloadFileMain;
import com.papayacoders.allinonedownloader.AllDownload.iUtils;
import com.papayacoders.allinonedownloader.R;

import android.content.Context;
import android.os.AsyncTask;




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AllocineDownloader {

    private Context context;
    private String FinalURL;
    private String VideoURL;

    public AllocineDownloader(Context context, String vid) {
        this.context = context;
        VideoURL = vid;
    }

    public void DownloadVideo() {
        new CallAllocineData().execute(VideoURL);
    }


    public static class CallAllocineData extends AsyncTask<String, Void, Document> {
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

                Elements elements = document.select("link");
                for (Element element : elements) {
                    if (element.attr("as").equals("video")) {

                        String replaceString = element.getElementsByTag("link").attr("href");
                        System.out.println("myresponseis111 list_of_qualities" + replaceString);
                        DownloadFileMain.startDownloading(Mcontext, "https:" + replaceString, "Allocine_" + System.currentTimeMillis(), ".mp4");


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
