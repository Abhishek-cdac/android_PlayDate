package com.playdate.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityLoginBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.LoginUser;
import com.playdate.app.model.LoginUserDetails;
import com.playdate.app.ui.forgot_password.ForgotPassword;
import com.playdate.app.ui.register.RegisterActivity;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.DEVICE_TYPE;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private GoogleApiClient googleApiClient;

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;
    private static final int RC_SIGN_IN = 1;
    CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        loginViewModel = new LoginViewModel();
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);
        Button logout = binding.logout;

        loginViewModel.getUser().observe(this, loginUser -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
//                binding.loginEmail.setError("Enter an E-Mail Address");
                binding.loginEmail.requestFocus();
                new CommonClass().showDialogMsg(LoginActivity.this, "PlayDate", "Enter a username", "Ok");

            }
//            else if (!loginUser.isEmailValid()) {
////                binding.loginEmail.setError("Enter a Valid E-mail Address");
//                binding.loginEmail.requestFocus();
//                new CommonClass().showDialogMsg(LoginActivity.this,"PlayDate","Enter valid email address","Ok");
//            }
            else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
//                binding.loginPassword.setError("Enter a Password");
                binding.loginPassword.requestFocus();
                new CommonClass().showDialogMsg(LoginActivity.this, "PlayDate", "Enter password", "Ok");
            } else if (!loginUser.isPasswordLengthGreaterThan5()) {
//                binding.loginPassword.setError("Enter at least 6 Digit password");
                new CommonClass().showDialogMsg(LoginActivity.this, "PlayDate", "Enter at least 6 digit password", "Ok");
                binding.loginPassword.requestFocus();
            } else {

                callLoginAPI(loginUser);


            }

        });

        loginViewModel.getRegisterUser().observe(this, register -> {

            if (register) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            } else {
                // call API
            }

        });

        loginViewModel.getForgotClick().observe(this, forgot -> {
            if (forgot) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            } else {
                /////   
            }
        });

        loginViewModel.fbClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean register) {

                calltoFB();// or you can put this in viewmodel or simply complete

            }
        });


        loginViewModel.GoogleClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean register) {

                calltoGoogle();// or you can put this in viewmodel or simply complete

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), "LogOut", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Session not close", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    private void callLoginAPI(LoginUser loginUser) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("email", loginUser.getStrEmailAddress());
        hashMap.put("password", loginUser.getStrPassword());
        hashMap.put("deviceID", "12345");//Hardcode
        hashMap.put("deviceType", DEVICE_TYPE);
        hashMap.put("deviceToken", "12345");//Hardc
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.login(hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        // startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        //finish();
                        checkforTheLastActivity(response.body());

                    } else {
                        clsCommon.showDialogMsg(LoginActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(LoginActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkforTheLastActivity(LoginResponse body) {
        LoginUserDetails user = body.getUserData();
        SessionPref.getInstance(LoginActivity.this).saveLoginUser(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getPhoneNo(),
                user.getStatus(),
                user.getToken(),
                user.getGender(),
                user.getBirthDate(),
                user.getAge(),
                user.getProfilePic(),
                user.getProfileVideo(),
                user.getRelationship(),
                user.getPersonalBio(),
                user.getInterested(),
                user.getRestaurants());


        if (user.getBirthDate() == null) {
            startActivity(new Intent(LoginActivity.this, AgeVerifiationActivity.class));
            finish();
        } else if (user.getGender() == null) {

        } else if (user.getRelationship() == null) {

        } else if (user.getPersonalBio() == null) {

        } else if (user.getProfilePic() == null) {

        } else if (user.getInterested() == null) {

        } else if (user.getRestaurants() == null) {

        }


//        Toast.makeText(this, user.getFullName(), Toast.LENGTH_SHORT).show();

    }


    private void calltoGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);


    }

    private void calltoFB() {
        FacebookSdk.sdkInitialize(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();
        facebookLogin();

        loginManager.logInWithReadPermissions(
                LoginActivity.this,
                Arrays.asList(
                        "email",
                        "public_profile",
                        "user_birthday"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(
                requestCode,
                resultCode,
                data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Log.d("Email ", account.getEmail());
            Log.d("Name ", account.getDisplayName());
            Log.d("Photo ", account.getPhotoUrl().toString());


        } catch (ApiException e) {
            Log.d("EXCEPTION", "signInResult:failed code=" + e.getStatusCode());
        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}