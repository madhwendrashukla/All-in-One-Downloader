package com.papayacoders.allinonedownloader.AllDownload.webservices;



import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.Mcontext;
import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.fromService;
import static com.papayacoders.allinonedownloader.AllDownload.webservices.DownloadVideosMain.pd;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.Keep;


import com.papayacoders.allinonedownloader.AllDownload.iUtils;
import com.papayacoders.allinonedownloader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

@Keep
public class TikTokNewTest2Downloader {

    static Map<String, String> map = new HashMap<>();
    static Map<String, String> mapData = new HashMap<>();
    private final Context context;
    private final String VideoURL;
    private String FinalURL;

    public TikTokNewTest2Downloader(Context context, String vid) {

        this.context = context;
        VideoURL = vid;

        mapData.put("User-Agent", "com.zhiliaoapp.musically/2018092101 (Linux; U; Android 6.0.1; en_US; SM-J700F; Build/MMB29K; Cronet/58.0.2991.0)");

        mapData.put("Cookie", "tt_webid=6935274800207676929; tt_webid_v2=6935274800207676929; _abck=570C412B56A4FBDFBE02DCC6260E703C~-1~YAAQrZywtgfc3Rl4AQAA+9xROgUWesKY7qQdGHMW//YbtehqkaWtS0Q3epzJ1fkLDTcuJEHUkZtEvJDluatAduFdnkq0UWcpmTXDFteloYs8brNOvzyz64iY6PXz/b173jF7BbtopYaFrgxmw2UxCC1eGf4MnLI7IzEjzB9TjMETtt2CxHi0dGfgZrV+Wumrd+HnMf6IwPhBwOOl/r7EKI4DaMjOrEr4MixbY2g/nf/lQvvtmi9igmHguCf0A+zp90JbXrarsYK4jJtGGq+B/xE8yckRqeZzVsnONktljZhU9H2uUTBRiFM6yr06yNdWa9+nNjQn23WJqS6TAxa5Wb/5WG0x16T6VVEUwRoomzj9d+65sLjo7HpmMfJDOSy9U8JZykKuDepVeA==~-1~-1~-1");


    }

    public void DownloadVideo() {
        new CalltikinData().execute("https://m.tiktok.com/v/6932392451298561281");
    }


    public static class CalltikinData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";


        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0])
                        .userAgent("Mozilla/5.0 (Linux; Android 10;TXY567) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/8399.0.9993.96 Mobile Safari/599.36")
                        //.userAgent("com.zhiliaoapp.musically/2018092101 (Linux; U; Android 6.0.1; en_US; SM-J700F; Build/MMB29K; Cronet/58.0.2991.0)")
                        .headers(mapData)
                        //  .data(mapData)
                        .post();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }
                System.out.println("myresponseis111 exp166 " + document.outerHtml());

                String property = System.getProperty("http.agent");
//                System.out.println("myresponseis111 exp166 3333 " + document.select("a[href]").first().attr("href"));
////                System.out.println("myresponseis111 exp177 " + document.select("video[src]").first().attr("src"));
//
////                String data = "";
////                ArrayList<String> arrayList = new ArrayList<>();
////
//                Elements elements = document.select("a");
//                for (Element element : elements) {
//                    if (element.attr("href").contains("http")) {
//                        //Save As you want to
//
//                        String myurlis = element.getElementsByAttribute("href").attr("href");
//
//                        new downloadFile().Downloading(Mcontext, myurlis, "Tiktok_" + System.currentTimeMillis(), ".mp4");
//                    }
//                }
//
//
//                CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];
//
//                charSequenceArr[0] = "1080p quality";
//                charSequenceArr[1] = "720p quality";
//                charSequenceArr[2] = "480p quality";
//
//                new AlertDialog.Builder(Mcontext).setTitle("Quality!").setItems(charSequenceArr, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        new downloadFile().Downloading(Mcontext, arrayList.get(i), "Mashable_" + System.currentTimeMillis(), ".mp4");
//
//                    }
//                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (!fromService) {
//
//                            pd.dismiss();
//                        }
//                    }
//                }).setCancelable(false).show();


                //   iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
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
//
//    public void downloadpost(String str) {
//        ((ApiService) ApiClient.getbase(this.context, true).create(ApiService.class)).getMainResponse(str).enqueue(new GetDownloadPostResponse(this, str));
//    }
//
//    public void downloadpostsecond(String str, String str2, Downloadpost downloadpost) {
//        ((ApiService) ApiClient.getbase(this.context, true).create(ApiService.class)).getTikVideoResponse(TikTokUtils.getdbstrvideoresponse(str2), "com.zhiliaoapp.musically/2018092101 (Linux; U; Android 6.0.1; en_US; SM-J700F; Build/MMB29K; Cronet/58.0.2991.0)", "A").enqueue(new GetMainTikResponse(this, str, downloadpost));
//    }
//
//    public void getUrlFromTikTokApi(String str) {
//        try {
//            ((ApiService) ApiClient.getbase(this.context, true).create(ApiService.class)).getTikResponse(TikTokUtils.getdbstr(), AESUtil.getDecryptedstr(Keys.secretKey(), Keys.ivKey(), str)).enqueue(new GetTikTokResponse(this, str));
//        } catch (String str2) {
//            Log.i("Exception", str2.toString());
//        }
//    }
//
//    public void tikvideoapi(String str, Downloadpost downloadpost) {
//        String property = System.getProperty("http.agent");
//        Map hashMap = new HashMap();
//        hashMap.put("api_key", urlParse("app-snaptik"));
//        hashMap.put("url", urlParse(str));
//        ((ApiService) ApiClient.getbase(this.context, true).create(ApiService.class)).getTikVideoApi(TikTokUtils.getdbstrvideoapi(), property, hashMap).enqueue(new GetTikVideoApi(this, str, downloadpost));
//    }
//
//    public void tikvideoobj(String str, Downloadpost downloadpost) {
//        if (downloadpost != null) {
//            HomeFrag.enq = Utils.DownloadUrlWithoutWatermark(this.context, downloadpost.getmsg_txt(), this.a);
//        } else {
//            ((ApiService) ApiClient.getbase(this.context, true).create(ApiService.class)).getTikVideoobj(str, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36").enqueue(new GetTikTokVideoobjResponse(this));
//        }
//    }
//}
