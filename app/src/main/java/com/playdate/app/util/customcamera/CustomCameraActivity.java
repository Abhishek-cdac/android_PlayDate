package com.playdate.app.util.customcamera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.playdate.app.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

//import android.hardware.Camera;


public class CustomCameraActivity extends AppCompatActivity implements View.OnClickListener {
//    static
//    {
//        System.loadLibrary("NativeImageProcessor");
//    }
    private Camera myCamera;
    private CameraPreview myCameraSurfaceView;
    private MediaRecorder mediaRecorder;
    //    private Button startVideoButton;
    ImageView iv_flash;
    ImageView iv_back_camera;
    ImageView ivCapture;
    private TextView videoRecordingTimerTextView;
    boolean recording = false;
    private FrameLayout myCameraPreview = null; // this layout contains surfaceview
    private boolean cameraFront = false;
    private int isCameraFlashOn = 0;
    private boolean isVideoRecordingPause;
    private int setOrientationHint;
    // Defined CountDownTimer variables
    private long startVideoRecordingTime = 12 * 1000;
    private CountDownTimer countDownTimer;
    private long timeElapsed;
    private String saveUrl = "/MyBio.mp4";
    private Bundle extras = null;
    private boolean isTime, isUrl;
    private String videoDuration;
    private Parameters p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);

        if (ActivityCompat.checkSelfPermission(CustomCameraActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            String[] PERMISSIONS = {
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            };
            ActivityCompat.requestPermissions(CustomCameraActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);

        } else {
            intializeCameraPreview();
            initid();
            checkIntentExtra();
        }


    }

    // Intialize camera preview first time
    private void intializeCameraPreview() {
        myCamera = getCameraInstance();     //Get Camera for preview
        if (myCamera == null) {
            Toast.makeText(CustomCameraActivity.this, "Failed to open Camera", Toast.LENGTH_LONG).show();
            finish();
        }
        myCameraSurfaceView = new CameraPreview(this, myCamera);
        myCameraPreview = findViewById(R.id.rlCameraPreview);
        myCameraPreview.addView(myCameraSurfaceView);
    }

    private TextSwitcher textswitcher;

    // String array to be shown on textSwitcher
    String textToShow[] = {
            "Tip: The video is 12 seconds long.",
            "Tip: Tell the ladies why you joined us.",
            "Tip: First Impression always counts.",
            "Tip: A big smile is powerful weapon.",
            "Tip: Try out the filters."


    };
    // Total length of the string array
    int messageCount = textToShow.length;
    // to keep current Index of text
    int currentIndex = -1;

    // Set Factory for the textSwitcher *Compulsory part
    void setFactory() {
        textswitcher.setFactory(() -> {

            TextView myText = new TextView(CustomCameraActivity.this);
            myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            myText.setTextSize(18);
            myText.setTextColor(Color.WHITE);
            return myText;
        });
    }

    void loadAnimations() {

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);

        // set the animation type of textSwitcher
        textswitcher.setInAnimation(in);
        textswitcher.setOutAnimation(out);
    }

    private void initid() {
        textswitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        ivCapture = findViewById(R.id.ivCapture);
        ivCapture.setOnClickListener(this);
        iv_back_camera = findViewById(R.id.iv_back_camera);
        iv_back_camera.setOnClickListener(this);
        iv_back_camera.setOnClickListener(this);
//        playPauseButton=(Button)findViewById(R.id.pause_play_button);
//        playPauseButton.setOnClickListener(this);
        iv_flash = findViewById(R.id.iv_flash);
        iv_flash.setOnClickListener(this);
        videoRecordingTimerTextView = findViewById(R.id.timer_textview);
        loadAnimations();
        setFactory();
        setCounter();
    }

    public void checkIntentExtra() { // this function check  intent has extras values or not
        extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("time")) {
                startVideoRecordingTime = (extras.getInt("time") + 1) * 1000;
                isTime = true;

            }
            if (extras.containsKey("url")) {
                saveUrl = extras.getString("url");
                isTime = false;
                isUrl = true;
                Log.d("saveUrl", saveUrl);
            }
        } else {
            Log.d("extras is null", extras + "");
            isTime = false;
        }
    }

    private final static int ALL_PERMISSIONS_RESULT = 107;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivCapture) {
            if (ActivityCompat.checkSelfPermission(CustomCameraActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                String[] PERMISSIONS = {
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                };
                ActivityCompat.requestPermissions(CustomCameraActivity.this,
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);

            } else {

                try {
                    startVideoRecording();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        if (v.getId() == R.id.iv_back_camera) {
            switchCamera();
        }

        if (v.getId() == R.id.iv_flash) {
            if (isFlashAvailable(this)) {
                isCameraFlashOn = isCameraFlashOn + 1;
                if (isCameraFlashOn > 3) {
                    isCameraFlashOn = 0;
                }
                if (isCameraFlashOn == 0) {
                    iv_flash.setImageResource(R.drawable.ic_flash_auto);
                } else if (isCameraFlashOn == 1) {
                    iv_flash.setImageResource(R.drawable.ic_flash_on);
                } else {
                    iv_flash.setImageResource(R.drawable.ic_flash_off);
                }


                setFlash(isCameraFlashOn);
//                isCameraFlashOn = !isCameraFlashOn;
            } else {
                Log.d("flash not available", "flash not here");
            }
        }
    }

    // Choose camera with front and back functionaly
    public void chooseCamera() {
        if (cameraFront) { // if the camera preview is the front
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                // open the backFacingCamera
                // set a picture callback
                // refresh the preview
                Log.d("findBackFacingCamera", cameraId + "");
                myCamera = Camera.open(cameraId); // change switch camera icon image
                myCamera.lock();
                myCamera.setDisplayOrientation(90);
                myCameraSurfaceView.initPreview(myCamera);
                iv_flash.setVisibility(View.INVISIBLE);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                // open the backFacingCamera
                // set a picture callback
                // refresh the preview
                Log.d("findFrontFacingCamera", cameraId + "");
                myCamera = Camera.open(cameraId); // change switch camera icon image
                myCamera.lock();
                myCamera.setDisplayOrientation(90);
                myCameraSurfaceView.refreshCamera(myCamera);
                iv_flash.setVisibility(View.VISIBLE);
            }
        }
    }

    // return cameraPreview Id 1 to open front camera
    private int findFrontFacingCamera() {
        //releaseCamera();
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                setOrientationHint = 270;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }

    // return cameraPreview Id 0 to  open back camera
    private int findBackFacingCamera() {
        //releaseCamera();
        //releaseMediaRecorder();
        int cameraId = -1;
        // Search for the back facing camera
        int numberOfCameras = Camera.getNumberOfCameras(); // get the number of cameras
        // for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                setOrientationHint = 90;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }

    // return camera instance when activity open first time
    private Camera getCameraInstance() {
        releaseCamera();
        releaseMediaRecorder();
        Camera c = null;
        try {
            c = Camera.open(findBackFacingCamera());
            c.setDisplayOrientation(90);
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return c; // returns null if camera is unavailable
    }

    @SuppressLint("InlinedApi")
    private boolean prepareMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        try {
            isCameraFlashOn = 0;
//            if (isCameraFlashOn) {  // camera flash on off
            setFlash(isCameraFlashOn);
//            }
            myCamera.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaRecorder.setCamera(myCamera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(1, CamcorderProfile.QUALITY_HIGH));

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES);
        File file = new File(path, saveUrl);
        path.mkdirs();


        mediaRecorder.setOutputFile(file);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setPreviewDisplay(myCameraSurfaceView.getHolder().getSurface());
        mediaRecorder.setOrientationHint(setOrientationHint);
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    // Setup video recording timer function and start timer
    public void startVideoRecordingTimer() {
        setupCountDown();
        countDownTimer.start();
    }

    // switch camera to front or rear
    private void switchCamera() {
        if (!recording) {
            int camerasNumber = Camera.getNumberOfCameras();
            if (camerasNumber > 1) {
                releaseCamera(); // release the old camera instance
                chooseCamera(); // switch camera, from the front and the back and vice versa
            } else {
            }
        }
    }

    // start video recording function
    private void startVideoRecording() throws IOException {
        // TODO Auto-generated method stub
        // check if video recording already started
        if (recording) {
            stopVideoRecording(); // stop recording and release camera
            stopRecordingTimer();
        } else {
            if (!prepareMediaRecorder()) {
                Toast.makeText(CustomCameraActivity.this,
                        "Fail in prepareMediaRecorder()!\n - Ended -",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            startVideoRecordingTimer();
            //Invisible view after start recording
            iv_back_camera.setVisibility(View.INVISIBLE);
            iv_flash.setVisibility(View.INVISIBLE);
            mediaRecorder.start();
            recording = true;
        }
    }

    /* this function is  not in working now
     * use to pause or resume video when recording
     */
//    private void playPauseVideoRecording() {
//        if(isVideoRecordingPause){
//            isVideoRecordingPause=false;
////            playPauseButton.setBackgroundResource(R.drawable.ic_play_circle_outline_white_48dp);
//            mediaRecorder.reset();
//            stopRecordingTimer();
//        }else{
//            isVideoRecordingPause=true;
//            playPauseButton.setBackgroundResource(R.drawable.ic_pause_circle_outline_white_48dp);
//        }
//    }
    // stop recording timer when stop video record
    private void stopRecordingTimer() {
        countDownTimer.cancel();
    }

    // set camera flash on off
    private void setFlash(int val) {
        if (val == 0) {
            Parameters params = myCamera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_AUTO);
            myCamera.setParameters(params);
        } else if (val == 1) {
            Parameters params = myCamera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            myCamera.setParameters(params);
        } else {
            Parameters params = myCamera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            myCamera.setParameters(params);
        }
    }

    // stop video recording
    public void stopVideoRecording() {
        //Invisible view after start recording
        try {
            iv_back_camera.setVisibility(View.VISIBLE);
            iv_flash.setVisibility(View.VISIBLE);
            mediaRecorder.stop();   // stop the recording
            releaseMediaRecorder(); // release the MediaRecorder object\
            if (isFlashAvailable(this)) {
                isCameraFlashOn = 0;
                setFlash(isCameraFlashOn);
            }

            releaseCamera();
            recording = false;
            stopRecordingTimer();
            /*
             * intent use here to send back response to activity that start this current activity
             */
            Intent returnIntent = new Intent();
            // check that video save url coming from previous activity or not
            if (!isUrl) {
                returnIntent.putExtra("url", saveUrl);
            }
            returnIntent.putExtra("videoDuration", videoDuration);
            setResult(1, returnIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /* this code check video recording continue or not when current activity goes in pause state
         *
         */
        if (recording) {
            Log.d("onPause", "OK");
            stopVideoRecording();
        } else {
            Log.d("finishActivity", "ok");
            finish();
        }
        //myCameraSurfaceView.onPauseMySurfaceView();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // myCameraSurfaceView.onResumeMySurfaceView();
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release();  // release the recorder object
            mediaRecorder = null;
            myCamera.lock();       // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (myCamera != null) {
            myCamera.release();    // release the camera for other applications
            myCamera = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (recording) {
            stopVideoRecording();
        } else {
            releaseCamera();
            finish();
        }
        Log.d("onBackPressed", "yes");
        // finish();
    }

    // function setup countdown timer for video recording


    private void setupCountDown() {
        Log.d("startVideoRecordingTime", startVideoRecordingTime + "");
        countDownTimer = new CountDownTimer(startVideoRecordingTime, 1000) {
            public void onTick(long millisUntilFinished) {
                // check that video recording time coming previous activity or not
                if (!isTime) {
                    // if time not coming from previous activity then time will increase
                    timeElapsed = startVideoRecordingTime - millisUntilFinished;
                    // Get video duration
                    videoDuration = String.format("%d :%d ",
                            TimeUnit.MILLISECONDS.toMinutes(timeElapsed),
                            TimeUnit.MILLISECONDS.toSeconds(timeElapsed) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeElapsed)));
                    showRecordingTime(timeElapsed);
                } else {
                    timeElapsed = startVideoRecordingTime - millisUntilFinished;
                    // Get video duration
                    videoDuration = String.format("%d :%d ",
                            TimeUnit.MILLISECONDS.toMinutes(timeElapsed),
                            TimeUnit.MILLISECONDS.toSeconds(timeElapsed) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeElapsed)));
                    showRecordingTime(millisUntilFinished);
                }
            }

            public void onFinish() {
                Log.d("finish", "yes");
                stopVideoRecording();
            }
        };
    }


    private void setCounter() {
        CountDownTimer ctimer = new CountDownTimer(60 * 1000, 4000) {
            public void onTick(long millisUntilFinished) {
                currentIndex++;
                // If index reaches maximum reset it
                if (currentIndex == messageCount)
                    currentIndex = 0;
                textswitcher.setText(textToShow[currentIndex]);
            }


            public void onFinish() {
            }
        };
        ctimer.start();
    }


    @SuppressLint("InlinedApi")
    public void showRecordingTime(long time) {
//        Log.d("startVideoRecordingTime", startVideoRecordingTime + "");
//        videoRecordingTimerTextView.setText("" + String.format("%d :%d ",
//                TimeUnit.MILLISECONDS.toMinutes(time),
//                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));

        switch (count) {

            case 1:
                ivCapture.setImageResource(R.drawable.img1);
                break;
            case 2:
                ivCapture.setImageResource(R.drawable.img2);
                break;
            case 3:
                ivCapture.setImageResource(R.drawable.img3);
                break;
            case 4:
                ivCapture.setImageResource(R.drawable.img4);
                break;
            case 5:
                ivCapture.setImageResource(R.drawable.img5);
                break;
            case 6:
                ivCapture.setImageResource(R.drawable.img6);
                break;
            case 7:
                ivCapture.setImageResource(R.drawable.img7);
                break;
            case 8:
                ivCapture.setImageResource(R.drawable.img8);
                break;
            case 9:
                ivCapture.setImageResource(R.drawable.img9);
                break;
            case 10:
                ivCapture.setImageResource(R.drawable.img10);
                break;
            case 11:
                ivCapture.setImageResource(R.drawable.img11);
                break;
            case 12:
                ivCapture.setImageResource(R.drawable.img12);
                break;

        }
        count = count + 1;


    }

    int count = 1;

    /*
     * @return true if a flash is available, false if not
     */
    public static boolean isFlashAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
