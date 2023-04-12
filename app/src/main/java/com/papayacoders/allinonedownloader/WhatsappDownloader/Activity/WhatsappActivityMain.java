package com.papayacoders.allinonedownloader.WhatsappDownloader.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import com.papayacoders.allinonedownloader.R;

public class WhatsappActivityMain extends AppCompatActivity {
    public int STORAGE_PERMISSION_REQUEST_CODE = 12;
    String which;
    LinearLayout lWhatsapp, lWhatsBus;
    FrameLayout frameBanner;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        Drawable background = getResources().getDrawable(R.drawable.bg_status);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);
        setContentView(R.layout.whatsapp_activity);

        lWhatsapp = findViewById(R.id.lWhatsapp);
        lWhatsBus = findViewById(R.id.lWhatsBus);
        frameBanner = findViewById(R.id.frameBanner);
        LinearLayout ytChannel = findViewById(R.id.ytChannel);

        ytChannel.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@SalluTech1SUB")));
        });

        loadAds();
        loadGoogleAdmobBanner();
        lWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which = "WA";

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(WhatsappActivityMain.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            if (isWriteStoragePermissionGranted()) {
                                Intent intent = new Intent(WhatsappActivityMain.this, WhatsappActivity.class);
                                intent.putExtra("which", "WA");
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            if (isWriteStoragePermissionGranted()) {
                                Intent intent = new Intent(WhatsappActivityMain.this, WhatsappActivity.class);
                                intent.putExtra("which", "WA");
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd = null;
                        }
                    });

                } else {
                    if (isWriteStoragePermissionGranted()) {
                        Intent intent = new Intent(WhatsappActivityMain.this, WhatsappActivity.class);
                        intent.putExtra("which", "WA");
                        startActivity(intent);
                    }
                }


            }
        });

        lWhatsBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which = "WB";

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(WhatsappActivityMain.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            if (isWriteStoragePermissionGranted()) {
                                Intent intent = new Intent(WhatsappActivityMain.this, WhatsappActivity.class);
                                intent.putExtra("which", "WB");
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            if (isWriteStoragePermissionGranted()) {
                                Intent intent = new Intent(WhatsappActivityMain.this, WhatsappActivity.class);
                                intent.putExtra("which", "WB");
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd = null;
                        }
                    });

                } else {
                    if (isWriteStoragePermissionGranted()) {
                        if (isWriteStoragePermissionGranted()) {
                            Intent intent = new Intent(WhatsappActivityMain.this, WhatsappActivity.class);
                            intent.putExtra("which", "WB");
                            startActivity(intent);
                        }
                    }
                }


            }
        });
    }

    private void loadAds() {

        InterstitialAd.load(this, getString(R.string.interstitial_ID), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
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

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
                return false;
            }
        } else {
            return true;
        }
    }

    public void loadGoogleAdmobBanner() {

        if (frameBanner != null) {
            frameBanner.setVisibility(View.VISIBLE);
            AdView adView = new AdView(WhatsappActivityMain.this);
            adView.setAdUnitId(getResources().getString(R.string.banner_ID));

            frameBanner.addView(adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            AdSize adSize = getAdSize(WhatsappActivityMain.this);
            adView.setAdSize(adSize);
            adView.loadAd(adRequest);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);

                }
            });
        }
    }

    private AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (which.matches("WA")) {
                    Intent intent = new Intent(WhatsappActivityMain.this, WhatsappActivity.class);
                    intent.putExtra("which", "WA");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WhatsappActivityMain.this, WhatsappActivity.class);
                    intent.putExtra("which", "WB");
                    startActivity(intent);
                }
            } else {
                showDailog();
            }
        }
    }

    public void showDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WhatsappActivityMain.this);
        builder.setMessage("You need to give permission to access feature.");
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("GIVE PERMISSION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                ActivityCompat.requestPermissions(WhatsappActivityMain.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);
            }
        });
        builder.show();
    }
}