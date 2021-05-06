package com.playdate.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.playdate.app.model.LoginUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginViewModel extends ViewModel {

    public LoginViewModel() {
//        EmailAddress.setValue("ajit.jadhav36@gmail.com");
//        Password.setValue("80868086");
    }

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> ForgotClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> onFBClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> onGoogleClick = new MutableLiveData<>();

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private MutableLiveData<LoginUser> userMutableLiveData;
//    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> getRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClickRegister(View view) {
        Log.d("ddd", "asdzsd");
        RegisterClick.setValue(true);
    }

    public void onClickLogin(View view) {

        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(loginUser);
    }

    public void onClickForgotPass(View view) {

        ForgotClick.setValue(true);

//        LoginUser loginUser = new LoginUser(EmailAddress.getValue(), Password.getValue());
//
//        userMutableLiveData.setValue(loginUser);


    }

    public MutableLiveData<Boolean> getForgotClick() {

        if (ForgotClick == null) {
            ForgotClick = new MutableLiveData<>();
        }
        return ForgotClick;

    }

    public MutableLiveData<Boolean> GoogleClick() {

        if (onGoogleClick == null) {
            onGoogleClick = new MutableLiveData<>();
        }
        return onGoogleClick;

    }


    public void onClickgPlus(View view) {

        onGoogleClick.setValue(true);

    }


    public MutableLiveData<Boolean> fbClick() {

        if (onFBClick == null) {
            onFBClick = new MutableLiveData<>();
        }
        return onFBClick;

    }


    public void onClickFb(View view) {

        onFBClick.setValue(true);


    }


//   CLIENT ID  - 796495283790-tmc38onsosd8gvcs206bknct6k6mh6po.apps.googleusercontent.com
//   CLIENT SECRET -  uu2Rrd-c1TG4Il9wXbbLAppJ

//    public void facebookLogin() {
//        loginManager
//                = LoginManager.getInstance();
//        callbackManager
//                = CallbackManager.Factory.create();
//
//        loginManager
//                .registerCallback(
//                        callbackManager,
//                        new FacebookCallback<LoginResult>() {
//
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                GraphRequest request = GraphRequest.newMeRequest(
//
//                                        loginResult.getAccessToken(),
//
//                                        new GraphRequest.GraphJSONObjectCallback() {
//
//                                            @Override
//                                            public void onCompleted(JSONObject object,
//                                                                    GraphResponse response) {
//
//                                                if (object != null) {
//                                                    try {
//                                                        String name = object.getString("name");
//                                                        String email = object.getString("email");
//                                                        String fbUserID = object.getString("id");
//
//                                                        Log.d("Name of user ", name);
//                                                        disconnectFromFacebook();
//
//                                                        // do action after Facebook login success
//                                                        // or call your API
//                                                    } catch (JSONException | NullPointerException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                }
//                                            }
//                                        });
//
//                                Bundle parameters = new Bundle();
//                                parameters.putString(
//                                        "fields",
//                                        "id, name, email, gender, birthday");
//                                request.setParameters(parameters);
//                                request.executeAsync();
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                Log.v("LoginScreen", "---onCancel");
//                            }
//
//                            @Override
//                            public void onError(FacebookException error) {
//                                // here write code when get error
//                                Log.v("LoginScreen", "----onError: "
//                                        + error.getMessage());
//                            }
//                        });
//    }
//
//    public void disconnectFromFacebook() {
//        if (AccessToken.getCurrentAccessToken() == null) {
//            return; // already logged out
//        }
//
//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                "/me/permissions/",
//                null,
//                HttpMethod.DELETE,
//                new GraphRequest
//                        .Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse graphResponse) {
//                        LoginManager.getInstance().logOut();
//                    }
//                })
//                .executeAsync();
//    }
}
// checck now and doin activity
