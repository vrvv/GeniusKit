package com.mssoftwareindia.geniuskit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;


/**
 * Created by Karan-Pitroda on 1/2/2019.
 */

public class MySharedPref {
    public MyApp app;
    SharedPreferences shared;
    SharedPreferences.Editor et;
    private String MY_PREF_NAME;
    private Context context;
    public final String user_id = "userid";
    public final String companyid = "companyid";
    public final String userModel = "userModel";
    public final String reatailerModel = "retailerModel";
    public final String locationid = "locationid";
    public final String cultureId = "cultureId";
    public static String DEVICE_TOKEN = "device_token";



    public MySharedPref(Context ct) {
        this.context = ct;
        MY_PREF_NAME = context.getResources().getString(R.string.app_name);
        shared = ct.getSharedPreferences(MY_PREF_NAME, 0);
        et = shared.edit();
    }


    public String getString(final String key, final String value) {

        return shared.contains(key) ? shared.getString(key, "") : "";

    }

    public void sharedPrefClear() {

        et.clear();
        et.apply();
        et.commit();

    }
    public String getDeviceToken() {
        return shared.contains(DEVICE_TOKEN) ? shared.getString(DEVICE_TOKEN, "") : "";
    }

    public void setDeviceToken(String Login_token) {
        et.putString(DEVICE_TOKEN, Login_token);
        et.commit();
    }
    public String getCultureId() {
        return shared.contains(cultureId) ? shared.getString(cultureId, "") : "";
    }
    public void setCultureId(String culture) {
        et.putString(cultureId, culture);
        et.commit();
    }
    public void setLocationid(String locationId) {
        et.putString(locationid, locationId);
        et.commit();
    }

    public String getLocationid() {
        return shared.contains(locationid) ? shared.getString(locationid, "") : "";
    }
    public String getUserId() {
        return shared.contains(user_id) ? shared.getString(user_id, "") : "";
    }
    public void setUserId(String UserId) {
        et.putString(user_id, UserId);
        et.commit();
    }
    public String getCompanyId() {
        return shared.contains(companyid) ? shared.getString(companyid, "") : "";
    }
    public void setCompanyId(String compId) {
        et.putString(companyid, compId);
        et.commit();
    }
    public String getUserModel() {
        return shared.contains(userModel) ? shared.getString(userModel, "") : "";
    }

    public void setUserModel(String UserModel) {
        et.putString(userModel, UserModel);
        et.commit();
    }
    public String getReatailerModel() {
        return shared.contains(reatailerModel) ? shared.getString(reatailerModel, "") : "";
    }

    public void setReatailerModel(String UserModel) {
        et.putString(reatailerModel, UserModel);
        et.commit();
    }
    public final String isLoggedIn = "isLoggedIn";
    public final String isReatailer = "isReatailer";
    public boolean getIsReatailer() {
        return shared.contains(isReatailer) ? shared.getBoolean(isReatailer, false) : false;
    }

    public void setIsReatailer(boolean isfirst) {
        et.putBoolean(isReatailer, isfirst);
        et.commit();
    } public boolean getIsLoggedIn() {
        return shared.contains(isLoggedIn) ? shared.getBoolean(isLoggedIn, false) : false;
    }

    public void setIsLoggedIn(boolean isfirst) {
        et.putBoolean(isLoggedIn, isfirst);
        et.commit();
    }
    public void clearApp() {
        et.clear();
        et.apply();
        et.commit();
    }
}
