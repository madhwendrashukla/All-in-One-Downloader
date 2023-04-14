package com.papayacoders.allinonedownloader.AllDownload;

import static com.papayacoders.allinonedownloader.utils.Constants.PREF_APPNAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.webkit.URLUtil;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import es.dmoral.toasty.Toasty;


public class iUtils {
    //private InterstitialAd interstitialAd;
    public static String myInstagramTempCookies = "";
    public static String myTiktokTempCookies = "";
    public static String myDLphpTempCookies = "";
    public static int showDialogUpdateTimesShown = 0;
    public static int showDialogUpdateTimesPerSession = 1;
    public static String[] UserAgentsList ={"Mozilla/5.0 (Linux; Android 10; SM-A205U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Mobile Safari/537.36",
            "Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-A515F) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/15.0 Chrome/90.0.4430.210 Mobile Safari/537.36",
            "Mozilla/5.0 (Linux; Android 10; SM-A102U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Mobile Safari/537.36"};

    public static boolean isSameDomain(String url, String url1) {
        return getRootDomainUrl(url.toLowerCase()).equals(getRootDomainUrl(url1.toLowerCase()));
    }

    private static String getRootDomainUrl(String url) {
        String[] domainKeys = url.split("/")[2].split("\\.");
        int length = domainKeys.length;
        int dummy = domainKeys[0].equals("www") ? 1 : 0;
        if (length - dummy == 2)
            return domainKeys[length - 2] + "." + domainKeys[length - 1];
        else {
            if (domainKeys[length - 1].length() == 2) {
                return domainKeys[length - 3] + "." + domainKeys[length - 2] + "." + domainKeys[length - 1];
            } else {
                return domainKeys[length - 2] + "." + domainKeys[length - 1];
            }
        }
    }

    public  static boolean verifyInstallerId(Context context) {

        // A list with valid installers package name
        List<String> validInstallers = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));

        // The package name of the app that has installed your app
        final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());

        // true if your app has been downloaded from Play Store
        return installer != null && validInstallers.contains(installer);
    }

    public static String showCookies(String websiteURL) {

        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            // Access the website
            URL url = new URL(websiteURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.getContent();
            // Get CookieStore
            CookieStore cookieStore = cookieManager.getCookieStore();
            // Get cookies
            Object[] arr = cookieStore.getCookies().toArray();

            String csrftoken = "" + arr[0].toString();
            // csrftoken = csrftoken.replace("csrftoken=", "");
            String mid = "" + arr[1];
            // mid = mid.replace("mid=", "");
            String ig_did = "" + arr[2];
            //  ig_did = ig_did.replace("ig_did=", "");
            String ig_nrcb = "" + arr[3];
            //  ig_nrcb = ig_nrcb.replace("ig_nrcb=", "");
            System.out.println("working errpr \t Value: " + csrftoken + mid);
            // return csrftoken + "; ds_user_id=24740642071; sessionid=8354837521:IfDmOl5NAeYI8m:18; " + mid + "; " + ig_did + "; " + ig_nrcb;
            // return csrftoken + "; ds_user_id=8354837521; sessionid=8354837521:IfDmOl5NAeYI8m:18; " + mid + "; " + ig_did + "; " + ig_nrcb;
            return csrftoken + "; ds_user_id=8354837521; sessionid=8354837521%3Ak9OWG7Iaz8rYOu%3A4; " + mid + "; " + ig_did + "; " + ig_nrcb;

        } catch (Exception e) {
            System.out.println("working errpr \t Value: " + e.getMessage());
            return "";
        }
//
//        String session_id = getCookie1(str, "sessionid");
//        String csrftoken = getCookie1(str, "csrftoken");
//        String userid = getCookie1(str, "ds_user_id");

    }

    public static String showCookiestiktokful(String websiteURL) {

        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            // Access the website
            URL url = new URL(websiteURL);

            URLConnection urlConnection = url.openConnection();
            urlConnection.getContent();

            // Get CookieStore
            CookieStore cookieStore = cookieManager.getCookieStore();

            // Get cookies


            Object[] arr = cookieStore.getCookies().toArray();

            String xtoken = "";
            if (arr[0].toString().contains("x-token")) {
                xtoken = arr[0].toString();
            } else if (arr[1].toString().contains("x-token")) {
                xtoken = arr[1].toString();

            }
            System.out.println("working errpr \t allcookies: " + cookieStore.getCookies());
            System.out.println("working errpr \t Value: " + xtoken);


            return xtoken.substring(xtoken.indexOf("x-token=") + 8);

        } catch (Exception e) {
            System.out.println("working errpr \t Value: " + e.getMessage());
            return "";
        }
//
//        String session_id = getCookie1(str, "sessionid");
//        String csrftoken = getCookie1(str, "csrftoken");
//        String userid = getCookie1(str, "ds_user_id");

    }



    public static String showCookiesdlphp(String websiteURL) {

        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            // Access the website
            URL url = new URL(websiteURL);

            URLConnection urlConnection = url.openConnection();
            urlConnection.getContent();

            // Get CookieStore
            CookieStore cookieStore = cookieManager.getCookieStore();

            // Get cookies

//            Cookie:__test=28fc5ae658c04b8cdf45a3d72ee61ab2; PHPSESSID=9e7825a10df001cbd6ef5aa15b476b39


            Object[] arr = cookieStore.getCookies().toArray();

            String xtoken = "";
            if (arr[0].toString().contains("__test")) {
                xtoken = arr[0].toString();
            }
//
            System.out.println("working errpr \t allcookies: " + cookieStore.getCookies()+"___"+xtoken);
//            System.out.println("working errpr \t Value: " + xtoken);


            return cookieStore.getCookies().toString()+"";

        } catch (Exception e) {
            System.out.println("working errpr \t Value: " + e.getMessage());
            return "";
        }


    }




    public static String getCookietextFromCookies(String siteName, String CookieName) {
        String CookieValue = null;

        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        if (cookies != null && !cookies.isEmpty()) {
            String[] temp = cookies.split(";");
            for (String ar1 : temp) {
                if (ar1.contains(CookieName)) {
                    String[] temp1 = ar1.split("=");
                    CookieValue = temp1[1];
                    break;
                }
            }
        }
        return CookieValue;
    }

    public static boolean hasMarsallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static long getRemoteFileSize(String str) {
        try {
            URLConnection str1 = new URL(str).openConnection();
            str1.connect();
            long contentLength = (long) str1.getContentLength();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file_size = ");
            stringBuilder.append(contentLength);
            Log.e("sasa", stringBuilder.toString());
            return contentLength;
        } catch (Exception str2) {
            str2.printStackTrace();
            return 0;
        }
    }

    public static void tintMenuIcon(Context context, MenuItem item, int color) {
        Drawable drawable = item.getIcon();
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static String decryptSoundUrl(String cipherText) throws Exception {

        String iv_key = "asd!@#!@#@!12312";
        String decryp_key = "g@1n!(f1#r.0$)&%";

        IvParameterSpec iv2 = new IvParameterSpec(iv_key.getBytes("UTF-8"));
        SecretKeySpec key2 = new SecretKeySpec(decryp_key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, key2, iv2);
        byte[] plainText = new byte[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(cipherText));
        } else {

            return "";
        }
        return new String(plainText);
    }

    public static void bookmarkUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences(PREF_APPNAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        // if url is already bookmarked, unbookmark it
        if (pref.getBoolean(url, false)) {
            editor.remove(url).apply();
        } else {
            editor.putBoolean(url, true);
        }

        editor.commit();
    }

    public static boolean isBookmarked(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences(PREF_APPNAME, 0);
        return pref.getBoolean(url, false);
    }

    public static void ShowToast(Context context, String str) {
        Toasty.info(context, str).show();
    }

    public static void ShowToastError(Context context, String str) {
        Toasty.error(context, str).show();
    }

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }


    public static boolean checkURL(CharSequence input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        Pattern URL_PATTERN = Patterns.WEB_URL;
        boolean isURL = URL_PATTERN.matcher(input).matches();
        if (!isURL) {
            String urlString = input + "";
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    new URL(urlString);
                    isURL = true;
                } catch (Exception ignored) {
                }
            }
        }
        return isURL;
    }

    public static String getFilenameFromURL(String str) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(new File(new URL(str).getPath()).getName());
            stringBuilder.append("");
            return stringBuilder.toString();
        } catch (Exception str2) {
            str2.printStackTrace();
            return String.valueOf(System.currentTimeMillis());

        }
    }

    public static String getImageFilenameFromURL(String url) {
        try {
            return new File(new URL(url).getPath()).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return System.currentTimeMillis() + ".png";
        }
    }

    public static String getVideoFilenameFromURL(String url) {
        try {
            return new File(new URL(url).getPath()).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return System.currentTimeMillis() + ".mp4";
        }
    }

    public static String getStringSizeLengthFile(long j) {
        try {

            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            float f = (float) j;
            if (f < 1048576.0f) {
                return decimalFormat.format((double) (f / 1024.0f)) + " Kb";
            } else if (f < 1.07374182E9f) {
                return decimalFormat.format((double) (f / 1048576.0f)) + " Mb";
            } else if (f >= 1.09951163E12f) {
                return "";
            } else {
                return decimalFormat.format((double) (f / 1.07374182E9f)) + " Gb";
            }
        } catch (Exception e) {
            return "NaN";
        }
    }

    public static String getStringSizeLengthFile_onlylong(long j) {
        try {

            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            float f = (float) j;
            if (f < 1048576.0f) {
                return decimalFormat.format((double) (f / 1024.0f));
            } else if (f < 1.07374182E9f) {
                return decimalFormat.format((double) (f / 1048576.0f));
            } else if (f >= 1.09951163E12f) {
                return "";
            } else {
                return decimalFormat.format((double) (f / 1.07374182E9f));
            }
        } catch (Exception e) {
            return "0";
        }
    }

    public static String formatDuration(long j) {
        String str;
        String str2;
        long j2 = (j / 1000) % 60;
        long j3 = (j / 60000) % 60;
        long j4 = j / 3600000;
        StringBuilder sb = new StringBuilder();
        if (j4 == 0) {
            str = "";
        } else if (j4 < 10) {
            str = String.valueOf(0 + j4);
        } else {
            str = String.valueOf(j4);
        }
        sb.append(str);
        if (j4 != 0) {
            sb.append("h");
        }
        String str3 = "00";
        if (j3 == 0) {
            str2 = str3;
        } else if (j3 < 10) {
            str2 = String.valueOf(0 + j3);
        } else {
            str2 = String.valueOf(j3);
        }
        sb.append(str2);
        sb.append("min");
        if (j2 != 0) {
            if (j2 < 10) {
                str3 = String.valueOf(0 + j2);
            } else {
                str3 = String.valueOf(j2);
            }
        }
        sb.append(str3);
        sb.append("s");
        return sb.toString();
    }

    public static boolean isMyPackedgeInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }


    private void mergeSongs(File mergedFile, File... mp3Files) {
        FileInputStream fisToFinal = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mergedFile);
            fisToFinal = new FileInputStream(mergedFile);
            for (File mp3File : mp3Files) {
                if (!mp3File.exists())
                    continue;
                FileInputStream fisSong = new FileInputStream(mp3File);
                SequenceInputStream sis = new SequenceInputStream(fisToFinal, fisSong);
                byte[] buf = new byte[1024];
                try {
                    for (int readNum; (readNum = fisSong.read(buf)) != -1; )
                        fos.write(buf, 0, readNum);
                } finally {
                    if (fisSong != null) {
                        fisSong.close();
                    }
                    if (sis != null) {
                        sis.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
                if (fisToFinal != null) {
                    fisToFinal.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
