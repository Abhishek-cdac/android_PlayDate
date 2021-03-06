package com.playdate.app.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
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
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.playdate.app.R;
import com.playdate.app.business.businessbio.BusinessBioActivity;
import com.playdate.app.business.businessphoto.BusinessUploadPhotoActivity;
import com.playdate.app.business.startdate.BusinessStartingDateActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityLoginBinding;
import com.playdate.app.model.Interest;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.LoginUser;
import com.playdate.app.model.LoginUserDetails;
import com.playdate.app.service.FcmMessageService;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.dialogs.UserTypeDialog;
import com.playdate.app.ui.forgot_password.ForgotPasswordActivity;
import com.playdate.app.ui.register.age_verification.AgeVerificationActivity;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.ui.register.gender.GenderSelActivity;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.ui.register.otp.OTPActivity;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.ui.register.username.UserNameActivity;
import com.playdate.app.ui.register.usertype.UserTypeSelectionActivity;
import com.playdate.app.ui.restaurant.RestaurantActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.customcamera.otalia.CameraActivity;
import com.playdate.app.util.session.SessionPref;

import org.jetbrains.annotations.NotNull;
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
import timber.log.Timber;

import static com.playdate.app.data.api.RetrofitClientInstance.DEVICE_TYPE;
import static com.playdate.app.util.session.SessionPref.LoginUserFCMID;
import static com.playdate.app.util.session.SessionPref.LoginUserInterestsIDS;
import static com.playdate.app.util.session.SessionPref.LoginUserSourceType;
import static com.playdate.app.util.session.SessionPref.LoginVerified;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, OnUserTypeSelected {

    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private final String ServerAuthCode = null;
    private GoogleApiClient googleApiClient;
    private ImageView iv_show_password;
    private EditText login_Password;

    private ActivityLoginBinding binding;
    private static final int RC_SIGN_IN = 1;
    private CommonClass clsCommon;
    boolean isBusiness = false;
    private SessionPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = SessionPref.getInstance(this);
        clsCommon = CommonClass.getInstance();
        FacebookSdk.sdkInitialize(this);

        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//      boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
//        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        LoginViewModel loginViewModel = new LoginViewModel();
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);
        Button logout = binding.logout;
//        RelativeLayout rl_couple = findViewById(R.id.rl_couple);
        iv_show_password = findViewById(R.id.iv_show_password);
        login_Password = findViewById(R.id.login_Password);
//        rl_couple.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, DashboardBusiness.class)));

        binding.loginEmail.setFilters(new InputFilter[]{ignoreFirstWhiteSpace(), new InputFilter.LengthFilter(50)});

        loginViewModel.getUser().observe(this, loginUser -> {

            if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
//                binding.loginEmail.setError("Enter an E-Mail Address");
                binding.loginEmail.requestFocus();
                new CommonClass().showDialogMsg(LoginActivity.this, "PlayDate", "Enter Email/Mobile number", "Ok");

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
                startActivity(new Intent(LoginActivity.this, UserTypeSelectionActivity.class));
            } else {
                // call API
            }

        });

        loginViewModel.getForgotClick().observe(this, forgot -> {
            if (forgot) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        loginViewModel.fbClick().observe(this, register -> {

            calltoFB();// or you can put this in viewmodel or simply complete

        });


        loginViewModel.GoogleClick().observe(this, register -> {

            calltoGoogle();// or you can put this in viewmodel or simply complete

        });

        logout.setOnClickListener(v -> Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Toast.makeText(getApplicationContext(), "LogOut", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Session not close", Toast.LENGTH_LONG).show();
                        }
                    }
                }));

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


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.tag("******").w(task.getException(), "Fetching FCM registration token failed");
                        return;
                    }
                    String token = task.getResult();
                    Timber.d(token);
                    SessionPref pref1 = SessionPref.getInstance(LoginActivity.this);
                    pref1.saveStringKeyVal(LoginUserFCMID, token);
                });

        startService(new Intent(this, FcmMessageService.class));
    }

    private InputFilter ignoreFirstWhiteSpace() {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }
                }
                return null;
            }
        };
    }

    private void callLoginAPI(LoginUser loginUser) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("keyward", loginUser.getStrEmailAddress());
        hashMap.put("password", loginUser.getStrPassword());
        hashMap.put("deviceID", getDeviceID());
        hashMap.put("deviceType", DEVICE_TYPE);
        hashMap.put("deviceToken", getFCM());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.login(hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {


                        checkForTheLastActivity(response.body());

                    } else {
                        clsCommon.showDialogMsg(LoginActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(LoginActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
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


    private void checkForTheLastActivity(LoginResponse body) {
        try {
            LoginUserDetails user = body.getUserData();
            if (user.getUserType() == null) {

                showTypeDialog(user);

            } else {
                logicAfterUserType(user);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void logicAfterUserType(LoginUserDetails user) {
        isBusiness = user.getUserType().toLowerCase().equals("business");
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
                user.getProfilePicPath(),
                user.getBusinessImage(),
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
        Context mContext = LoginActivity.this;
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
                mIntent = new Intent(mContext, AgeVerificationActivity.class);

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

    public void nextCheck(int userType) {

        callAPIUpdateUserType(userType);
    }

    private void callAPIUpdateUserType(int userType) {

        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        hashMap.put("userType", userType == 0 ? "Person" : "Business");
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();


        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveBoolKeyVal(SessionPref.isBusiness, userType != 0);
                        if (null != user) {
                            user.setUserType(userType == 0 ? "Person" : "Business");
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

    private LoginUserDetails user;

    private void showTypeDialog(LoginUserDetails user) {
        new UserTypeDialog(this).show();
        this.user = user;
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
                        "email"
//                        ,
//                        "public_profile",
//                        "user_birthday"

// commented for now will remove later
                )
        );


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

        } else {

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
                    pref.saveStringKeyVal(LoginUserSourceType, "Google");
                    callGmailSocialLoginAPI(personEmail, personId, ServerAuthCode);
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
        callGmailSocialLoginAPI("ajit.jadhav80868086@gmail.com", "0000000000", ServerAuthCode);
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
                        clsCommon.showDialogMsg(LoginActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(LoginActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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


    public void facebookLogin() {
        loginManager = LoginManager.getInstance();
        loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        callbackManager = CallbackManager.Factory.create();

        loginManager.registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        String accessToken = loginResult.getAccessToken().getToken();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {

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
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString(
                                "fields",
                                "id, name, email, gender");
//                        parameters.putString(
//                                "fields",
//                                "id, name, email, gender, birthday");
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
                        clsCommon.showDialogMsg(LoginActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(LoginActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
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