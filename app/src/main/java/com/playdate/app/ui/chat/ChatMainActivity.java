package com.playdate.app.ui.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.service.GpsTracker;
import com.playdate.app.util.common.BaseActivity;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import io.socket.emitter.Emitter;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.ALL_PERMISSIONS_RESULT;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.PICK_PHOTO_FOR_AVATAR;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.REQUEST_TAKE_GALLERY_VIDEO;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.TAKE_PHOTO_CODE;

public class ChatMainActivity extends BaseActivity implements onSmileyChangeListener, onImageSelectListener, View.OnClickListener {

    private RecyclerView rv_chat;
    private RecyclerView rv_smileys;
    private ChatAdapter adapter;
    private ImageView iv_mic;
    private EditText et_msg;
    private ImageView iv_delete_msg;
    private ImageView video_cal;
    private String sender_photo;
    private String sender_name;
    private String chatId;
    private String userIDTo;
    private RelativeLayout rl_chat;
    private ArrayList<ChatMessage> chatMsgList;
    private ArrayList<Integer> lstSmiley;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private ChatBottomSheet sheet;
    private boolean isVisible = false;

    private MediaRecorder mRecorder;
    private MediaPlayer mediaPlayer;
    private String mFileName = null;
    private static final String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private String AudioSavePathInDevice = null;
    private TransparentProgressDialog pd;
    private double lattitude, longitude;
    private GpsTracker gpsTracker;

    private final int REQUEST_AUDIO_PERMISSION_CODE = 1;


    private Integer[] intEmoji = {
            0x1F600, 0x1F603, 0x1F604, 0x1F601, 0x1F606, 0x1F605, 0x1F923, 0x1F602, 0x1F61A, 0x1F619,
            0x1F642, 0x1F643, 0x1F609, 0x1F60A, 0x1F607, 0x1F60B, 0x1F60D, 0x1F929, 0x1F618, 0x1F617,
            0x1F61C, 0x1F92A, 0x1F61D, 0x1F911, 0x1F917, 0x1F92B, 0x1F914, 0x1F910, 0x1F928, 0x1F610,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_AUDIO_PERMISSION_CODE);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/AudioRecording.3gp";

        rl_chat = findViewById(R.id.rl_chat);
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
        iv_delete_msg = findViewById(R.id.iv_delete_msg);
        video_cal = findViewById(R.id.video_cal);

        lstSmiley = new ArrayList<>();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_chat.setLayoutManager(manager);
        callAPI();

        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rv_smileys.setLayoutManager(manager1);

        ChatSmileyAdapter chatSmileyAdapter = new ChatSmileyAdapter(lstSmiley);
        rv_smileys.setAdapter(chatSmileyAdapter);

        try {
            Intent mIntent = getIntent();
            if (null != mIntent) {
                sender_name = mIntent.getStringExtra("Name");
                sender_photo = mIntent.getStringExtra("Image");
                userIDTo = mIntent.getStringExtra("UserID");
                chatId = mIntent.getStringExtra("chatId");
            }

            picasso.get().load(sender_photo).placeholder(R.drawable.cupertino_activity_indicator).into(profile_image);
            chat_name.setText(sender_name);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        CreateSmilyList();
//        getEmoticon();
//        addQuestions();

        chat_name.setOnClickListener(this);
        arrow_back.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        iv_send.setOnClickListener(this);
        iv_video.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        iv_mic.setOnClickListener(this);
        iv_circle.setOnClickListener(this);
        iv_smiley.setOnClickListener(this);

        createRoom();
        mSocket.on("chat_message_room", onNewMessage);
    }

    private void callAPI() {
        adapter = new ChatAdapter(new ArrayList<>(), this);
        rv_chat.setAdapter(adapter);
        scrollTOEnd();
    }

    void scrollTOEnd() {
        rv_chat.post(() -> rv_chat.scrollToPosition(adapter.getItemCount() - 1));
    }

    Emitter.Listener onNewMessage = args -> runOnUiThread(() -> {

        try {
            JSONObject data = (JSONObject) args[0];
            Log.d("****", data.toString());

            adapter.addToListText(data.getString("message"), data.getString("userId"), data.getString("username"), data.getString("profilePic"));
            scrollTOEnd();

        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    private void sendMessgae(String msg) {
        if (null != mSocket) {
            if (mSocket.connected()) {

                try {
                    JSONObject jsonObject = new JSONObject();
                    SessionPref pref = SessionPref.getInstance(ChatMainActivity.this);
                    jsonObject.put("userId", pref.getStringVal(SessionPref.LoginUserID));
                    jsonObject.put("message", msg);
                    jsonObject.put("chatId", chatId);
                    jsonObject.put("username", pref.getStringVal(SessionPref.LoginUserusername));
                    jsonObject.put("profilePic", pref.getStringVal(SessionPref.LoginUserprofilePic));
                    mSocket.emit("chat_message_room", jsonObject);
                } catch (Exception ignored) {

                }
            }
            et_msg.setText("");
        }

    }


    String question = "According to Forbes,which entreprenour became first person in history to have net worth of $400 billion?";

    private void addQuestions() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                adapter.addQuestion(question);
                scrollTOEnd();


            }
        }, 5000);
    }

    private void startRecording() {
        String uuid = UUID.randomUUID().toString();
        mFileName = this.getExternalCacheDir().getAbsolutePath() + "/" + uuid + ".3gp";
        Log.d("FILENAME...", mFileName);
        iv_mic.setEnabled(true);
        pd = TransparentProgressDialog.getInstance(this);
        pd.show();

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
                pd.cancel();
                Log.d("Recording STopped", "Recording Stop");
                Toast.makeText(getApplicationContext(), "Recording Stop", Toast.LENGTH_SHORT).show();

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
                if (requestCode == PICK_PHOTO_FOR_AVATAR) {

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
                        sheet.dismiss();
                        Drawable d = new BitmapDrawable(getResources(), bitmap);
//                        adapter.addImage(d);
                        scrollTOEnd();


                    }
                } else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                    if (data != null) {
                        Uri contentURI = data.getData();

                        Log.d("CONTENTURI ", String.valueOf(contentURI));
                        String selectedVideoPath = contentURI.toString();
//                        String selectedVideoPath = getPath(contentURI);
                        Log.d("path", selectedVideoPath);
//                        adapter.addVIdeo(contentURI);
                        scrollTOEnd();


                    } else {
                        Log.d("FAILED", "FAIELD TO GET DATA");
                        Toast.makeText(this, "FAIELD TO GET DATA", Toast.LENGTH_SHORT).show();
                    }

                } else if (requestCode == TAKE_PHOTO_CODE) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (null != bitmap) {
                        Log.d("BITMAP VALUE", bitmap.toString());
                        sheet.dismiss();
                        Drawable d = new BitmapDrawable(getResources(), bitmap);
//                        adapter.addImage(d);
                        scrollTOEnd();


                    }
                }

            } else {
                Log.d("Failed", "Failed to load");
                Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e + " ERROR EXCEPTION IN RESULT", Toast.LENGTH_SHORT).show();
        }


    }

    boolean audioRecordingPermissionGranted = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
            audioRecordingPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }

        if (!audioRecordingPermissionGranted) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }


    private void openCamera() {
        try {
            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
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
        for (int i = 0; i <= intEmoji.length; i++) {
//            String emoji = new String(Character.toChars(i));
            try {
                lstSmiley.add(intEmoji[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }


    @Override
    public void onSmileyChange(int position) {
        int smiley = lstSmiley.get(position);
        Drawable drawable = getResources().getDrawable(smiley);

//        adapter.addSmiley(drawable);
        scrollTOEnd();


    }





    @Override
    public void onLocationSelect() {

        sheet.dismiss();
        gpsTracker = new GpsTracker(this);
        if (gpsTracker.canGetLocation()) {
            lattitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Log.e("latlong", "" + lattitude + "  " + longitude);
            if (String.valueOf(lattitude).equals("0.0") && String.valueOf(longitude).equals("0.0")) {
                Toast.makeText(this, "Wait a moment", Toast.LENGTH_SHORT).show();
            } else {
//                adapter.sendLcation(lattitude, longitude);
            }
//            Toast.makeText(this,""+lattitude +" , "+ longitude,Toast.LENGTH_SHORT).show();

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
                    SessionPref pref = SessionPref.getInstance(ChatMainActivity.this);
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
        if (id == R.id.arrow_back || id == R.id.profile_image) {
            finish();
        } else if (id == R.id.iv_send) {
            String msg = et_msg.getText().toString();
            if (msg.isEmpty()) {
                Toast.makeText(ChatMainActivity.this, "Empty message can't send", Toast.LENGTH_SHORT).show();
            } else {
                sendMessgae(msg);
            }
        }else if(id==R.id.iv_video){
            Intent intent;
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
            }
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_TAKE_GALLERY_VIDEO);
        }else if(id==R.id.iv_camera){

        }else if(id==R.id.iv_mic){
            startRecording();
        }else if(id==R.id.iv_smiley){
            if (isVisible) {
                rv_smileys.setVisibility(View.GONE);
                isVisible = false;
            } else {
                rv_smileys.setVisibility(View.VISIBLE);
                isVisible = true;
            }
        }

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


