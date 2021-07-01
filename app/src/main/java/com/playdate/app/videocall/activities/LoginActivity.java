//package com.playdate.app.videocall.activities;
//
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.EditText;
//
//import com.quickblox.core.QBEntityCallback;
//import com.quickblox.core.exception.QBResponseException;
//import com.quickblox.core.helper.Utils;
//import com.playdate.app.util.MyApplication;
//import com.playdate.app.R;
//import com.playdate.app.videocall.services.LoginService;
//import com.playdate.app.videocall.utils.Consts;
//import com.playdate.app.videocall.utils.KeyboardUtils;
//import com.playdate.app.videocall.utils.QBEntityCallbackImpl;
//import com.playdate.app.videocall.utils.SharedPrefsHelper;
//import com.playdate.app.videocall.utils.ToastUtils;
//import com.playdate.app.videocall.utils.ValidationUtils;
//import com.quickblox.users.QBUsers;
//import com.quickblox.users.model.QBUser;
//
//import androidx.annotation.Nullable;
//
//public class LoginActivity extends BaseActivity {
//    private final String TAG = LoginActivity.class.getSimpleName();
//
//    private static final int MIN_LENGTH = 3;
//
//    private EditText userLoginEditText;
//    private EditText userFullNameEditText;
//
//    private QBUser userForSave;
//
//    public static void start(Context context) {
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_video_call);
//
//        initUI();
//    }
//
//    private void initUI() {
//        setActionBarTitle(R.string.title_login_activity);
//        userLoginEditText = findViewById(R.id.user_login);
//        userFullNameEditText = findViewById(R.id.user_full_name);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_login_user_done) {
//            if (ValidationUtils.isLoginValid(this, userLoginEditText) &&
//                    ValidationUtils.isFoolNameValid(this, userFullNameEditText)) {
//                hideKeyboard();
//                userForSave = createUserWithEnteredData();
//                startSignUpNewUser(userForSave);
//            }
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void hideKeyboard() {
//        KeyboardUtils.hideKeyboard(userLoginEditText);
//        KeyboardUtils.hideKeyboard(userFullNameEditText);
//    }
//
//    private void startSignUpNewUser(final QBUser newUser) {
//        Log.d(TAG, "SignUp New User");
//        showProgressDialog(R.string.dlg_creating_new_user);
//        requestExecutor.signUpNewUser(newUser, new QBEntityCallback<QBUser>() {
//                    @Override
//                    public void onSuccess(QBUser result, Bundle params) {
//                        Log.d(TAG, "SignUp Successful");
//                        saveUserData(newUser);
//                        loginToChat(result);
//                    }
//
//                    @Override
//                    public void onError(QBResponseException e) {
//                        Log.d(TAG, "Error SignUp" + e.getMessage());
//                        if (e.getHttpStatusCode() == Consts.ERR_LOGIN_ALREADY_TAKEN_HTTP_STATUS) {
//                            signInCreatedUser(newUser);
//                        } else {
//                            hideProgressDialog();
//                            ToastUtils.longToast(R.string.sign_up_error);
//                        }
//                    }
//                }
//        );
//    }
//
//    private void loginToChat(final QBUser qbUser) {
//        qbUser.setPassword(MyApplication.USER_DEFAULT_PASSWORD);
//        userForSave = qbUser;
//        startLoginService(qbUser);
//    }
//
//    private void saveUserData(QBUser qbUser) {
//        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
//        sharedPrefsHelper.saveQbUser(qbUser);
//    }
//
//    private QBUser createUserWithEnteredData() {
//        return createQBUserWithCurrentData(String.valueOf(userLoginEditText.getText()),
//                String.valueOf(userFullNameEditText.getText()));
//    }
//
//    private QBUser createQBUserWithCurrentData(String userLogin, String userFullName) {
//        QBUser qbUser = null;
//        if (!TextUtils.isEmpty(userLogin) && !TextUtils.isEmpty(userFullName)) {
//            qbUser = new QBUser();
//            qbUser.setLogin(userLogin);
//            qbUser.setFullName(userFullName);
//            qbUser.setPassword(MyApplication.USER_DEFAULT_PASSWORD);
//        }
//        return qbUser;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Consts.EXTRA_LOGIN_RESULT_CODE) {
//            hideProgressDialog();
//            boolean isLoginSuccess = data.getBooleanExtra(Consts.EXTRA_LOGIN_RESULT, false);
//            String errorMessage = data.getStringExtra(Consts.EXTRA_LOGIN_ERROR_MESSAGE);
//
//            if (isLoginSuccess) {
//                saveUserData(userForSave);
//                signInCreatedUser(userForSave);
//            } else {
//                ToastUtils.longToast(getString(R.string.login_chat_login_error) + errorMessage);
//                userLoginEditText.setText(userForSave.getLogin());
//                userFullNameEditText.setText(userForSave.getFullName());
//            }
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
//
//    private void signInCreatedUser(final QBUser qbUser) {
//        Log.d(TAG, "SignIn Started");
//        requestExecutor.signInUser(qbUser, new QBEntityCallbackImpl<QBUser>() {
//            @Override
//            public void onSuccess(QBUser user, Bundle params) {
//                Log.d(TAG, "SignIn Successful");
//                sharedPrefsHelper.saveQbUser(userForSave);
//                updateUserOnServer(qbUser);
//            }
//
//            @Override
//            public void onError(QBResponseException responseException) {
//                Log.d(TAG, "Error SignIn" + responseException.getMessage());
//                hideProgressDialog();
//                ToastUtils.longToast(R.string.sign_in_error);
//            }
//        });
//    }
//
//    private void updateUserOnServer(QBUser user) {
//        user.setPassword(null);
//        QBUsers.updateUser(user).performAsync(new QBEntityCallback<QBUser>() {
//            @Override
//            public void onSuccess(QBUser qbUser, Bundle bundle) {
//                hideProgressDialog();
//                OpponentsActivity.start(LoginActivity.this);
//                finish();
//            }
//
//            @Override
//            public void onError(QBResponseException e) {
//                hideProgressDialog();
//                ToastUtils.longToast(R.string.update_user_error);
//            }
//        });
//    }
//
//    private void startLoginService(QBUser qbUser) {
//        Intent tempIntent = new Intent(this, LoginService.class);
//        PendingIntent pendingIntent = createPendingResult(Consts.EXTRA_LOGIN_RESULT_CODE, tempIntent, 0);
//        LoginService.start(this, qbUser, pendingIntent);
//    }
//
//
//
//}