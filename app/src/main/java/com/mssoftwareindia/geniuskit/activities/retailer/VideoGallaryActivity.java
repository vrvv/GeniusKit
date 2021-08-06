package com.mssoftwareindia.geniuskit.activities.retailer;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.adapter.DistributorAdapter;
import com.mssoftwareindia.geniuskit.adapter.VideoListAdapter;
import com.mssoftwareindia.geniuskit.databinding.ActivityVideoGallaryBinding;
import com.mssoftwareindia.geniuskit.model.DistributorListResponse;
import com.mssoftwareindia.geniuskit.model.VideoListResponse;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class VideoGallaryActivity extends AppCompatActivity {
    public VideoGallaryActivity context;
    public String TAG = "VideoGallaryActivity";
    View view;
    public ActivityVideoGallaryBinding binding;
    public VideoListAdapter adapter;
    public ArrayList<VideoListResponse.Response> responseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoGallaryBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.recyclerVideo.setLayoutManager(new LinearLayoutManager(context));
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.recyclerVideo.setLayoutAnimation(controller);
        hitVideoGallryApi();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void hitVideoGallryApi() {
        Call<ResponseBody> call = APIClient.getInstance().getVideoGallry();
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                VideoListResponse aboutus = gson.fromJson(responseData, VideoListResponse.class);
                if (aboutus.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    responseArrayList.clear();
                    responseArrayList.addAll(aboutus.getData().getResponse());

                }
                adapter = new VideoListAdapter(context, responseArrayList);
                binding.recyclerVideo.setAdapter(adapter);

            }

            @Override
            public void failure(String responseData) {
            }
        });

    }
}