package com.playdate.app.ui.chat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.UserInfo;
import com.playdate.app.model.chat_models.ChatFile;
import com.playdate.app.model.chat_models.ChatFileUpload;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.model.chat_models.ChatMsgResp;
import com.playdate.app.model.chat_models.MediaInfo;
import com.playdate.app.model.chat_models.PollingQuestion;
import com.playdate.app.model.chat_models.PollingResponse;
import com.playdate.app.service.GpsTracker;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.date.DateBaseActivity;
import com.playdate.app.ui.date.fragments.FragSelectDate;
import com.playdate.app.util.MyApplication;
import com.playdate.app.util.common.AudioRecordProgressDialog;
import com.playdate.app.util.common.BaseActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.session.SessionPref;
import com.playdate.app.videocall.activities.CallActivity;
import com.playdate.app.videocall.activities.PermissionsActivity;
import com.playdate.app.videocall.services.LoginService;
import com.playdate.app.videocall.util.QBResRequestExecutor;
import com.playdate.app.videocall.utils.CollectionsUtils;
import com.playdate.app.videocall.utils.Consts;
import com.playdate.app.videocall.utils.PermissionsChecker;
import com.playdate.app.videocall.utils.QBEntityCallbackImpl;
import com.playdate.app.videocall.utils.SharedPrefsHelper;
import com.playdate.app.videocall.utils.ToastUtils;
import com.playdate.app.videocall.utils.WebRtcSessionManager;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.GenericQueryRule;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.socket.emitter.Emitter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.ALL_PERMISSIONS_RESULT;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.PICK_PHOTO_FOR_AVATAR;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.REQUEST_LOCATION_CODE;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.REQUEST_TAKE_GALLERY_VIDEO;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.TAKE_PHOTO_CODE;

public class ChatMainActivity extends BaseActivity implements onSmileyChangeListener, onImageSelectListener, View.OnClickListener {

    private RecyclerView rv_chat;
    private RecyclerView rv_smileys;
    private ChatAdapter adapter;
    private ImageView iv_mic;
    private EditText et_msg;
    private String sender_photo;
    private String sender_name;
    private String chatId;
    private String userIDTo;
    private ArrayList<Integer> lstSmiley;
    private NestedScrollView scrollview;
    private SessionPref pref;
    private boolean isVisible = false;
    private MediaRecorder mRecorder;
    private String mFileName = null;
    private static final String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private AudioRecordProgressDialog apd;
    private String UserID = "";
    private double lattitude;
    private double longitude;
    private Onclick itemClick;
    private CommonClass commonClass;


    private final int REQUEST_AUDIO_PERMISSION_CODE = 1;


    private int PageNumber = 1;
    private JSONObject objNotTyping;

    private final long delay = 1000; // 1 seconds after user stops typing
    private long last_text_edit = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private boolean isMoreData = true;

    // Video Calling
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected QBResRequestExecutor requestExecutor;
    protected SharedPrefsHelper sharedPrefsHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        commonClass = new CommonClass();
        pref = SessionPref.getInstance(ChatMainActivity.this);
        requestExecutor = MyApplication.getInstance().getQbResRequestExecutor();
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        UserID = pref.getStringVal(SessionPref.LoginUserID);

        sharedPrefsHelper = SharedPrefsHelper.getInstance();
        scrollview = findViewById(R.id.scrollview);
//        rl_chat = findViewById(R.id.rl_chat);
        rv_smileys = findViewById(R.id.rv_smileys);
        iv_mic = findViewById(R.id.iv_mic);
        ImageView iv_circle = findViewById(R.id.iv_circle);
        ImageView arrow_back = findViewById(R.id.arrow_back);
        et_msg = findViewById(R.id.et_chat);
        ImageView iv_send = findViewById(R.id.iv_send);
        ImageView iv_camera = findViewById(R.id.iv_camera);
        ImageView iv_video = findViewById(R.id.iv_video);
        rv_chat = findViewById(R.id.rv_chat);
        ImageView profile_image = findViewById(R.id.profile_image);
        TextView chat_name = findViewById(R.id.chat_name);
        ImageView iv_smiley = findViewById(R.id.iv_smiley);
        ImageView iv_delete_msg = findViewById(R.id.iv_delete_msg);
        ImageView iv_video_call = findViewById(R.id.iv_video_call);

        lstSmiley = new ArrayList<>();


        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        rv_chat.setLayoutManager(manager);


        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rv_smileys.setLayoutManager(manager1);


        try {
            Intent mIntent = getIntent();
            if (null != mIntent) {
                sender_name = mIntent.getStringExtra("Name");
                sender_photo = mIntent.getStringExtra("Image");
                userIDTo = mIntent.getStringExtra("UserID");
                chatId = mIntent.getStringExtra("chatId");
            }

            Picasso.get().load(sender_photo).placeholder(R.drawable.cupertino_activity_indicator).into(profile_image);
            chat_name.setText(sender_name);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        CreateSmilyList();
        getEmoticon();
//        addQuestions();

        ChatSmileyAdapter chatSmileyAdapter = new ChatSmileyAdapter(lstSmiley, this);
        rv_smileys.setAdapter(chatSmileyAdapter);

        chat_name.setOnClickListener(this);
        arrow_back.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        iv_send.setOnClickListener(this);
        iv_video.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        iv_mic.setOnClickListener(this);
        iv_circle.setOnClickListener(this);
        iv_smiley.setOnClickListener(this);
        iv_video_call.setOnClickListener(this);

        createRoom();
        listen();
        readMsgEmit();

        JSONObject objTyping = getJOBTyping(true);
        objNotTyping = getJOBTyping(false);

        et_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
                mSocket.emit("typing", objTyping);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {
                    mSocket.emit("typing", objNotTyping);
                }
            }
        });


        scrollview.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            if (scrollY == 0) {
//                callAPI();

            }

        });


        //video call

        userForSave = createUserWithEnteredData();
        startSignUpNewUser(userForSave);
        checker = new PermissionsChecker(getApplicationContext());
        loadUsers();

        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {

            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String questionId, String optionId) {
                if (i == 10) {
                    sendResponse(questionId, optionId);
                }

            }

            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };
        callAPI();
    }

    private void listen() {
        try {
            mSocket.on("chat_message_room", onNewMessage);
            mSocket.on("typing", onTyping);
            mSocket.on("chat_room", ChatRoomCreated);
            mSocket.on("chat_question_answer", ChatQuestioAnswer);
            mSocket.on("Data", OnError);
//            Log.d("****mSocket", mSocket.id());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readMsgEmit() {
        try {
            JSONObject readMsg = new JSONObject();

            readMsg.put("userId", pref.getStringVal(SessionPref.LoginUserID));
            readMsg.put("chatId", chatId);

            mSocket.emit("chat_message_read", readMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                if (null != objNotTyping)
                    mSocket.emit("typing", objNotTyping);
            }
        }
    };


    private JSONObject getJOBTyping(boolean typing) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", pref.getStringVal(SessionPref.LoginUserID));
            jsonObject.put("typing", typing);
            jsonObject.put("chatId", chatId);
            jsonObject.put("username", pref.getStringVal(SessionPref.LoginUserusername));
            jsonObject.put("profilePic", pref.getStringVal(SessionPref.LoginUserprofilePic));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private ArrayList<ChatMessage> lstChat;
    private ArrayList<PollingQuestion> lstPollingQuestion;

    private void callAPI() {

        if (!isMoreData) {
            return;
        }
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("limit", "10");
        hashMap.put("pageNo", "" + PageNumber);
        hashMap.put("chatId", chatId);
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        Call<ChatMsgResp> call = service.getChatMessage("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<ChatMsgResp>() {
            @Override
            public void onResponse(Call<ChatMsgResp> call, Response<ChatMsgResp> response) {
//                pd.cancel();

                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        ChatMsgResp resp = response.body();
                        if (null == lstChat) {
                            lstChat = new ArrayList<>();
//                            lstPollingQuestion = new ArrayList<>();

                        }
                        if (PageNumber == 1) {
                            lstChat = resp.getLstChatMsg();
                            lstPollingQuestion = resp.getLstPollingQuestion();
//                            lst_chatResponse.add(new ChatTotalResponse(lstChat, lstPollingQuestion));


                            if (null != lstPollingQuestion) {

                                for (int i = 0; i < lstPollingQuestion.size(); i++) {
                                    Log.d("pollingDate", lstPollingQuestion.get(i).getEntryDate());

                                    int j = 0;
                                    boolean found = false;
                                    int foundIndex = 0;
                                    String d1 = lstPollingQuestion.get(i).getEntryDate().replace("T", " ");
                                    for (; j < lstChat.size(); j++) {
                                        if (null != lstChat.get(j).getEntryDate()) {

                                            try {

                                                String d2 = lstChat.get(j).getEntryDate().replace("T", " ");
                                                Date pollDate = sdf.parse(d1);
                                                Date MsgDate = sdf.parse(d2);
                                                long diff = pollDate.getTime() - MsgDate.getTime();
                                                if (diff < 0) {// minus diff
                                                    System.out.println("Date1 is after Date2");
                                                } else { // plus diff
                                                    found = true;
                                                    foundIndex = j;
                                                    j = lstChat.size();

                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                    if (found) {
                                        ChatMessage msg = new ChatMessage();
                                        msg.setPolling(lstPollingQuestion.get(i));
                                        msg.setType("polling");
                                        lstChat.add(foundIndex, msg);
                                    } else {
                                        ChatMessage msg = new ChatMessage();
                                        msg.setPolling(lstPollingQuestion.get(i));
                                        msg.setType("polling");
                                        lstChat.add(j - 1, msg);
                                    }


                                }

                            }// run and check

                            adapter = new ChatAdapter(lstChat, ChatMainActivity.this, itemClick);
                            rv_chat.setAdapter(adapter);
                            scrollTOEnd();
                        } else {
                            lstChat.addAll(resp.getLstChatMsg());
                            adapter.notifyDataSetChanged();


                        }
                        PageNumber = PageNumber + 1;


                    }
                } else {
                    isMoreData = false;
                }


            }

            @Override
            public void onFailure(Call<ChatMsgResp> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
                isMoreData = false;
            }
        });
    }


    void scrollTOEnd() {
        scrollview.post(() -> scrollview.fullScroll(View.FOCUS_DOWN));
    }

    Emitter.Listener onNewMessage = args -> runOnUiThread(() -> {

        try {
            JSONObject data = (JSONObject) args[0];
            Log.d("****OnNewMsg", data.toString());
            String userIDFromIP = data.getString("userId");
            if (userIDFromIP.equals(userIDTo) || userIDFromIP.equals(UserID)) {


                if (!UserID.equals(userIDFromIP)) {
                    if (lstChat.get(0).getType().equals("typing")) {
                        lstChat.remove(0);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    readMsgEmit();
                }
                ArrayList<MediaInfo> lstMedia = new ArrayList<>();
                try {

                    MediaInfo mediaInfo = new MediaInfo();
                    mediaInfo.setMediaType(data.getString("mediaType"));
                    mediaInfo.setMediaId(data.getString("mediaId"));
                    mediaInfo.setMediaFullPath(data.getString("mediaFullPath"));
                    mediaInfo.setMediaThumbName(data.getString("mediaFullPathThumb"));
                    lstMedia.add(mediaInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UserInfo userInfo = new UserInfo();
                ArrayList<UserInfo> info = new ArrayList<>();
                info.add(userInfo);
                userInfo.setProfilePicPath(data.getString("profilePic"));
                ChatMessage chat = new ChatMessage();
                chat.setType(data.getString("messageType"));
                chat.setUserName(data.getString("username"));
                chat.setUserID(userIDFromIP);
                chat.setMessage(data.getString("message"));
                chat.setLattitude(data.getString("lat"));
                chat.setLattitude(data.getString("long"));
                chat.setUserInfo(info);
                chat.setMediaInfo(lstMedia);

                adapter.addToListText(chat);
                scrollTOEnd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    Emitter.Listener onTyping = args -> runOnUiThread(() -> {

        try {
            JSONObject data = (JSONObject) args[0];
            Log.d("****typing", data.toString());
            String userIDFromIP = data.getString("userId");
            if (userIDFromIP.equals(userIDTo) || userIDFromIP.equals(UserID)) {
//                if (!UserID.equals(userIDFromIP)) {
                if (!lstChat.get(0).getType().equals("typing")) {

                    ArrayList<MediaInfo> lstMedia = new ArrayList<>();
                    lstMedia.get(0).setMediaType("typing");
                    UserInfo userInfo = new UserInfo();
                    ArrayList<UserInfo> info = new ArrayList<>();

                    userInfo.setProfilePicPath(data.getString("profilePic"));
                    info.add(userInfo);
                    ChatMessage chat = new ChatMessage();
                    chat.setType("typing");
                    chat.setUserName(data.getString("username"));
                    chat.setUserID(userIDFromIP);
                    chat.setMessage("");
                    chat.setLattitude("0");
                    chat.setLattitude("");
                    chat.setUserInfo(info);
                    chat.setMediaInfo(lstMedia);

                    adapter.addToListText(chat);
                    scrollTOEnd();


                }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    Emitter.Listener ChatRoomCreated = args -> runOnUiThread(() -> {

        try {
            JSONObject data = (JSONObject) args[0];
            Log.d("****ChatRoomCreated", data.toString());
            chatId = data.getString("chatId");

        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    Emitter.Listener ChatQuestioAnswer = args -> runOnUiThread(() -> {
        try {
            JSONObject data = (JSONObject) args[0];
            Log.d("****ChatQuestionAnswer", data.toString());
            PollingResponse response = new PollingResponse();
            response.setIsRightAnswer(data.getString("isRightAnswer"));
            response.setPoints(data.getInt("points"));
            response.setAnswerOrder(data.getInt("answerOrder"));

            Log.d("RightAnswer", "isRightAnswer: " + data.getString("isRightAnswer"));

            new WinnerActivity(ChatMainActivity.this, data.get("answerOrder").toString(), data.get("points").toString()).show();


        } catch (Exception e) {

        }
    });

    Emitter.Listener OnError = args -> runOnUiThread(() -> {
        try {
            JSONObject data = (JSONObject) args[0];
            Log.d("****OnError", data.toString());
            if(data.toString().contains("already answered")){
                commonClass.showDialogMsg(ChatMainActivity.this,  "PlayDate", data.getString("message"), "Ok");
            }else{

            }



        } catch (Exception e) {

        }
    });

    private void sendMessgae(String msg, String Type, String mediaID) {
        if (null != mSocket) {
            if (mSocket.connected()) {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("userId", pref.getStringVal(SessionPref.LoginUserID));
                    jsonObject.put("message", msg);
                    jsonObject.put("chatId", chatId);
                    jsonObject.put("username", pref.getStringVal(SessionPref.LoginUserusername));
                    jsonObject.put("profilePic", pref.getStringVal(SessionPref.LoginUserprofilePic));


                    jsonObject.put("mediaId", mediaID);
                    jsonObject.put("lat", lattitude);
                    jsonObject.put("long", longitude);
                    jsonObject.put("messageType", Type);


                    mSocket.emit("chat_message_room", jsonObject);
                } catch (Exception ignored) {
                    Toast.makeText(this, "Emit chat excp", Toast.LENGTH_SHORT).show();
                }
            }
            et_msg.setText("");
            et_msg.requestFocus();
        }
    }

    private void sendResponse(String questionId, String optionId) {

        if (null != mSocket) {
            if (mSocket.connected()) {

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("userId", pref.getStringVal(SessionPref.LoginUserID));
                    jsonObject.put("chatId", chatId);
                    jsonObject.put("username", pref.getStringVal(SessionPref.LoginUserusername));
                    jsonObject.put("profilePic", pref.getStringVal(SessionPref.LoginUserprofilePic));

                    jsonObject.put("questionId", questionId);
                    jsonObject.put("optionId", optionId);

                    mSocket.emit("chat_question_answer", jsonObject);
                    Log.d("sendResponse", "sendResponse: ");

                } catch (Exception ignored) {
                    Toast.makeText(this, "Emit ans excp", Toast.LENGTH_SHORT).show();

                }

            }
        }
    }

    private void startRecording() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_AUDIO_PERMISSION_CODE);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/AudioRecording.3gp";

        String uuid = UUID.randomUUID().toString();
        mFileName = this.getExternalCacheDir().getAbsolutePath() + "/" + uuid + ".mp3";
        Log.d("FILENAME...", mFileName);
        iv_mic.setEnabled(true);
        apd = AudioRecordProgressDialog.getInstance(this);


        apd.show();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR WHILE RECORDING ", e.toString());
        }
        mRecorder.start();
        stopRecordingAfter();
    }

    private void stopRecordingAfter() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mRecorder.stop();
                apd.cancel();
                Log.d("Recording STopped", "Recording Stop");
                Toast.makeText(getApplicationContext(), "Recording Stop", Toast.LENGTH_SHORT).show();
                addToListAudio(mFileName);

//                adapter.addToListAudio(mFileName);
                scrollTOEnd();


            }
        }, 3000);
    }


    Bitmap bitmap = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == PICK_PHOTO_FOR_AVATAR || requestCode == TAKE_PHOTO_CODE) {


                    if (data.getData() == null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                    } else {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != bitmap) {
                        Log.d("BITMAP VALUE", bitmap.toString());
//                      sheet.dismiss();
//                      Drawable d = new BitmapDrawable(getResources(), bitmap);
                        addToListImage(bitmap, false);
                        scrollTOEnd();
                    }
                } else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                    if (data != null) {
                        Uri contentURI = data.getData();
                        String selectedVideoPath = getPath(contentURI);
                        addToListVideo(selectedVideoPath);
                        scrollTOEnd();


                    } else {
                        Log.d("FAILED", "FAIELD TO GET DATA");
                        Toast.makeText(this, "FAIELD TO GET DATA", Toast.LENGTH_SHORT).show();
                    }

                }

            } else if (requestCode == REQUEST_LOCATION_CODE) {
                if (data.getBooleanExtra("locationImg", false)) {
                    sharelocation();
                }
            } else {
                Log.d("Failed", "Failed to load");
                //  Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e + " ERROR EXCEPTION IN RESULT", Toast.LENGTH_SHORT).show();
        }


    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    File audioFile = null;

    private void addToListAudio(String mFileName) {
        audioFile = new File(mFileName);
        SessionPref pref = SessionPref.getInstance(this);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("mediaFeed", audioFile.getName(), RequestBody.create(MediaType.parse("audio/*"), audioFile));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ChatFileUpload> call = service.addmediaAudio("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart);
        call.enqueue(new Callback<ChatFileUpload>() {
            @Override
            public void onResponse(Call<ChatFileUpload> call, Response<ChatFileUpload> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        ChatFile media = response.body().getChatFile();
                        sendMessgae("", "media", media.getMediaId());
                        Log.d("AUDIO ADDED", "ADded audio");

                    } else {
                        Log.e("Status code 0", "onResponse" + response.body().getMessage());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ChatFileUpload> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    File currentFile = null;

    private void addToListVideo(String videoPath) {
        currentFile = new File(videoPath);
        SessionPref pref = SessionPref.getInstance(this);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("mediaFeed", currentFile.getName(), RequestBody.create(MediaType.parse("video/mp4"), currentFile));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ChatFileUpload> call = service.addmediaVideo("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart);
        call.enqueue(new Callback<ChatFileUpload>() {
            @Override
            public void onResponse(Call<ChatFileUpload> call, Response<ChatFileUpload> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        ChatFile media = response.body().getChatFile();
                        sendMessgae("", "media", media.getMediaId());

                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ChatFileUpload> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void addToListImage(Bitmap bitmap, boolean isLocation) {
        File f = new File(getCacheDir(), "chat.png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SessionPref pref = SessionPref.getInstance(this);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("mediaFeed", f.getName(), RequestBody.create(MediaType.parse("image/png"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ChatFileUpload> call = service.addMediaImage("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart);
        call.enqueue(new Callback<ChatFileUpload>() {
            @Override
            public void onResponse(Call<ChatFileUpload> call, Response<ChatFileUpload> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        ChatFile media = response.body().getChatFile();
                        if (isLocation) {
                            sendMessgae("", "location", media.getMediaId());
                        } else {
                            sendMessgae("", "media", media.getMediaId());
                        }
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ChatFileUpload> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
            boolean audioRecordingPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }

//        if (!audioRecordingPermissionGranted) {
////            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//        }
    }
//
//    public boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
//                WRITE_EXTERNAL_STORAGE);
//        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
//                RECORD_AUDIO);
//        return result == PackageManager.PERMISSION_GRANTED &&
//                result1 == PackageManager.PERMISSION_GRANTED;
//    }


    private void openCamera() {
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    private void getEmoticon() {
        lstSmiley = new CommonClass().getEmojiArr();
    }


    @Override
    public void onSmileyChange(int position) {
        int smiley = lstSmiley.get(position);
        sendMessgae("" + smiley, "emoji", null);

    }


    @Override
    public void onLocationSelect() {

//        sheet.dismiss();
        GpsTracker gpsTracker = new GpsTracker(this);
        if (gpsTracker.canGetLocation()) {
            this.lattitude = gpsTracker.getLatitude();
            this.longitude = gpsTracker.getLongitude();
            Log.e("latlong", "" + lattitude + "  " + longitude);
            if (String.valueOf(lattitude).equals("0.0") && String.valueOf(longitude).equals("0.0")) {
                Toast.makeText(this, "Wait a moment", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ChatMainActivity.this, MapActivity.class);
                intent.putExtra("lattitude", lattitude);
                intent.putExtra("longitude", longitude);
                startActivityForResult(intent, REQUEST_LOCATION_CODE);

//                startActivity(intent);
//                sendMessgae("", "location", "");
//                adapter.sendLcation(lattitude, longitude);
            }

        } else {
            gpsTracker.showSettingsAlert();
        }


        scrollTOEnd();

    }


    void createRoom() {
        if (null != mSocket) {
            if (mSocket.connected()) {

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userId", pref.getStringVal(SessionPref.LoginUserID));
                    jsonObject.put("token", pref.getStringVal(SessionPref.LoginUsertoken));
                    jsonObject.put("toUserId", userIDTo);
                    mSocket.emit("chat_room", jsonObject);

                } catch (Exception ignored) {

                }
            }
        }
    }

    @Override
    public void onCameraSelect() {

        String[] PERMISSIONS = {
                Manifest.permission.CAMERA,
        };
        ActivityCompat.requestPermissions(this,
                PERMISSIONS,
                ALL_PERMISSIONS_RESULT);
        openCamera();
    }

    @Override
    public void onGallerySelect() {
        String[] PERMISSIONS = {
                WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(this,
                PERMISSIONS,
                ALL_PERMISSIONS_RESULT);
        pickImage();
    }

    public void onMapClick(double lattitude, double longitude) {

        Uri gmmIntentUri = Uri.parse("geo:" + lattitude + "," + longitude + "?z=17");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Can't load Maps", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.iv_video_call) {
            if (checkOverlayPermissions()) {
                runNextScreen();
            }
        } else if (id == R.id.iv_circle) {
            new ChatBottomSheet("Extra", this).show(getSupportFragmentManager(), "ModalBottomSheet");
        } else if (id == R.id.arrow_back || id == R.id.profile_image) {
            finish();
        } else if (id == R.id.iv_send) {
            validateMsg();
        } else if (id == R.id.iv_video) {
            pickVideo();
        } else if (id == R.id.iv_camera) {
            new ChatBottomSheet("", this).show(getSupportFragmentManager(), "ModalBottomSheet");
        } else if (id == R.id.iv_mic) {
            startRecording();
        } else if (id == R.id.iv_smiley) {
            smileyCode();
        }

    }

    private void validateMsg() {
        String msg = et_msg.getText().toString();
        if (msg.isEmpty()) {
            Toast.makeText(ChatMainActivity.this, "Empty message can't send", Toast.LENGTH_SHORT).show();
        } else {
            sendMessgae(msg, "text", null);
            mSocket.emit("typing", objNotTyping);
        }
    }

    private void smileyCode() {
        if (isVisible) {
            rv_smileys.setVisibility(View.GONE);
            isVisible = false;
        } else {
            rv_smileys.setVisibility(View.VISIBLE);
            isVisible = true;
        }
    }

    public void pickVideo() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("video/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);

    }

    @Override
    protected void onDestroy() {
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }

        locationBitmap = null;
        super.onDestroy();

    }

//video Call

    private static final String OVERLAY_PERMISSION_CHECKED_KEY = "overlay_checked";
    private static final String MI_OVERLAY_PERMISSION_CHECKED_KEY = "mi_overlay_checked";

    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1764;

    private void buildOverlayPermissionAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Overlay Permission Required");
        builder.setIcon(R.drawable.ic_error_outline_gray_24dp);
        builder.setMessage("To receive calls in background - \nPlease Allow overlay permission in Android Settings");
        builder.setCancelable(false);

        builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtils.longToast("You might miss calls while your application in background");
                sharedPrefsHelper.save(OVERLAY_PERMISSION_CHECKED_KEY, true);
            }
        });

        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showAndroidOverlayPermissionsSettings();
            }
        });

        AlertDialog alertDialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog.create();
            alertDialog.show();
        }
    }

    private void showAndroidOverlayPermissionsSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(ChatMainActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        } else {
//            Log.d(TAG, "Application Already has Overlay Permission");
        }
    }

    private void buildMIUIOverlayPermissionAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Additional Overlay Permission Required");
        builder.setIcon(R.drawable.ic_error_outline_orange_24dp);
        builder.setMessage("Please make sure that all additional permissions granted");
        builder.setCancelable(false);

        builder.setNeutralButton("I'm sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPrefsHelper.save(MI_OVERLAY_PERMISSION_CHECKED_KEY, true);
                runNextScreen();
            }
        });

        builder.setPositiveButton("Mi Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showMiUiPermissionsSettings();
            }
        });

        AlertDialog alertDialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog.create();
            alertDialog.show();
        }
    }

    private void runNextScreen() {
        if (sharedPrefsHelper.hasQbUser()) {
            LoginService.start(ChatMainActivity.this, sharedPrefsHelper.getQbUser());
            startCall(true, Opponent);

        } else {
        }
    }

    private void showMiUiPermissionsSettings() {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", getPackageName());
        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
    }

    private boolean checkOverlayPermissions() {
//        Log.e(TAG, "Checking Permissions");
        boolean overlayChecked = sharedPrefsHelper.get(OVERLAY_PERMISSION_CHECKED_KEY, false);
        boolean miOverlayChecked = sharedPrefsHelper.get(MI_OVERLAY_PERMISSION_CHECKED_KEY, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this) && !overlayChecked) {
//                Log.e(TAG, "Android Overlay Permission NOT Granted");
                buildOverlayPermissionAlertDialog();
                return false;
            } else if (PermissionsChecker.isMiUi() && !miOverlayChecked) {
//                Log.e(TAG, "Xiaomi Device. Need additional Overlay Permissions");
                buildMIUIOverlayPermissionAlertDialog();
                return false;
            }
        }
//        Log.e(TAG, "All Overlay Permission Granted");
        sharedPrefsHelper.save(OVERLAY_PERMISSION_CHECKED_KEY, true);
        sharedPrefsHelper.save(MI_OVERLAY_PERMISSION_CHECKED_KEY, true);
        return true;
    }

    private QBUser userForSave;
    public QBUser result;


    private void startSignUpNewUser(final QBUser newUser) {
//        Log.d(TAG, "SignUp New User");
//        showProgressDialog(R.string.dlg_creating_new_user);
        requestExecutor.signUpNewUser(newUser, new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser result, Bundle params) {
//                        Log.d(TAG, "SignUp Successful");
//                        ChatMainActivity.result=result;
                        saveUserData(newUser);
                        loginToChat(result);
                    }

                    @Override
                    public void onError(QBResponseException e) {
//                        Log.d(TAG, "Error SignUp" + e.getMessage());
                        if (e.getHttpStatusCode() == Consts.ERR_LOGIN_ALREADY_TAKEN_HTTP_STATUS) {
                            signInCreatedUser(newUser);
                        } else {
//                            hideProgressDialog();
                            ToastUtils.longToast(R.string.sign_up_error);
                        }
                    }
                }
        );
    }

    private void signInCreatedUser(final QBUser qbUser) {
//        Log.d(TAG, "SignIn Started");
        requestExecutor.signInUser(qbUser, new QBEntityCallbackImpl<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle params) {
//                Log.d(TAG, "SignIn Successful");
                sharedPrefsHelper.saveQbUser(user);
                updateUserOnServer(user);
                loginToChat(user);

            }

            @Override
            public void onError(QBResponseException responseException) {
//                Log.d(TAG, "Error SignIn" + responseException.getMessage());
//                hideProgressDialog();
                ToastUtils.longToast(R.string.sign_in_error);
            }
        });
    }

    private void loginToChat(final QBUser qbUser) {
        qbUser.setPassword(MyApplication.USER_DEFAULT_PASSWORD);
        userForSave = qbUser;
        startLoginService(qbUser);
    }

    private void startLoginService(QBUser qbUser) {
        Intent tempIntent = new Intent(this, LoginService.class);
        PendingIntent pendingIntent = createPendingResult(Consts.EXTRA_LOGIN_RESULT_CODE, tempIntent, 0);
        LoginService.start(this, qbUser, pendingIntent);
    }

    private void updateUserOnServer(QBUser user) {
        user.setPassword(null);
        QBUsers.updateUser(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
//                hideProgressDialog();
//                OpponentsActivity.start(ChatMainActivity.this);
//                finish();
            }

            @Override
            public void onError(QBResponseException e) {
//                hideProgressDialog();
                ToastUtils.longToast(R.string.update_user_error);
            }
        });
    }

    private void saveUserData(QBUser qbUser) {
        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
        qbUser.setPassword(MyApplication.USER_DEFAULT_PASSWORD);
        sharedPrefsHelper.saveQbUser(qbUser);
    }

    private QBUser createUserWithEnteredData() {
        return createQBUserWithCurrentData(pref.getStringVal(SessionPref.LoginUserID), pref.getStringVal(SessionPref.LoginUserusername));
    }

    private QBUser createQBUserWithCurrentData(String userLogin, String userFullName) {
        QBUser qbUser = null;
        if (!TextUtils.isEmpty(userLogin) && !TextUtils.isEmpty(userFullName)) {
            qbUser = new QBUser();
            qbUser.setLogin(userLogin);
            qbUser.setFullName(userFullName);
            qbUser.setPassword(MyApplication.USER_DEFAULT_PASSWORD);
        }
        return qbUser;
    }


//    private static final String TAG = OpponentsActivity.class.getSimpleName();

    private static final int PER_PAGE_SIZE_100 = 100;
    private static final String ORDER_RULE = "order";
    private static final String ORDER_DESC_UPDATED = "desc date updated_at";
    private PermissionsChecker checker;

    QBUser Opponent;

    private void loadUsers() {
        ArrayList<GenericQueryRule> rules = new ArrayList<>();
        rules.add(new GenericQueryRule(ORDER_RULE, ORDER_DESC_UPDATED));

        QBPagedRequestBuilder qbPagedRequestBuilder = new QBPagedRequestBuilder();
        qbPagedRequestBuilder.setRules(rules);
        qbPagedRequestBuilder.setPerPage(PER_PAGE_SIZE_100);
        qbPagedRequestBuilder.setPage(0);

        QBUsers.getUserByLogin(userIDTo).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle bundle) {

                Opponent = user;
                if (checker.lacksPermissions(Consts.PERMISSIONS)) {
                    startPermissionsActivity(false);
                }

            }

            @Override
            public void onError(QBResponseException e) {
            }
        });
    }

    private void startPermissionsActivity(boolean checkOnlyAudio) {
        PermissionsActivity.startActivity(this, checkOnlyAudio, Consts.PERMISSIONS);
    }


    private void startCall(boolean isVideoCall, QBUser qbUsers) {
//        Log.d(TAG, "Starting Call");
        List<QBUser> selectedUsers = new ArrayList<>();
        selectedUsers.add(qbUsers);
        ArrayList<Integer> opponentsList = CollectionsUtils.getIdsSelectedOpponents(selectedUsers);
        QBRTCTypes.QBConferenceType conferenceType = isVideoCall
                ? QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
                : QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO;
//        Log.d(TAG, "conferenceType = " + conferenceType);

        QBRTCClient qbrtcClient = QBRTCClient.getInstance(getApplicationContext());
        QBRTCSession newQbRtcSession = qbrtcClient.createNewSessionWithOpponents(opponentsList, conferenceType);
        WebRtcSessionManager.getInstance(this).setCurrentSession(newQbRtcSession);

        // Make Users FullName Strings and ID's list for iOS VOIP push
        String newSessionID = newQbRtcSession.getSessionID();
        ArrayList<String> opponentsIDsList = new ArrayList<>();
        ArrayList<String> opponentsNamesList = new ArrayList<>();
        List<QBUser> usersInCall = selectedUsers;

        // the Caller in exactly first position is needed regarding to iOS 13 functionality
        usersInCall.add(0, qbUsers);

        for (QBUser user : usersInCall) {
            String userId = user.getId().toString();
            String userName = "";
            if (TextUtils.isEmpty(user.getFullName())) {
                userName = user.getLogin();
            } else {
                userName = user.getFullName();
            }

            opponentsIDsList.add(userId);
            opponentsNamesList.add(userName);
        }

//        String opponentsIDsString = TextUtils.join(",", opponentsIDsList);
//        String opponentNamesString = TextUtils.join(",", opponentsNamesList);

//        Log.d(TAG, "New Session with ID: " + newSessionID + "\n Users in Call: " + "\n" + opponentsIDsString + "\n" + opponentNamesString);
        CallActivity.start(this, false);
    }

    public static Bitmap locationBitmap = null;


    public void sharelocation() {
        if (locationBitmap != null) {
            Log.d("locatinBitmap", "locatinBitmap not null");
            addToListImage(locationBitmap, true);

        } else {
            Log.d("locatinBitmap", "locatinBitmap null");
        }
    }

    public void createDate() {
        Intent intent = new Intent(ChatMainActivity.this, DateBaseActivity.class);
        FragSelectDate.userIdTo = userIDTo;
        DateBaseActivity.fromChat = true;
        startActivity(intent);
    }
}


interface onSmileyChangeListener {
    void onSmileyChange(int position);

    void onLocationSelect();
}

interface onImageSelectListener {
    void onCameraSelect();

    void onGallerySelect();
}


