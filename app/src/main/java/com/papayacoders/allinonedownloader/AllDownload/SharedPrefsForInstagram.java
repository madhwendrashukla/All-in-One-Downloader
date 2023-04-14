package com.papayacoders.allinonedownloader.AllDownload;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class SharedPrefsForInstagram {
    public static String PREFERENCE = "Allvideodownloaderinstaprefs";
    public static String PREFERENCE_item = "instadata";
    public static String PREFERENCE_SESSIONID = "session_id";
    public static String PREFERENCE_USERID = "user_id";
    public static String PREFERENCE_COOKIES = "Cookies";
    public static String PREFERENCE_CSRF = "csrf";
    public static String PREFERENCE_ISINSTAGRAMLOGEDIN = "IsInstaLogin";
    public static Context context;

    private static SharedPrefsForInstagram instance;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    public SharedPrefsForInstagram() {
    }

    public SharedPrefsForInstagram(Context context) {
        SharedPrefsForInstagram.context = context;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        sharedPreference = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public static SharedPrefsForInstagram getInstance() {
        return instance;


    }


    public void setPreference(Map<String, String> map) {

        Gson gson = new Gson();
        String hashMapString = gson.toJson(map);

        editor = sharedPreference.edit();
        editor.putString(PREFERENCE_item, hashMapString);
        editor.apply();
    }

    public Map<String, String> getPreference(String key) {
        try {
            Gson gson = new Gson();
            String storedHashMapString = sharedPreference.getString(PREFERENCE_item, "oopsDintWork");
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();

            return gson.<HashMap<String, String>>fromJson(storedHashMapString, type);
        } catch (Exception exception) {
            return null;
        }
    }

    public void clearSharePrefs() {

        Map<String, String> map = new HashMap<>();

        map.put(SharedPrefsForInstagram.PREFERENCE_COOKIES, "");
        map.put(SharedPrefsForInstagram.PREFERENCE_CSRF, "");
        map.put(SharedPrefsForInstagram.PREFERENCE_ISINSTAGRAMLOGEDIN, "false");
        map.put(SharedPrefsForInstagram.PREFERENCE_SESSIONID, "");
        map.put(SharedPrefsForInstagram.PREFERENCE_USERID, "");


        Gson gson = new Gson();
        String hashMapString = gson.toJson(map);

        editor = sharedPreference.edit();
        editor.putString(PREFERENCE_item, hashMapString);
        editor.apply();

    }
}
