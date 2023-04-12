package com.papayacoders.allinonedownloader.browsing_feature;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.papayacoders.allinonedownloader.R;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class AdBlocker implements Serializable {
    private List<String> filters;
    private String easylistLastModified;

    AdBlocker() {
        filters = new ArrayList<>();
    }

    public void update(final Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("settings", 0);
        final String today = new SimpleDateFormat("dd MM yyyy", Locale.getDefault()).format(new Date());
        Log.d("VDDebug", "update: " + today + " " + prefs.getString(context.getString(R.string.adFiltersLastUpdated), "") );
        if (!today.equals(prefs.getString(context.getString(R.string.adFiltersLastUpdated), ""))) {
            final AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setMessage("Updating. Please wait...");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            new Thread() {
                @Override
                public void run() {
                    String easyList = "https://easylist.to/easylist/easylist.txt";
                    List<String> tempFilters = new ArrayList<>();
                    try {
                        URLConnection uCon = new URL(easyList).openConnection();
                        if (uCon != null) {
                            InputStream in = uCon.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                if (line.contains("Last modified")) {
                                    if (line.equals(easylistLastModified)) {
                                        Log.i("VDDebug", "ads filters is already up to date");
                                        reader.close();
                                        in.close();
                                        dialog.dismiss();
                                        return;
                                    } else {
                                        easylistLastModified = line;
                                    }
                                } else if (!line.startsWith("!") || !line.startsWith("[")) {
                                    tempFilters.add(line);
                                }
                            }
                            if (!tempFilters.isEmpty()) {
                                filters = tempFilters;
                                Log.i("VDDebug", "updating ads filters complete. Total: " +
                                        filters.size());
                            }
                            File file = new File(context.getFilesDir(), "ad_filters.dat");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                            objectOutputStream.writeObject(AdBlocker.this);
                            objectOutputStream.close();
                            fileOutputStream.close();
                        }
                        dialog.dismiss();
                        prefs.edit().putString(context.getString(R.string.adFiltersLastUpdated), today).apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }
                }
            }.start();
        }
    }

    public boolean checkThroughFilters(String url) {
        for (String filter : filters) {
            String filterFormat = filter.replace("||", "//");
            if (url.contains(filterFormat)) {
                Log.d("VDDebug", "checkThroughFilters: " + filter + " " +  url);
                return true;
            }
        }
        return false;
    }
}
