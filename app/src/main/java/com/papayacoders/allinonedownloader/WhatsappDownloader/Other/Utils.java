package com.papayacoders.allinonedownloader.WhatsappDownloader.Other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Utils {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    Activity mContext;
    public static final String PREF_NAME = "com.wp";

    public Utils(Activity context) {
        mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setWAUri(String spin) {
        editor.putString("waUri", spin);
        editor.commit();

    }
    public String getWAUri() {
        return pref.getString("waUri", "null");
    }

    public void setWBUri(String spin) {
        editor.putString("wbUri", spin);
        editor.commit();

    }
    public String getWBUri() {
        return pref.getString("wbUri", "null");
    }


}
