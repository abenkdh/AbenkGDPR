package uc.benkkstudio.abenkgdprlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

@SuppressLint("MissingPermission")
public class AbenkAdmob {
    private Context context;
    private InterstitialAd interstitialAd;

    public AbenkAdmob(Context context, InterstitialAd interstitialAd) {
        this.context = context;
        this.interstitialAd = interstitialAd;
    }


    public void loadInterstitialAd(String interstitialId) {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(interstitialId);
        interstitialAd.loadAd(AbenkGDPR.getAdRequest(context));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(AbenkGDPR.getAdRequest(context));
            }
        });
    }

    public void showInterstitialAd() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    public void showBannerAd(String bannerId, final LinearLayout linerlayout) {
        AdView adView = new AdView(context);
        adView.setAdUnitId(bannerId);
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(AbenkGDPR.getAdRequest(context));
        linerlayout.setVisibility(View.GONE);
        linerlayout.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int error) {
                linerlayout.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                linerlayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void initStartApp(String startAppId, Boolean disableSplash){
        StartAppSDK.init(context, startAppId, true);
        if(disableSplash){
            StartAppAd.disableSplash();
        }
    }

    public void startAppInterstitial(){
        StartAppAd.showAd(context);
    }

    public void startAppBanner(LinearLayout linearLayout){
        Banner startAppBanner = new Banner(context);
        linearLayout.addView(startAppBanner);
    }

}
