package com.mssoftwareindia.geniuskit.activities.retailer;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mssoftwareindia.geniuskit.MyApp;
import com.mssoftwareindia.geniuskit.R;
import com.mssoftwareindia.geniuskit.databinding.ActivityShopRegisterBinding;
import com.mssoftwareindia.geniuskit.model.City;
import com.mssoftwareindia.geniuskit.model.RegisterRes;
import com.mssoftwareindia.geniuskit.model.State;
import com.mssoftwareindia.geniuskit.rest_api.APIClient;
import com.mssoftwareindia.geniuskit.rest_api.ApiCallback;
import com.mssoftwareindia.geniuskit.rest_api.NewApiCall;
import com.mssoftwareindia.geniuskit.ui.NothingSelectedSpinnerAdapter;
import com.mssoftwareindia.geniuskit.utils.CustomDialog;
import com.mssoftwareindia.geniuskit.utils.NetworkUtil;
import com.mssoftwareindia.geniuskit.utils.ToastUtils;
import com.mssoftwareindia.geniuskit.utils.Utility;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ShopRegisterActivity extends AppCompatActivity {
    public ShopRegisterActivity context;
    public String TAG = "ShopRegisterActivity";
    View view;
    public ActivityShopRegisterBinding binding;
    public ArrayList<State.Response> stateArrayList = new ArrayList<>();
    public ArrayList<String> strStateList = new ArrayList<>();
    public ArrayList<City.Response> cityArrayList = new ArrayList<>();
    public ArrayList<String> strCiyList = new ArrayList<>();
    public String personName = "", satateId = "", Name = "", password = "", email = "", mobile = "", address = "",
            pincode = "", cityId = "", aadhar = "";
    double longitude=0, latitude=0;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopRegisterBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        context = this;
        getState();
       /* try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        } catch (Exception e) {
            longitude = 0;
            latitude = 0;
        }
*/
        binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                try {
                    if (position > 0) {
                        satateId = stateArrayList.get(position - 1).getName();
                        getCity();
                    } else {
                        satateId = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                try {
                    if (position > 0) {
                        cityId = cityArrayList.get(position - 1).getName();
                    } else {
                        cityId = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.txtRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation() {
        Name = binding.edtName.getText().toString();
        personName = binding.edtPersonName.getText().toString();
        email = binding.edtEmail.getText().toString();
        mobile = binding.edtMobile.getText().toString();
        address = binding.edtAddress.getText().toString();
        pincode = binding.edtPincode.getText().toString();
        aadhar = binding.edtAdhar.getText().toString();

        if (Name.isEmpty()) {
            binding.ipName.setError("please enter name");
        } else if (personName.isEmpty()) {
            binding.ipPersonName.setError("please enter person name");
            binding.ipName.setError(null);
        } else if (email.isEmpty()) {
            binding.ipEmail.setError("please enter email address");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
        } else if (!Utility.isValidEmail(email)) {
            binding.edtEmail.requestFocus();
            binding.ipEmail.setError("please enter valid email address");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
        } else if (mobile.isEmpty()) {
            binding.ipMobile.setError("please enter mobile number");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
            binding.ipEmail.setError(null);
        } else if (mobile.length() != 10) {
            binding.ipMobile.setError("please enter valid mobile number");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
            binding.ipEmail.setError(null);
        } else if (satateId.isEmpty()) {
            binding.ipState.setError("please select state");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
            binding.ipEmail.setError(null);
            binding.ipMobile.setError(null);

        } else if (cityId.isEmpty()) {
            binding.ipCity.setError("please select city");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
            binding.ipEmail.setError(null);
            binding.ipMobile.setError(null);
            binding.ipState.setError(null);
        } else if (pincode.isEmpty()) {
            binding.ipPincode.setError("please enter pincode");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
            binding.ipEmail.setError(null);
            binding.ipMobile.setError(null);
            binding.ipState.setError(null);
            binding.ipCity.setError(null);

        } else if (address.isEmpty()) {
            binding.ipAddress.setError("please enter address");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
            binding.ipEmail.setError(null);
            binding.ipMobile.setError(null);
            binding.ipState.setError(null);
            binding.ipCity.setError(null);
            binding.ipPincode.setError(null);
        } else if (aadhar.isEmpty()) {
            binding.ipAdhar.setError("please enter aadhar card number");
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
            binding.ipEmail.setError(null);
            binding.ipMobile.setError(null);
            binding.ipState.setError(null);
            binding.ipCity.setError(null);
            binding.ipPincode.setError(null);
            binding.ipAddress.setError(null);
        } else {
            binding.ipName.setError(null);
            binding.ipPersonName.setError(null);
            binding.ipEmail.setError(null);
            binding.ipMobile.setError(null);
            binding.ipState.setError(null);
            binding.ipCity.setError(null);
            binding.ipPincode.setError(null);
            binding.ipAddress.setError(null);
            binding.ipAdhar.setError(null);

            doAddShop();
        }
    }

    private void doAddShop() {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }

        Call<ResponseBody> call = APIClient.getInstance().addReatailer(Name, personName, email, mobile, satateId, cityId,
                pincode, address, aadhar, longitude + "", latitude + "", MyApp.apptoken);
        NewApiCall newApiCall = new NewApiCall();
        newApiCall.makeApiCall(context, true, call, new ApiCallback() {
            @Override
            public void success(String responseData) {
                Gson gson = new Gson();
                RegisterRes commonRes = gson.fromJson(responseData, RegisterRes.class);
                if (commonRes.getData().getStatus() == Utility.StandardStatusCodes.SUCCESS) {
                    _dialog(commonRes.getData().getResponse().get(0).getMessage());
                } else {
                    _dialog1(commonRes.getData().getResult());
                }
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    private void _dialog1(String msg) {

        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialogTwoButton(context, "Add Shop", msg,
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                    }
                }, null);
    }

    private void _dialog(String msg) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.showDialogTwoButton(context, "Add Shop", msg,
                "Ok", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customDialog.dismissdialogtwobutton();
                        onBackPressed();
                    }
                }, null);
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
                ArrayAdapter aa = new ArrayAdapter(context, R.layout.spinner_username_text, strStateList);
                aa.setDropDownViewResource(R.layout.spinner_username_text);
                binding.spState.setAdapter(new NothingSelectedSpinnerAdapter(
                        aa, R.layout.spinner_username_text,
                        context, "Select state"));
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }

    public void getCity() {
        Call<ResponseBody> call = APIClient.getInstance().getCity(satateId);
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
                ArrayAdapter aa = new ArrayAdapter(context, R.layout.spinner_username_text, strCiyList);
                aa.setDropDownViewResource(R.layout.spinner_username_text);
                binding.spCity.setAdapter(new NothingSelectedSpinnerAdapter(
                        aa, R.layout.spinner_username_text,
                        context, "Select city"));
            }

            @Override
            public void failure(String responseData) {
            }
        });
    }


}