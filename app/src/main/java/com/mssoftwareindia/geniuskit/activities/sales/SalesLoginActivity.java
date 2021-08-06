package com.mssoftwareindia.geniuskit.activities.sales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.databinding.ActivityLoginBinding;
import com.mssoftwareindia.geniuskit.model.SalesUserModel;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.CustomDialog;
import com.mssoftwareindia.geniuskit.utils.NetworkUtil;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class SalesLoginActivity extends AppCompatActivity {
    public SalesLoginActivity context;
    public String TAG = "LoginActivity";
    View view;
    public ActivityLoginBinding binding;
    public String userNameL = "", passwordL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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
             /*   Intent i = new Intent(insatnce, RegisterActivity.class);
                startActivity(i);
*/
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void validationLogin(View view) {
        userNameL = binding.edtUsername.getText().toString();
        passwordL = binding.edtPassword.getText().toString();
        if (userNameL.isEmpty()) {
            binding.ipUsername.setError("plese enter reg code");
        } else if (passwordL.isEmpty()) {
            binding.ipPassword.setError("plese enter password");
            binding.ipUsername.setError(null);
        } else {
            binding.ipUsername.setError(null);
            binding.ipPassword.setError(null);
            hitApiLogin();
        }
    }

    private void hitApiLogin() {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        Call<ResponseBody> call = APIClient.getInstance().salesLogin(userNameL, passwordL, MyApp.apptoken);
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                SalesUserModel loginModel = gson.fromJson(responseData, SalesUserModel.class);
                if (loginModel.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    MyApp.userModel = loginModel;
                    String json = gson.toJson(MyApp.userModel);
                    MyApp.mySharedPref.setUserModel(json);
                    MyApp.mySharedPref.setIsLoggedIn(true);
                    MyApp.mySharedPref.setUserId(loginModel.getData().getResponse().get(0).getRegcode());
                    Intent i = new Intent(context, DashBoardActivity.class);
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


}