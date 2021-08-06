package com.mssoftwareindia.geniuskit.activities.retailer;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.databinding.ActivityRetailerLoginBinding;
import com.mssoftwareindia.geniuskit.model.CommonRes;
import com.mssoftwareindia.geniuskit.model.RetailerUserModel;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.ui.OtpView;
import com.mssoftwareindia.geniuskit.utils.CustomDialog;
import com.mssoftwareindia.geniuskit.utils.LogHelper;
import com.mssoftwareindia.geniuskit.utils.NetworkUtil;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.List;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

public class RetailerLoginActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {
    public RetailerLoginActivity context;
    public String TAG = "RetailerLoginActivity";
    View view;
    public ActivityRetailerLoginBinding binding;
    public String mobile_no = "";
    public BottomSheetDialog mBottomSheetVerifyOTP;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    String[] PERMISSIONS_11 = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailerLoginBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i = new Intent(insatnce, ForgotPasswordActivity.class);
                startActivity(i);
*/
            }
        });
        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if (hasLocationAnPermissions()) {

                    Intent i = new Intent(context, ShopRegisterActivity.class);
                    startActivity(i);
               /* } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        EasyPermissions.requestPermissions(
                                context,
                                "We need all required permission for add reatiler. please give all required permission.",
                                PERMISSION_ALL,
                                PERMISSIONS_11);
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Permission?")
                                .setMessage("We need all required permission for add reatiler. please give all required permission.")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        // Ask for both permissions

                                            EasyPermissions.requestPermissions(
                                                    context,
                                                    "location",
                                                    PERMISSION_ALL,
                                                    PERMISSIONS);


                                    }
                                }).create().show();
                    }
                }*/

              /*  if (!hasPermissions(context, PERMISSIONS)) {

                    new AlertDialog.Builder(context)
                            .setTitle("Permission?")
                            .setMessage("We need all required permission for add reatiler. please give all required permission.")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    ActivityCompat.requestPermissions(context, PERMISSIONS, PERMISSION_ALL);
                                }
                            }).create().show();


                }else{
                    Intent i = new Intent(context, ShopRegisterActivity.class);
                    startActivity(i);
                }*/


            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void validationLogin(View view) {
        mobile_no = binding.edtMobile.getText().toString();
        if (mobile_no.isEmpty()) {
            binding.ipUsername.setError("plese enter mobile number");
        } else {
            binding.ipUsername.setError(null);
            hitGetOtp();
        }
    }


    private void hitGetOtp() {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        Call<ResponseBody> call = APIClient.getInstance().getOtp(mobile_no);
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                CommonRes loginModel = gson.fromJson(responseData, CommonRes.class);
                if (loginModel.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    bottomOTP();
                    mBottomSheetVerifyOTP.show();

                } else {
                    _dialog(loginModel.getData().getResult());
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });


    }

    private void bottomOTP() {
        View view = getLayoutInflater().inflate(R.layout.bottom_verify_otp, null);
        mBottomSheetVerifyOTP = new BottomSheetDialog(context);
        mBottomSheetVerifyOTP.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetVerifyOTP.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        OtpView mOtpView = view.findViewById(R.id.otp_view);
        TextView tvContinue = view.findViewById(R.id.tvContinue);
        TextView tvMobile = view.findViewById(R.id.tvMobile);
        tvMobile.setText(binding.edtMobile.getText().toString());
        TextView tvResend = view.findViewById(R.id.tvResend);
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitGetOtp();
                mBottomSheetVerifyOTP.dismiss();
            }
        });
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpInput = mOtpView.getOTP().toString().trim();
                if (otpInput.isEmpty()) {
                    ToastUtils.show(context, "Please enter otp");
                    return;
                }
                mBottomSheetVerifyOTP.dismiss();
                validateMobileAPI(otpInput);
            }
        });
    }

    private void validateMobileAPI(String otpInput) {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        Call<ResponseBody> call = APIClient.getInstance().loginShop(mobile_no, otpInput, MyApp.apptoken);
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                RetailerUserModel loginModel = gson.fromJson(responseData, RetailerUserModel.class);
                if (loginModel.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    MyApp.retailerUserModel = loginModel;
                    String json = gson.toJson(MyApp.retailerUserModel);
                    MyApp.mySharedPref.setReatailerModel(json);
                    MyApp.mySharedPref.setIsLoggedIn(true);
                    MyApp.mySharedPref.setIsReatailer(true);
                    Intent i = new Intent(context, RetailerDashboardActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    _dialog(loginModel.getData().getResult());
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void _dialog(String msg) {

        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialogTwoButton(context, "Login", msg,
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                    }
                }, null);
    }

    private boolean hasLocationAnPermissions() {
        return EasyPermissions.hasPermissions(this, PERMISSIONS_11);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        LogHelper.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        LogHelper.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        LogHelper.d(TAG, "onRationaleAccepted:" + requestCode);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        LogHelper.d(TAG, "onRationaleDenied:" + requestCode);
    }
}