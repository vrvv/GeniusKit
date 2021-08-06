package com.mssoftwareindia.geniuskit.utils;

import android.content.Context;
import android.net.ConnectivityManager;


public class NetworkUtil {

    private Context context;
    private static NetworkUtil networkUtil;
    private ConnectivityManager connectivityManager;

    private NetworkUtil(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkUtil getInstance(Context context) {
        if (networkUtil == null) {
            networkUtil = new NetworkUtil(context);
        }
        return networkUtil;
    }

    public boolean isConnected() {
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}