package com.playdate.app.ui.register.profile;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityUploadProfileBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.LoginUserDetails;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.register.RegisterActivity;
import com.playdate.app.ui.register.interest.InterestActivity;
import com.playdate.app.ui.restaurant.RestaurantActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.image_crop.MainActivity;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.util.session.SessionPref.LoginUserprofilePic;

public class UploadProfileActivity extends AppCompatActivity {

    UploadProfileViewModel viewModel;
    ActivityUploadProfileBinding binding;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int PICK_PHOTO_FOR_AVATAR = 150;
    public final static int TAKE_PHOTO_CODE = 0;
    public final static int REQUEST_TAKE_GALLERY_VIDEO = 200;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new UploadProfileViewModel();
        binding = DataBindingUtil.setContentView(UploadProfileActivity.this, R.layout.activity_upload_profile);
        binding.setLifecycleOwner(this);
        binding.setUploadProfileViewModel(viewModel);


        viewModel.OnNextClick().observe(this, click -> {
            uploadImage();
//            if (mIntent.getBooleanExtra("fromProfile", false)) {
//                Intent mIntent = new Intent();
//                setResult(407, mIntent);
//                finish();
//            } else {
//                uploadImage();
//
//            }

        });

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
        mIntent = getIntent();

    }

    private void uploadImage() {

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();





        File f = new File(getCacheDir(), "profile");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40 , bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SessionPref pref = SessionPref.getInstance(this);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("userProfilePic", f.getName(), RequestBody.create(MediaType.parse("image/png"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Log.d("UPLOADFILEPART", String.valueOf(filePart));
        Call<LoginResponse> call = service.uploadImage("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.dismiss();
                if (response.code() == 200) {

                    LoginUserDetails user = response.body().getUserData();
                    pref.saveStringKeyVal(LoginUserprofilePic,user.getProfilePicPath());

                    if (mIntent.getBooleanExtra("fromProfile", false)) {
                        Intent mIntent = new Intent();
                        setResult(407, mIntent);
                        finish();
                    }else{
                        startActivity(new Intent(UploadProfileActivity.this, InterestActivity
                                .class));
                    }


                } else {
                    new CommonClass().showDialogMsg(UploadProfileActivity.this, "PlayDate", "An error occurred!", "Ok");

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
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

    Bitmap bitmap = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode != RESULT_OK) {
                return;
            }
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
//                    binding.profileImage.setImageBitmap(bitmap);
                    showChange();
                    DashboardActivity.bitmap=bitmap;
                    Intent mIntent = new Intent(this, MainActivity.class);
                    mIntent.putExtra("isForProfile",true);
                    startActivity(mIntent);
                }



            } else if (requestCode == TAKE_PHOTO_CODE) {


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
//                    binding.profileImage.setImageBitmap(bitmap);
                    showChange();
                    DashboardActivity.bitmap=bitmap;
                    Intent mIntent = new Intent(this, MainActivity.class);
                    mIntent.putExtra("isForProfile",true);
                    startActivity(mIntent);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            if(null!=DashboardActivity.bitmap){
                bitmap=DashboardActivity.bitmap;
                binding.profileImage.setImageBitmap(bitmap);
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
