package com.mssoftwareindia.geniuskit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.databinding.ActivityAboutusBinding;
import com.mssoftwareindia.geniuskit.databinding.ActivityContactusBinding;
import com.mssoftwareindia.geniuskit.model.Aboutus;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ContactusActivity extends AppCompatActivity {
    public ContactusActivity context;
    public String TAG = "ContactusActivity";
    View view;
    public ActivityContactusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactusBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}