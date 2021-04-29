package com.playdate.app.util.customcamera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    public final String TAG = CameraPreview.class.getSimpleName();
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    Camera.Parameters parameters;
    public void surfaceCreated(SurfaceHolder holder) {
        try {
             parameters = mCamera.getParameters();
            parameters.set("orientation", "portrait");
            List<String> lst= parameters.getSupportedColorEffects();
            Log.d("ddd",""+lst.size());
//            parameters.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);
//            parameters.setPreviewSize(1440, 1080);
            mCamera.setParameters(parameters);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mCamera.setPreviewDisplay(mHolder);




            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void refreshCamera(Camera myCamera) {
        mCamera = myCamera;

        mHolder = getHolder();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mCamera.setPreviewDisplay(mHolder);


            Camera.Parameters params = mCamera.getParameters();
            params.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);
            try {
                params.setPreviewSize(1440, 1080);
                params.flatten();
                mCamera.setParameters(params);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }

            mCamera.startPreview();


        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void initPreview(Camera myCamera) {
        mCamera = myCamera;
        mHolder = getHolder();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}