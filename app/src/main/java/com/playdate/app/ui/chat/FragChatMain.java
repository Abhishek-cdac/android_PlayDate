package com.playdate.app.ui.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.chat_models.ChatAttachment;
import com.playdate.app.model.chat_models.ChatExample;
import com.playdate.app.model.chat_models.ChatMessage;
import com.playdate.app.ui.anonymous_question.adapter.SmileyAdapter;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.social.upload_media.PostMediaActivity;
import com.playdate.app.util.common.CommonClass;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.ALL_PERMISSIONS_RESULT;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.PICK_PHOTO_FOR_AVATAR;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.REQUEST_TAKE_GALLERY_VIDEO;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.TAKE_PHOTO_CODE;

public class FragChatMain extends Fragment implements onSmileyChangeListener, onImageSelectListener {


    RecyclerView rv_chat;
    RecyclerView rv_smileys;
    ChatAdapter adapter;
    ImageView iv_send, iv_circle, iv_camera, iv_video, iv_mic, iv_smiley;
    EditText et_msg;
    ImageView profile_image;
    TextView chat_name;
    Intent mIntent;
    String sender_photo;
    String sender_name;
    RelativeLayout rl_chat;
    ArrayList<ChatMessage> chatMsgList;
    ArrayList<Integer> lstSmiley;

    MediaRecorder mRecorder;
    String mFileName = null;
    final int REQUEST_AUDIO_PERMISSION_CODE = 1;


    public FragChatMain(ArrayList<ChatMessage> chatMsgList, String sender_name, String sender_photo) {
        this.chatMsgList = chatMsgList;
        this.sender_photo = sender_photo;
        this.sender_name = sender_name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat_screen, container, false);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/AudioRecording.3gp";

        rl_chat = view.findViewById(R.id.rl_chat);
        rv_smileys = view.findViewById(R.id.rv_smileys);
        iv_mic = view.findViewById(R.id.iv_mic);
        iv_circle = view.findViewById(R.id.iv_circle);
        et_msg = view.findViewById(R.id.et_chat);
        iv_send = view.findViewById(R.id.iv_send);
        iv_camera = view.findViewById(R.id.iv_camera);
        iv_video = view.findViewById(R.id.iv_video);
        rv_chat = view.findViewById(R.id.rv_chat);
        profile_image = view.findViewById(R.id.profile_image);
        chat_name = view.findViewById(R.id.chat_name);
        iv_smiley = view.findViewById(R.id.iv_smiley);

        lstSmiley = new ArrayList<>();

        int height = new CommonClass().getScreenHeight(getActivity());

        int m1 = (int) getResources().getDimension(R.dimen._15sdp);
        int m2 = (int) getResources().getDimension(R.dimen._15sdp);
        int m3 = (int) getResources().getDimension(R.dimen._10sdp);
        int m4 = (int) getResources().getDimension(R.dimen._1sdp);
        int m5 = (int) getResources().getDimension(R.dimen._1sdp);
        int m6 = (int) getResources().getDimension(R.dimen._1sdp);

        rl_chat.getLayoutParams().height = height - (m1 + m2 + m3 + m4 + m5 + m6);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_chat.setLayoutManager(manager);
        adapter = new ChatAdapter(chatMsgList);
        rv_chat.setAdapter(adapter);
        rv_chat.addOnScrollListener(new RecyclerView.OnScrollListener() {
        });

        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rv_smileys.setLayoutManager(manager1);

        ChatSmileyAdapter chatSmileyAdapter = new ChatSmileyAdapter(lstSmiley, this);
        rv_smileys.setAdapter(chatSmileyAdapter);

        Picasso.get().load(sender_photo).placeholder(R.drawable.cupertino_activity_indicator).into(profile_image);
        chat_name.setText(sender_name);

        CreateSmilyList();


        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addToListText(et_msg);
            }
        });
        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                ChatBottomSheet sheet = new ChatBottomSheet("Image", FragChatMain.this);
                sheet.show(fragmentManager, "chat bottom sheet");

            }
        });
        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMISSIONS = {
                        RECORD_AUDIO, WRITE_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(getActivity(),
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);
                openRecorder();

            }
        });
        iv_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                ChatBottomSheet sheet = new ChatBottomSheet("Extra", FragChatMain.this);
                sheet.show(fragmentManager, "chat bootom sheet");

            }
        });
        iv_smiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_smileys.setVisibility(View.VISIBLE);
            }
        });


        return view;
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
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (null != bitmap) {
                        Log.d("BITMAP VALUE", bitmap.toString());
                        Drawable d = new BitmapDrawable(getResources(), bitmap);
                        adapter.addImage(d);

                    }
                } else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                    if (data != null) {
                        Uri contentURI = data.getData();

                        Log.d("CONTENTURI ", String.valueOf(contentURI));
                        String selectedVideoPath = contentURI.toString();
//                        String selectedVideoPath = getPath(contentURI);
                        Log.d("path", selectedVideoPath);
                        adapter.addVIdeo(contentURI);


                    } else {
                        Log.d("FAILED", "FAIELD TO GET DATA");
                        Toast.makeText(getActivity(), "FAIELD TO GET DATA", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                Log.d("Failed", "Failed to load");
                Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e + " ERROR EXCEPTION IN RESULT", Toast.LENGTH_SHORT).show();
        }


    }

    private String getPath(Uri contentURI) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            Log.d("CURSOR STRING", cursor.getString(column_index));
            return cursor.getString(column_index);
        } else {
            Log.d("CURSOR Error", String.valueOf(cursor));

        }

        return cursor.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private void openRecorder() {
        if (CheckPermissions()) {

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile(mFileName);
            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e("Audio recorder", "prepare() failed");
            }
            mRecorder.start();
            Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_LONG).show();
        } else {
            RequestPermissions();
        }

    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);

    }

    private boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
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

    public void CreateSmilyList() {
        lstSmiley.add(R.drawable.face1);
        lstSmiley.add(R.drawable.face2);
        lstSmiley.add(R.drawable.face3);
        lstSmiley.add(R.drawable.face4);
        lstSmiley.add(R.drawable.face5);
        lstSmiley.add(R.drawable.face6);
        lstSmiley.add(R.drawable.face7);
        lstSmiley.add(R.drawable.face8);
        lstSmiley.add(R.drawable.face9);
        lstSmiley.add(R.drawable.face10);
        lstSmiley.add(R.drawable.face11);
        lstSmiley.add(R.drawable.face12);
        lstSmiley.add(R.drawable.face13);
        lstSmiley.add(R.drawable.face14);
        lstSmiley.add(R.drawable.face15);
        lstSmiley.add(R.drawable.face16);
        lstSmiley.add(R.drawable.face17);
        lstSmiley.add(R.drawable.face18);
        lstSmiley.add(R.drawable.face19);
        lstSmiley.add(R.drawable.face20);
        lstSmiley.add(R.drawable.face21);
        lstSmiley.add(R.drawable.face22);
        lstSmiley.add(R.drawable.face23);
        lstSmiley.add(R.drawable.face24);
        lstSmiley.add(R.drawable.face25);
        lstSmiley.add(R.drawable.face26);
        lstSmiley.add(R.drawable.face27);
        lstSmiley.add(R.drawable.face28);
        lstSmiley.add(R.drawable.face29);
    }


    @Override
    public void onSmileyChange(int position) {
        int smiley = lstSmiley.get(position);
        Drawable drawable = getResources().getDrawable(smiley);
        adapter.addSmiley(drawable);
    }

    @Override
    public void onCameraSelect() {

        String[] PERMISSIONS = {
                Manifest.permission.CAMERA,
        };
        ActivityCompat.requestPermissions(getActivity(),
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
        ActivityCompat.requestPermissions(getActivity(),
                PERMISSIONS,
                ALL_PERMISSIONS_RESULT);
        pickImage();
    }
}


interface onSmileyChangeListener {
    void onSmileyChange(int position);
}

interface onImageSelectListener {
    void onCameraSelect();

    void onGallerySelect();
}

//public class ChatActivity extends AppCompatActivity {
//
//    RecyclerView rv_chat;
//    ChatAdapter adapter;
//    ImageView iv_send, iv_circle, iv_camera, iv_video, iv_mic;
//    EditText et_msg;
//    ImageView profile_image;
//    TextView chat_name;
//    Intent mIntent;
//    String sender_photo;
//    String sender_name;
//    ArrayList<ChatExample> chatExampleList;
//    ArrayList<ChatMessage> chatMsgList = new ArrayList<>();
//
//    private MediaRecorder mRecorder;
//    private static String mFileName = null;
//    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_screen);
//        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//        mFileName += "/AudioRecording.3gp";
//        mIntent = getIntent();
//        setInIt();
//    }
//
//    private void setInIt() {
//        iv_mic = findViewById(R.id.iv_mic);
//        iv_circle = findViewById(R.id.iv_circle);
//        et_msg = findViewById(R.id.et_chat);
//        iv_send = findViewById(R.id.iv_send);
//        iv_camera = findViewById(R.id.iv_camera);
//        iv_video = findViewById(R.id.iv_video);
//        rv_chat = findViewById(R.id.rv_chat);
//        profile_image = findViewById(R.id.profile_image);
//        chat_name = findViewById(R.id.chat_name);
//
//        Bundle extras = mIntent.getExtras();
//
//        sender_name = mIntent.getStringExtra("sender_name");
//        sender_photo = mIntent.getStringExtra("sender_profile_image");
//
////        chatMsgList = extras.getParcelable("message_list");
////        chatMsgList = (ArrayList<ChatMessage>) getIntent().getSerializableExtra("message_list");
//
//
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        rv_chat.setLayoutManager(manager);
//        adapter = new ChatAdapter(chatMsgList);
//        rv_chat.setAdapter(adapter);
//        rv_chat.addOnScrollListener(new RecyclerView.OnScrollListener() {
//        });
//
//
//        Picasso.get().load(sender_photo).placeholder(R.drawable.cupertino_activity_indicator).into(profile_image);
//        chat_name.setText(sender_name);
//
//        iv_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adapter.addToList(et_msg);
//            }
//        });
//        iv_video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String[] PERMISSIONS = {
//                        WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                };
//                ActivityCompat.requestPermissions(ChatActivity.this,
//                        PERMISSIONS,
//                        ALL_PERMISSIONS_RESULT);
//                pickImage();
//            }
//        });
//        iv_camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String[] PERMISSIONS = {
//                        Manifest.permission.CAMERA,
//                };
//                ActivityCompat.requestPermissions(ChatActivity.this,
//                        PERMISSIONS,
//                        ALL_PERMISSIONS_RESULT);
//                openCamera();
//            }
//        });
//        iv_mic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String[] PERMISSIONS = {
//                        RECORD_AUDIO, WRITE_EXTERNAL_STORAGE
//                };
//                ActivityCompat.requestPermissions(ChatActivity.this,
//                        PERMISSIONS,
//                        ALL_PERMISSIONS_RESULT);
//                openRecorder();
//
//            }
//        });
//
//        iv_circle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                //    FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                ChatBottomSheet sheet = new ChatBottomSheet();
//                sheet.show(fragmentManager, "chat bootom sheet");
//
//            }
//        });
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_AUDIO_PERMISSION_CODE:
//                if (grantResults.length > 0) {
//                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    if (permissionToRecord && permissionToStore) {
//                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
//        }
//    }
//
//
//
//}
