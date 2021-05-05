package com.playdate.app.data.api;

import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.RegisterResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

    @FormUrlEncoded
    @POST("user/register")
    Call<RegisterResult> registerUser(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> login(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("user/verify-otp")
    Call<LoginResponse> verifyOtp(@FieldMap Map<String, String> param);


}
