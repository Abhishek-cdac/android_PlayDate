package com.playdate.app.ui.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.playdate.app.R;
import com.playdate.app.business.businessbio.BusinessBioActivity;
import com.playdate.app.business.businessphoto.BusinessUploadPhotoActivity;
import com.playdate.app.business.startdate.BusinessStartingDateActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.Interest;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.LoginUserDetails;
import com.playdate.app.model.RegisterResult;
import com.playdate.app.model.RegisterUser;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.ui.register.gender.GenderSelActivity;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.ui.register.otp.OTPActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.ui.register.username.UserNameActivity;
import com.playdate.app.ui.restaurant.RestaurantActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.customcamera.otalia.CameraActivity;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.DEVICE_TYPE;
import static com.playdate.app.util.session.SessionPref.LoginUserInterestsIDS;
import static com.playdate.app.util.session.SessionPref.LoginUserSourceType;
import static com.playdate.app.util.session.SessionPref.LoginVerified;

public class RegisterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText login_Password;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private ImageView iv_show_password;
    private GoogleSignInClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    private CommonClass clsCommon;
    private boolean isBusiness = false;

    private SessionPref pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = SessionPref.getInstance(this);
        clsCommon = CommonClass.getInstance();
        RegisterViewModel registerViewModel = new RegisterViewModel();
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = GoogleSignIn.getClient(this, gso);

        com.playdate.app.databinding.ActivityRegisterBinding binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.activity_register);
        binding.setLifecycleOwner(this);
        binding.setVMRegister(registerViewModel);
//        Button logout = binding.logout1;

        iv_show_password = findViewById(R.id.iv_show_password);
        login_Password = findViewById(R.id.login_Password);
        String userType = getIntent().getStringExtra("userType");

        if (userType.toLowerCase().equals("person")) {
            isBusiness = false;
        } else {
            isBusiness = true;
            binding.edtFullname.setHint("Business Name");
            SessionPref pref = SessionPref.getInstance(this);
            pref.saveBoolKeyVal(SessionPref.isBusiness, isBusiness);
        }

        registerViewModel.getFinish().observe(this, aBoolean -> finish());

        registerViewModel.getRegisterUser().observe(this, registerUser -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getFullname())) {
                if (isBusiness) {
                    clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter Business Name", "Ok");
                } else {
                    clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter Full Name", "Ok");
                }

            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getAddress())) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter Address", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPhoneNo())) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter Phone Number", "Ok");
            } else if ((registerUser).getPhoneNo().length() < 10) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter Proper Phone Number", "Ok");
            } else if (TextUtils.isEmpty(Objects.requireNonNull(registerUser).getPassword())) {
                clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", "Enter Password", "Ok");
            } else {

                callAPI(registerUser, userType);
            }

        });

        registerViewModel.fbClick().observe(this, register -> callToFB());


        iv_show_password.setOnClickListener(v -> {

            if (login_Password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                (iv_show_password).setImageResource(R.drawable.ic_visibility);

                //Show Password
                login_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                (iv_show_password).setImageResource(R.drawable.ic_visibility_off);

                //Hide Password
                login_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        });


        registerViewModel.getOnGoogleClick().observe(this, aBoolean -> callToGoogle());


    }

    private String getFCM() {
        String fcmID = pref.getStringVal(SessionPref.LoginUserFCMID);
        if (fcmID == null) {
            fcmID = "99999999";// no fcmid
        }
        return fcmID;
    }

    private String getDeviceID() {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    private void callAPI(RegisterUser registerUser, String userType) {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("fullName", registerUser.getFullname());
        hashMap.put("email", registerUser.getEmail());
        hashMap.put("address", registerUser.getAddress());
        hashMap.put("deviceType", DEVICE_TYPE);
        hashMap.put("deviceToken", getFCM());
        hashMap.put("phoneNo", registerUser.getPhoneNo());
        hashMap.put("password", registerUser.getPassword());
        hashMap.put("userType", userType);
        hashMap.put("inviteCode", "");
        hashMap.put("deviceID", getDeviceID());
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
                                user.getPaymentMode(),
                                user.getInviteLink(),
                                user.getUserType().toLowerCase().equals("business")
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
        startActivity(mIntent);
        finish();
    }

    private void callToGoogle() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
        startActivityForResult(googleSignInClient.getSignInIntent(), 1);

    }

    private void callToFB() {
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

        if (task.isSuccessful()) {
            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (acct != null) {
                    String personName = acct.getDisplayName();
                    String personGivenName = acct.getGivenName();
                    //  String ServerAuthCode = acct.getIdToken();

                    String personEmail = acct.getEmail();
                    String personId = acct.getId();
                    Uri personPhoto = acct.getPhotoUrl();

                    Log.e("personEmail", "" + personEmail);
                    // Log.e("ServerAuthCode", "" + ServerAuthCode);
                    Log.e("personId", "" + personId);
                    Log.e("personPhoto", "" + personPhoto);
                    Log.e("personName", "" + personName);
                    pref.saveStringKeyVal(LoginUserSourceType,"Google");
                    callGmailSocialLoginAPI(personEmail, personId, "");
                }


            } catch (Exception e) {
                Log.e("EXCEPTION", e.toString());
            }
        } else {
//            bypass();// remove when no use
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }

    }

    private void bypass() {
        callGmailSocialLoginAPI("ajit.jadhav80868086@gmail.com", "0000000000", "");
    }

    private void callGmailSocialLoginAPI(String personEmail, String personId, String serverAuthCode) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("email", personEmail);
        hashMap.put("sourceSocialId", personId);
        hashMap.put("deviceToken", getFCM());
        hashMap.put("sourceType", "Google");
        hashMap.put("deviceID", getDeviceID());//Hardcode
        hashMap.put("deviceType", DEVICE_TYPE);
        hashMap.put("inviteCode", "");
        //   hashMap.put("deviceToken", "12345");//Hardc
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.sociallogin(hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        checkForTheLastActivity(response.body());
                    } else {
                        clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, "Something went wrong...Please try later!" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(RegisterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    LoginUserDetails user;
    private void checkForTheLastActivity(LoginResponse body) {
        try {
            LoginUserDetails user = body.getUserData();
            if (user.getUserType() == null) {

                String val = "Person";
                if (isBusiness) {
                    val = "Business";
                } else {
                    val = "Person";
                }
                this.user=user;
                callAPIUpdateUserType(val);

            } else {
                logicAfterUserType(user);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void logicAfterUserType(LoginUserDetails user) {
        isBusiness = user.getUserType().toLowerCase().equals("business");
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
                getData(user.getInterested()),
                user.getInterestedIn(),
                "",
                user.getSourceType(),
                user.getSourceSocialId(),
                user.getInviteCode(),
                user.getPaymentMode(),
                user.getInviteLink(),
                isBusiness
        );

        Intent mIntent;
        Context mContext = RegisterActivity.this;
        if (isBusiness) {
            if (user.getBirthDate() == null) {
                mIntent = new Intent(mContext, BusinessStartingDateActivity.class);
            } else if (user.getUsername() == null) {
                mIntent = new Intent(mContext, UserNameActivity.class);
            } else if (user.getPersonalBio() == null) {
                mIntent = new Intent(mContext, BusinessBioActivity.class);
            } else if (user.getProfilePicPath() == null) {
                mIntent = new Intent(mContext, UploadProfileActivity.class);
            } else if (user.getBusinessImage() == null) {
                mIntent = new Intent(mContext, BusinessUploadPhotoActivity.class);
            } else {
                mIntent = new Intent(mContext, DashboardActivity.class);
            }
        } else {
            if (user.getStatus().equals("false")) {
                mIntent = new Intent(mContext, OTPActivity.class);
                mIntent.putExtra("Phone", user.getPhoneNo());
                mIntent.putExtra("resendOTP", true);
                mIntent.putExtra("Forgot", false);

            } else if (user.getBirthDate() == null) {
                mIntent = new Intent(mContext, AgeVerifiationActivity.class);

            } else if (user.getGender() == null) {
                mIntent = new Intent(mContext, GenderSelActivity.class);
            } else if (user.getRelationship() == null) {
                mIntent = new Intent(mContext, RelationActivity.class);
            } else if (user.getUsername() == null) {
                mIntent = new Intent(mContext, UserNameActivity.class);
            } else if (user.getPersonalBio() == null) {
                mIntent = new Intent(mContext, BioActivity.class);
            } else if (user.getProfilePicPath() == null) {
                mIntent = new Intent(mContext, UploadProfileActivity.class);
            } else if (user.getInterested() == null) {
                mIntent = new Intent(mContext, InterestActivity.class);
            } else if (user.getRestaurants() == null) {
                mIntent = new Intent(mContext, RestaurantActivity.class);
            } else if (user.getProfileVideoPath() == null) {
                mIntent = new Intent(mContext, CameraActivity.class);
            } else {
                mIntent = new Intent(mContext, DashboardActivity.class);
                SessionPref.getInstance(mContext).saveBoolKeyVal(LoginVerified, true);
            }
        }
        startActivity(mIntent);
        finish();
    }

    private String getData(ArrayList<Interest> interested) {
        String interest = "";
        String interestIDs = "";
        for (int i = 0; i < interested.size(); i++) {
            if (interest.isEmpty()) {
                interest = interested.get(i).getName();
                interestIDs = interested.get(i).get_id();
            } else {
                interest = interest + "," + interested.get(i).getName();
                interestIDs = interestIDs + "," + interested.get(i).get_id();
            }
        }

        SessionPref.getInstance(this).saveStringKeyVal(LoginUserInterestsIDS, interestIDs);

        return interest;
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
                                String accessToken = loginResult.getAccessToken().getToken();
                                GraphRequest request = GraphRequest.newMeRequest(

                                        loginResult.getAccessToken(),

                                        (object, response) -> {

                                            if (object != null) {
                                                try {
                                                    String name = object.getString("name");
                                                    String email = object.getString("email");
                                                    String fbUserID = object.getString("id");
                                                    //  String birthday = object.getString("birthday");
                                                    Log.d("Name of user ", name);
                                                    Log.d("email of user ", email);
                                                    Log.d("id of user ", fbUserID);
                                                    //Log.d("birthday of user ", birthday);
                                                    pref.saveStringKeyVal(LoginUserSourceType, "Facebook");
                                                    callSocialLoginAPI(email, fbUserID, accessToken);

                                                    disconnectFromFacebook();

                                                    // do action after Facebook login success
                                                    // or call your API
                                                } catch (JSONException | NullPointerException e) {
                                                    e.printStackTrace();
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

    private void callSocialLoginAPI(String email, String fbUserID, String token) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("sourceSocialId", fbUserID);
        hashMap.put("deviceToken", token);
        hashMap.put("sourceType", "Facebook");
        hashMap.put("deviceID", getDeviceID());//Hardcode
        hashMap.put("deviceType", DEVICE_TYPE);
        hashMap.put("inviteCode", "");

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.sociallogin(hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        // startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        //finish();

                        checkForTheLastActivity(response.body());


                    } else {
                        clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(RegisterActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(RegisterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                graphResponse -> LoginManager.getInstance().logOut())
                .executeAsync();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void callAPIUpdateUserType(String userType) {

        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        hashMap.put("userType", userType);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();


        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveBoolKeyVal(SessionPref.isBusiness, userType.equals("Business"));
                        if (null != user) {
                            user.setUserType(userType);
                            logicAfterUserType(user);
                        }

                    } else {

                    }
                } else {


                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                //Toast.makeText(RelationActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
