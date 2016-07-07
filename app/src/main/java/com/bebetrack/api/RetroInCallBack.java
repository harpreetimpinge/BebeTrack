package com.bebetrack.api;

import com.bebetrack.model.AuthData;

import retrofit2.Call;
import retrofit2.http.POST;

public interface RetroInCallBack {

    String ENDPOINT = "http://bebetrack.com/api/";

    @POST("create ")
    Call<AuthData> getAuthData();

}
