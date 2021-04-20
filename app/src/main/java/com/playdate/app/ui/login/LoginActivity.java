package com.playdate.app.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.playdate.app.databinding.ActivityLoginBinding;
import com.playdate.app.databinding.ActivityOnboardingBinding;
import com.playdate.app.model.LoginUser;
import com.playdate.app.ui.onboarding.OnBoardingActivity;
import com.playdate.app.ui.onboarding.OnBoardingViewModel;
import com.playdate.app.ui.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new LoginViewModel();
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(LoginUser loginUser) {

                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
                    binding.loginEmail.setError("Enter an E-Mail Address");
                    binding.loginEmail.requestFocus();
                } else if (!loginUser.isEmailValid()) {
                    binding.loginEmail.setError("Enter a Valid E-mail Address");
                    binding.loginEmail.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                    binding.loginPassword.setError("Enter a Password");
                    binding.loginPassword.requestFocus();
                } else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    binding.loginPassword.setError("Enter at least 6 Digit password");
                    binding.loginPassword.requestFocus();
                } else {
                    // call API
                }

            }
        });
        loginViewModel.getRegisterUser().observe(this, register -> {

            if (register) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            } else {
                // call API
            }

        });

//        setContentView(R.layout.activity_login);
//        EditText password = findViewById(R.id.login_Password);
//        Button login_btn = findViewById(R.id.login_button);
//        ImageView facebook = findViewById(R.id.fb);
//        ImageView twitter = findViewById(R.id.twitter);
//        ImageView g_plus = findViewById(R.id.g_plus);
//
//
//        FacebookSdk.sdkInitialize(this);
//        callbackManager = CallbackManager.Factory.create();
//
//        facebookLogin();
//
//
//        facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginManager.logInWithReadPermissions(
//                        LoginActivity.this,
//                        Arrays.asList(
//                                "email",
//                                "public_profile",
//                                "user_birthday"));
//
//            }
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        callbackManager.onActivityResult(
//                requestCode,
//                resultCode,
//                data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
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