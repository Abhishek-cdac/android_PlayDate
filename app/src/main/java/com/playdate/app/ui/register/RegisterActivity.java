package com.playdate.app.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.playdate.app.databinding.ActivityRegisterBinding;
import com.playdate.app.model.LoginUserDetails;
import com.playdate.app.model.RegisterResult;
import com.playdate.app.model.RegisterUser;
import com.playdate.app.ui.login.LoginActivity;
import com.playdate.app.ui.register.otp.OTPActivity;
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

public class RegisterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private RegisterViewModel registerViewModel;

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private GoogleApiClient googleApiClient;
    private ActivityRegisterBinding binding;
    private static final int RC_SIGN_IN = 1;
    CommonClass clsCommon;
    Intent mIntent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getIntent();
        clsCommon = CommonClass.getInstance();
        registerViewModel = new RegisterViewModel();
        registerViewModel.init();
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        binding.setLifecycleOwner(this);
        binding.setVMRegister(registerViewModel);
        Button logout = binding.logout1;


        registerViewModel.getFinish().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });

        registerViewModel.getRegisterUser().observe(this, registerUser -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getFullname())) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter full name", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getAddress())) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter address", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPhoneNo())) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter phone number", "Ok");
            } else if ((registerUser).getPhoneNo().length() < 10) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter proper phone number", "Ok");
            }
//                else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getEmail())) {
//                    clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter email id", "Ok");
//                } else if (!registerUser.isEmailValid()) {
//                    clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter valid email id", "Ok");
//                }
            else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPassword())) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter password", "Ok");
            } else {
                callAPI(registerUser);
            }

        });

        registerViewModel.fbClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean register) {

                calltoFB();

            }
        });

        registerViewModel.getOnGoogleClick().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                calltoGoogle();
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

    private void callAPI(RegisterUser registerUser) {

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("fullName", registerUser.getFullname());
        hashMap.put("email", registerUser.getEmail());
        hashMap.put("address", registerUser.getAddress());
        hashMap.put("deviceType", DEVICE_TYPE);
        hashMap.put("deviceToken", "12345695");
        hashMap.put("phoneNo", registerUser.getPhoneNo());
        hashMap.put("password", registerUser.getPassword());
        hashMap.put("userType", mIntent.getStringExtra("userType"));
        hashMap.put("inviteCode", "");

        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        hashMap.put("deviceID", android_id);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<RegisterResult> call = service.registerUser(hashMap);
        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        LoginUserDetails user = response.body().getData();
                        SessionPref.getInstance(RegisterActivity.this).saveLoginUser(
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
                                user.getProfilePicPath(),
                                user.getProfileVideoPath(),
                                user.getRelationship(),
                                user.getPersonalBio(),
                                "",
                                user.getInterestedIn(),
                                "",
                                user.getSourceType(),
                                user.getSourceSocialId(),
                                user.getInviteCode(),
                                user.getPaymentMode()
                                );

                        nextPage(registerUser);
                    } else {
                        clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Something went wrong...Please try later!", "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", jObjError.getJSONArray("data").getJSONObject(0).getString("msg"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(RegisterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nextPage(RegisterUser registerUser) {
        //next Page
        Intent mIntent = new Intent(RegisterActivity.this, OTPActivity.class);
        mIntent.putExtra("Name", registerUser.getFullname());
        mIntent.putExtra("Phone", registerUser.getPhoneNo());
        mIntent.putExtra("Address", registerUser.getAddress());
        mIntent.putExtra("Email", registerUser.getEmail());
        mIntent.putExtra("Password", registerUser.getPassword());
        mIntent.putExtra("Forgot", false);
//        registerViewModel.clearFileds();
        startActivity(mIntent);
        finish();
    }

    private void calltoGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);

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
