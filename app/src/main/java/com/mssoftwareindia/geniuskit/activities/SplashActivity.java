package com.mssoftwareindia.geniuskit.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.activities.retailer.RetailerDashboardActivity;
import com.mssoftwareindia.geniuskit.activities.sales.DashBoardActivity;
import com.mssoftwareindia.geniuskit.databinding.ActivitySplashBinding;
import com.mssoftwareindia.geniuskit.model.RetailerUserModel;
import com.mssoftwareindia.geniuskit.model.SalesUserModel;
import com.mssoftwareindia.geniuskit.utils.VersionHelper;

import org.jsoup.Jsoup;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    public SplashActivity instance;
    public String TAG = "SplashActivity";
    public ActivitySplashBinding binding;
    View view;
    public String currentVersion = "";
    public int versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        instance = this;
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            currentVersion = pInfo.versionName;
            versionCode = pInfo.versionCode;
            new GetPlayStoreVersion().execute("");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            gotoNextScreen();
            binding.linUpdate.setVisibility(View.GONE);
        }

    }
    private class GetPlayStoreVersion extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return Jsoup.connect("https://play.google.com/store/apps/details?id=" + SplashActivity.this.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (VersionHelper.compare(currentVersion, result) == -1) {

                    binding.linUpdate.setVisibility(View.VISIBLE);
                    binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    });

                } else {
                    binding.linUpdate.setVisibility(View.GONE);
                    gotoNextScreen();
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                binding.linUpdate.setVisibility(View.GONE);
                gotoNextScreen();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    private void gotoNextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MyApp.mySharedPref.getIsLoggedIn()) {
                    if (MyApp.mySharedPref.getIsReatailer()) {
                        Gson gson = new Gson();
                        String json = MyApp.mySharedPref.getReatailerModel();
                        MyApp.retailerUserModel = gson.fromJson(json, RetailerUserModel.class);
                        Intent intent = new Intent(instance, RetailerDashboardActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    } else {
                        Gson gson = new Gson();
                        String json = MyApp.mySharedPref.getUserModel();
                        MyApp.userModel = gson.fromJson(json, SalesUserModel.class);
                        Intent intent = new Intent(instance, DashBoardActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                } else {
                    Intent intent = new Intent(instance, WelcomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }

            }
        }, 2000);
    }

}