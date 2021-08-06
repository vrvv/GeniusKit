package com.mssoftwareindia.geniuskit;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mssoftwareindia.geniuskit.model.ProductResponse;
import com.mssoftwareindia.geniuskit.model.RetailerUserModel;
import com.mssoftwareindia.geniuskit.model.SalesUserModel;
import com.mssoftwareindia.geniuskit.utils.MySharedPref;

import java.util.ArrayList;
import java.util.List;


public class MyApp extends MultiDexApplication {
    public static MyApp instance;
    public static boolean isInfo = false;
    public final static boolean RETROFIT_SHOW_LOG = true;
    public static boolean isInAppLocationAdd = false;
    public static MySharedPref mySharedPref;
    //  public static MainUser mainUser;
    public static SalesUserModel userModel;
    public static RetailerUserModel retailerUserModel;
    public static String apptoken = "";
    public static List<ProductResponse.SubProduct> subProductArrayList = new ArrayList<>();

    public void onCreate() {
        super.onCreate();

        try {
            instance = this;
            mySharedPref = new MySharedPref(instance);
            Class.forName("android.os.AsyncTask");

        } catch (ClassNotFoundException e) {
        }
        FirebaseApp.initializeApp(instance);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        apptoken = token;
                        mySharedPref.setDeviceToken(token);
                        // Log and toast
                        Log.d("TAG", "TOKEN : " + token);
                    }
                });
    }


}