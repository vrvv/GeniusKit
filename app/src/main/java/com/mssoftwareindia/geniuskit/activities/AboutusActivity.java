package com.mssoftwareindia.geniuskit.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.databinding.ActivityAboutusBinding;
import com.mssoftwareindia.geniuskit.databinding.ActivityLoginBinding;
import com.mssoftwareindia.geniuskit.model.Aboutus;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class AboutusActivity extends AppCompatActivity {
    public AboutusActivity context;
    public String TAG = "AboutusActivity";
    View view;
    public ActivityAboutusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutusBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        if (Utility.isNetworkAvailable(context)) {
            hitAboutusApi();
        }
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void hitAboutusApi() {
        Call<ResponseBody> call = APIClient.getInstance().aboutUs();
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                Aboutus aboutus = gson.fromJson(responseData, Aboutus.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    binding.tvContent.setText(Html.fromHtml(aboutus.getData().getAbout().getContent()));
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

}