package com.mssoftwareindia.geniuskit.activities.sales;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.databinding.ActivityEditProfileBinding;
import com.mssoftwareindia.geniuskit.model.City;
import com.mssoftwareindia.geniuskit.model.Profile;
import com.mssoftwareindia.geniuskit.model.RegisterRes;
import com.mssoftwareindia.geniuskit.model.State;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.ui.NothingSelectedSpinnerAdapter;
import com.mssoftwareindia.geniuskit.utils.NetworkUtil;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public EditProfileActivity context;
    public String TAG = "EditProfileActivity";
    View view;
    public ActivityEditProfileBinding binding;
    public ArrayList<State.Response> stateArrayList = new ArrayList<>();
    public ArrayList<String> strStateList = new ArrayList<>();
    public ArrayList<City.Response> cityArrayList = new ArrayList<>();
    public ArrayList<String> strCiyList = new ArrayList<>();
    public String satateId = "", address = "",
            pincode = "", cityId = "";
   public ArrayList<Profile.Response> responseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        getGetProfile();


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.txtAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }
    private void getGetProfile() {
        Call<ResponseBody> call = APIClient.getInstance().getProfile(MyApp.userModel.getData().getResponse().get(0).getRegcode());
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                Profile product = gson.fromJson(responseData, Profile.class);
                if (product.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    responseArrayList.clear();
                    responseArrayList.addAll(product.getData().getResponse());
                     setData(responseArrayList.get(0));
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void setData(Profile.Response response) {
        binding.edtAddress.setText(response.getAddress());
        binding.edtPincode.setText(response.getPincode());
        satateId=response.getState();
        cityId=response.getCity();
        getState();
    }

    public void getState() {
        ArrayAdapter aa = new ArrayAdapter(context, R.layout.spinner_username_text, strCiyList);
        aa.setDropDownViewResource(R.layout.spinner_username_text);
        binding.spCity.setAdapter(new NothingSelectedSpinnerAdapter(
                aa, R.layout.spinner_username_text,
                context, "Select city"));
        Call<ResponseBody> call = APIClient.getInstance().getState();
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                State product = gson.fromJson(responseData, State.class);
                if (product.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    stateArrayList.clear();
                    stateArrayList.addAll(product.getData().getResponse());
                    for (int i = 0; i < stateArrayList.size(); i++) {
                        strStateList.add(product.getData().getResponse().get(i).getName());
                    }
                }
                addDataToStateSpin(stateArrayList);
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void addDataToStateSpin(ArrayList<State.Response> stateArrayList) {
        State.Response listModel = new State.Response();
        listModel.setName("Select State");
        listModel.setId("-10");
        stateArrayList.add(0, listModel);
        String[] namesArr = new String[stateArrayList.size()];

        for (int i = 0; i < stateArrayList.size(); i++) {
            namesArr[i] = stateArrayList.get(i).getName();
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.spinner_username_text, R.id.textViewSpinnerItem, namesArr);
        binding.spState.setAdapter(arrayAdapter);
        binding.spState.setSelection(0, false);

        binding.spState.setOnItemSelectedListener(this);
        int pos = arrayAdapter.getPosition(satateId);
        binding.spState.setSelection(pos);
    }

    private void validation() {
        address = binding.edtAddress.getText().toString();
        pincode = binding.edtPincode.getText().toString();

        if (satateId.isEmpty()) {
            binding.ipState.setError("please select state");

        } else if (cityId.isEmpty()) {
            binding.ipCity.setError("please select city");
            binding.ipState.setError(null);
        } else if (pincode.isEmpty()) {
            binding.ipPincode.setError("please enter pincode");
            binding.ipState.setError(null);
            binding.ipCity.setError(null);

        } else if (address.isEmpty()) {
            binding.ipAddress.setError("please enter address");
            binding.ipState.setError(null);
            binding.ipCity.setError(null);
            binding.ipPincode.setError(null);
        } else {
            binding.ipState.setError(null);
            binding.ipCity.setError(null);
            binding.ipPincode.setError(null);
            binding.ipAddress.setError(null);

            doEditProfile();
        }
    }

    private void doEditProfile() {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        Call<ResponseBody> call = APIClient.getInstance().editProfile(MyApp.userModel.getData().getResponse().get(0).getRegcode(), satateId, cityId, pincode, address);
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                RegisterRes commonRes = gson.fromJson(responseData, RegisterRes.class);
                if (commonRes.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    ToastUtils.show(context, commonRes.getData().getResponse().get(0).getMessage());
                    context.onBackPressed();
                } else {
                    ToastUtils.show(context, commonRes.getData().getResult());
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    public void getCity(String satateId) {
        Call<ResponseBody> call = APIClient.getInstance().getCity(String.valueOf(satateId));
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                City product = gson.fromJson(responseData, City.class);
                if (product.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    cityArrayList.clear();
                    cityArrayList.addAll(product.getData().getResponse());
                    for (int i = 0; i < cityArrayList.size(); i++) {
                        strCiyList.add(product.getData().getResponse().get(i).getName());

                    }

                }
                addDataToCitySpin(cityArrayList);
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void addDataToCitySpin(ArrayList<City.Response> cityArrayList) {
        City.Response listModel = new City.Response();
        listModel.setName("Select City");
        listModel.setId("-10");
        cityArrayList.add(0, listModel);
        String[] namesArr = new String[cityArrayList.size()];

        for (int i = 0; i < cityArrayList.size(); i++) {
            namesArr[i] = cityArrayList.get(i).getName();
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.spinner_username_text, R.id.textViewSpinnerItem, namesArr);
        binding.spCity.setAdapter(arrayAdapter);
        binding.spCity.setSelection(0, false);
        binding.spCity.setOnItemSelectedListener(this);
        String city = "";
        for (City.Response myItem : cityArrayList) {
            // do stuff
            if (myItem.getName() == cityId) {
                city = myItem.getName();
            }
        }
        int pos = arrayAdapter.getPosition(cityId);
        binding.spCity.setSelection(pos);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spState) {
            if (!binding.spState.getSelectedItem().equals("Select State")) {
                satateId = stateArrayList.get(position).getName();
                getCity(satateId);
            }
        } else if (parent.getId() == R.id.spCity) {
            if (!binding.spCity.getSelectedItem().equals("Select city")) {
                //  city_id = Integer.parseInt(cityArrayList.get(position).getId());
                cityId = cityArrayList.get(position).getName();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}