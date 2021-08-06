package com.mssoftwareindia.geniuskit.rest_api;


public interface ApiCallback {
      void success(String responseData);
      void failure(String responseData);
}
