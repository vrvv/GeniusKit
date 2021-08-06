package com.mssoftwareindia.geniuskit.rest_api;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.model.CommonRes;
import com.mssoftwareindia.geniuskit.utils.CustomDialog;
import com.mssoftwareindia.geniuskit.utils.LogHelper;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewApiCall {

    private CustomDialog customDialog;
    private String TAG = "api";
    private Dialog dialog;
    private String msg = "";
    private SharedPreferences preferences;

    public void makeApiCall(final Context context, final boolean isLoadingNeeded, Call<ResponseBody> call, final ApiCallback ApiCallback) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        customDialog = new CustomDialog();
        if (isConnectingToInternet(context)) {
            //Todo isLoadingNeed
            if (isLoadingNeeded)
                customDialog.displayProgress(context);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (isLoadingNeeded)
                        customDialog.dismissProgress(context);
                    Gson gson = new Gson();
                    String bodyString = null;
                    if (response.isSuccessful()) {
                        try {
                            bodyString = new String(response.body().bytes());
                            LogHelper.d("response Successful :-> " , bodyString);
                            CommonRes commonRes = gson.fromJson(bodyString, CommonRes.class);
                            switch (commonRes.getData().getStatus()) {
                                case Utility.StandardStatusCodes.SUCCESS:

                                    if (commonRes.getData().getStatus() == 200) {
                                        ApiCallback.success(bodyString);
                                    } else {
                                        ApiCallback.success(bodyString);
                                    }
                                    break;
                                case Utility.StandardStatusCodes.NO_DATA_FOUND:
                                    ApiCallback.success(bodyString);
                                    break;
                                case Utility.StandardStatusCodes.BAD_REQUEST:
                                    ApiCallback.success(bodyString);
                                    break;
                                case Utility.StandardStatusCodes.DUPLICATE_ERROR:
                                case Utility.StandardStatusCodes.CONFLICT:
                                case Utility.StandardStatusCodes.NOTACCEPTABLE:
//                                    msg = commonRes.getData().getResult().getResponse();
//                                    Utils.showToast(context, msg);
                                    break;
                                case Utility.StandardStatusCodes.UNAUTHORISE:
                                    ApiCallback.failure(bodyString);
                                    break;
                                default:
                                    break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        if (response.code() == Utility.StandardStatusCodes.UNAUTHORISE) {
                            try {
                                bodyString = new String(response.errorBody().bytes());
                                ApiCallback.failure(bodyString);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(context, "network error", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    LogHelper.d("response failure :-> " , t.toString());
                }
            });
        } else {
            ToastUtils.show(context,"No Internet Connection");
        }
    }

    private boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivity != null;
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}