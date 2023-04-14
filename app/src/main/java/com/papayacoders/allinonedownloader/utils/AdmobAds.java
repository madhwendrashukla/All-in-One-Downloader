package com.papayacoders.allinonedownloader.utils;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.papayacoders.allinonedownloader.MainActivity;
import com.papayacoders.allinonedownloader.R;


public class AdmobAds {

    private Context mContext;
    private AdView mBannerAd;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequestInter = new AdRequest.Builder().build();

    public AdmobAds(Context context) {
        this.mContext = context;

        MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        setBannerAd();
        setInterstitialAd();
    }

    public void setBannerAd(){
        mBannerAd = new AdView(mContext);
        mBannerAd.setAdSize(AdSize.SMART_BANNER);
        mBannerAd.setAdUnitId(AdmobID.BANNER_ID);
        LinearLayout adContainer = ((MainActivity) mContext).findViewById(R.id.main_banner_container);
        adContainer.addView(mBannerAd);
        mBannerAd.loadAd(adRequestInter);
    }

    public void setInterstitialAd(){
        InterstitialAd.load(mBannerAd.getContext(), "ca-app-pub-3940256099942544/1033173712", new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;

            }
        });
    }


    public void loadInterstitialAd(){


        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {

            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {

            }

            @Override
            public void onAdShowedFullScreenContent() {
                mInterstitialAd = null;
            }
        });

    }
}
