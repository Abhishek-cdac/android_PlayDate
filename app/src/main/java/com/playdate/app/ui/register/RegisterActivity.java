package com.playdate.app.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

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
import com.playdate.app.R;
import com.playdate.app.databinding.ActivityRegisterBinding;
import com.playdate.app.model.RegisterUser;
import com.playdate.app.ui.login.LoginActivity;
import com.playdate.app.ui.register.otp.OTPActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel registerViewModel;

    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new RegisterViewModel();
        registerViewModel.init();
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        binding.setLifecycleOwner(this);
        binding.setVMRegister(registerViewModel);

        registerViewModel.getFinish().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });

        registerViewModel.getRegisterUser().observe(this, new Observer<RegisterUser>() {
            @Override
            public void onChanged(RegisterUser registerUser) {
                if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getFullname())) {

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getAddress())) {

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPhoneNumber())) {

                } else if ((registerUser).getPhoneNumber().length() < 10) {

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getEmail())) {

                } else if (!registerUser.isEmailValid()) {

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPassword())) {

                } else {
                    //next Page
                    Intent mIntent = new Intent(RegisterActivity.this, OTPActivity.class);
                    mIntent.putExtra("Name", registerUser.getFullname());
                    mIntent.putExtra("Phone", registerUser.getPhoneNumber());
                    mIntent.putExtra("Address", registerUser.getAddress());
                    mIntent.putExtra("Email", registerUser.getEmail());
                    mIntent.putExtra("Password", registerUser.getPassword());
                    startActivity(mIntent);
                }

            }
        });

        registerViewModel.fbClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean register) {

                calltoFB();

            }
        });

    }

    private void calltoFB() {
        FacebookSdk.sdkInitialize(RegisterActivity.this);
        callbackManager = CallbackManager.Factory.create();
        facebookLogin();

        loginManager.logInWithReadPermissions(
                RegisterActivity.this,
                Arrays.asList(
                        "email",
                        "public_profile",
                        "user_birthday"));

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(
                requestCode,
                resultCode,
                data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void facebookLogin() {
        loginManager
                = LoginManager.getInstance();
        callbackManager
                = CallbackManager.Factory.create();

        loginManager
                .registerCallback(
                        callbackManager,
                        new FacebookCallback<LoginResult>() {

                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                GraphRequest request = GraphRequest.newMeRequest(

                                        loginResult.getAccessToken(),

                                        new GraphRequest.GraphJSONObjectCallback() {

                                            @Override
                                            public void onCompleted(JSONObject object,
                                                                    GraphResponse response) {

                                                if (object != null) {
                                                    try {
                                                        String name = object.getString("name");
                                                        String email = object.getString("email");
                                                        String fbUserID = object.getString("id");

                                                        Log.d("Name of user ", name);
                                                        disconnectFromFacebook();

                                                        // do action after Facebook login success
                                                        // or call your API
                                                    } catch (JSONException | NullPointerException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString(
                                        "fields",
                                        "id, name, email, gender, birthday");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel() {
                                Log.v("LoginScreen", "---onCancel");
                            }

                            @Override
                            public void onError(FacebookException error) {
                                // here write code when get error
                                Log.v("LoginScreen", "----onError: "
                                        + error.getMessage());
                            }
                        });
    }

    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }
}
