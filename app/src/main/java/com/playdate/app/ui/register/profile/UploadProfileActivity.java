package com.playdate.app.ui.register.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.BuildConfig;
import com.playdate.app.R;
import com.playdate.app.databinding.ActivityUploadProfileBinding;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.ui.register.interest.InterestActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.Manifest.permission_group.CAMERA;

public class UploadProfileActivity extends AppCompatActivity {

    UploadProfileViewModel viewModel;
    ActivityUploadProfileBinding binding;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int PICK_PHOTO_FOR_AVATAR = 150;
    private final static int TAKE_PHOTO_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new UploadProfileViewModel();
        binding = DataBindingUtil.setContentView(UploadProfileActivity.this, R.layout.activity_upload_profile);
        binding.setLifecycleOwner(this);
        binding.setUploadProfileViewModel(viewModel);


        viewModel.OnNextClick().observe(this, click -> startActivity(new Intent(UploadProfileActivity.this, InterestActivity
                .class)));

        viewModel.onBackClick().observe(this, click -> finish());
        viewModel.onGalleryClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                String[] PERMISSIONS = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(UploadProfileActivity.this,
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);
                pickImage();

            }
        });

        viewModel.onCameraClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                String[] PERMISSIONS = {
                        Manifest.permission.CAMERA,
                };
                ActivityCompat.requestPermissions(UploadProfileActivity.this,
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);
                openCamera();

            }
        });
        viewModel.OnChangeClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                binding.btnCamera.setVisibility(View.VISIBLE);
                binding.btnGallery.setVisibility(View.VISIBLE);
                binding.txtOr.setVisibility(View.VISIBLE);
                binding.btnChangeImage.setVisibility(View.GONE);
                binding.ivNext.setVisibility(View.GONE);
            }
        });
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    public static int count = 0;
    File newfile;

    public void openCamera() {
        try {
            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
//            Uri outputFileUri = FileProvider.getUriForFile(UploadProfileActivity.this, BuildConfig.APPLICATION_ID, newfile);

//            File newdir = new File(dir);
//            newdir.mkdirs();
//
//            count++;
//            String file = dir + count + ".jpg";
//            newfile = new File(file);
//            try {
//                newfile.createNewFile();
//            } catch (IOException e) {
//            }

            //Uri outputFileUri = Uri.fromFile(newfile);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode != RESULT_OK) {
                return;
            }
            if (requestCode == PICK_PHOTO_FOR_AVATAR) {
                Bitmap bitmap = null;
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
                    binding.profileImage.setImageBitmap(bitmap);
                    showChange();
                }

            } else if (requestCode == TAKE_PHOTO_CODE) {

                Bitmap bitmap = null;
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
                    binding.profileImage.setImageBitmap(bitmap);
                    showChange();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showChange() {
        binding.btnCamera.setVisibility(View.GONE);
        binding.btnGallery.setVisibility(View.GONE);
        binding.txtOr.setVisibility(View.GONE);
        binding.btnChangeImage.setVisibility(View.VISIBLE);
        binding.ivNext.setVisibility(View.VISIBLE);
    }


}
