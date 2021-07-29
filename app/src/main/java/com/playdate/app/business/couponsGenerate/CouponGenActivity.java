package com.playdate.app.business.couponsGenerate;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.dialogs.DialogCouponCreated;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponGenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private CommonClass clsCommon;
    private TextView availabilityDays;
    public final static int PICK_PHOTO_FOR_BUSINESS = 150;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int TAKE_PHOTO_CODE = 0;
    private EditText couponTitle, percentageOff, freeItem, pointsValue, amountOff, NewPrice;
    private TextView addImage;
    private String awardedByStr;
    private final String[] awardedBy = {"Game Winner", "Game Loser", "Level"};
    //    private Calendar date;
//    private String formateDate;
    private LinearLayout ll_camera_option;
    private ImageView camera;
    private ImageView restaurent_img;
//    String couponId, couponTitleUpdate;
//    Bundle bundle;
//    String level;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_generater);


        FrameLayout ll_image = findViewById(R.id.ll_image);
        camera = findViewById(R.id.camera);
        addImage = findViewById(R.id.addImage);
        restaurent_img = findViewById(R.id.restaurent_img);
        ll_camera_option = findViewById(R.id.ll_camera_option);

        couponTitle = findViewById(R.id.CouponTitle);
        percentageOff = findViewById(R.id.PercentageOff);
        freeItem = findViewById(R.id.FreeItem);
        pointsValue = findViewById(R.id.PointsValue);
        availabilityDays = findViewById(R.id.AvailbilityDays);
        amountOff = findViewById(R.id.AmountOff);
        NewPrice = findViewById(R.id.NewPrice);
        Spinner spinner1 = findViewById(R.id.spinner1);
        Button btnCreateCoupons = findViewById(R.id.btnCreateCoupons);
        clsCommon = CommonClass.getInstance();
        ImageView iv_back_generator = findViewById(R.id.iv_back_generator);
//        RelativeLayout rl_body = findViewById(R.id.rl_body);
//        LinearLayout ll_dropdown = findViewById(R.id.ll_dropdown);
//        TextView tv_awarded = findViewById(R.id.tv_awarded);
//        RelativeLayout rl_awarded = findViewById(R.id.rl_awarded);
//        TextView tv_level = findViewById(R.id.tv_level);
//        ImageView iv_drop = findViewById(R.id.iv_drop);
        LinearLayout ll_take_photo = findViewById(R.id.ll_take_photo);
        LinearLayout ll_upload_photo = findViewById(R.id.ll_upload_photo);
        ll_take_photo.setOnClickListener(this);
        ll_upload_photo.setOnClickListener(this);
        availabilityDays.setOnClickListener(this);
        btnCreateCoupons.setOnClickListener(this);
        iv_back_generator.setOnClickListener(this);
        ll_image.setOnClickListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, awardedBy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
    }


    public void openCamera() {
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_BUSINESS);
    }

    public void showDateTimePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.TimePickerTheme,

                (view, year, monthOfYear, dayOfMonth) -> {
                    String text=year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    availabilityDays.setText(text);
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        awardedByStr = awardedBy[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    Bitmap bitmap = null;

    private void callCreateCouponApi(String couponTitlestr, String percentageOffStr, String amountOffStr, String newPriceStr, String freeItemStr, String pointsValueStr) {
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(CouponGenActivity.this);
        pd.show();
        //create a file to write bitmap data
        File f = new File(getCacheDir(), "profile");
        try {
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = null;
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SessionPref pref = SessionPref.getInstance(this);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("couponTitle", couponTitlestr);
        hashMap.put("couponValidTillDate", availabilityDays.getText().toString());
        hashMap.put("couponAmountOf", amountOffStr);
        hashMap.put("couponPercentageValue", percentageOffStr);
        hashMap.put("freeItem", freeItemStr);
        hashMap.put("newPrice", newPriceStr);
        hashMap.put("awardedBy", awardedByStr);
        hashMap.put("couponPurchasePoint", pointsValueStr);
        hashMap.put("awardlevelValue", "1"); //Hardcode
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        StringBuilder url = new StringBuilder();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (url.length() == 0) {
                url = new StringBuilder(key + "=" + value);
            } else {
                url.append("&").append(key).append("=").append(value);
            }
        }


        MultipartBody.Part filePart = MultipartBody.Part.createFormData("couponImage", f.getName(), RequestBody.create(MediaType.parse("image/png"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        url.insert(0, "user/add-business-coupon?");
        Call<CommonModel> call = service.addBusinessCoupon("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart, hashMap, url.toString());
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
//                            finish();
                            DialogCouponCreated dialog = new DialogCouponCreated(CouponGenActivity.this);
                            dialog.show();
                            dialog.setOnDismissListener(dialogInterface -> finish());
                        } else {
                            clsCommon.showDialogMsgFrag(CouponGenActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(CouponGenActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(CouponGenActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(CouponGenActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode != RESULT_OK) {
                return;
            }
            if (requestCode == PICK_PHOTO_FOR_BUSINESS) {
                if (data.getData() == null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                } else {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(CouponGenActivity.this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != bitmap) {
                    restaurent_img.setImageBitmap(bitmap);
                    camera.setVisibility(View.GONE);
                    addImage.setVisibility(View.GONE);
                    //  showChange();
                }

            } else if (requestCode == TAKE_PHOTO_CODE) {
                if (data.getData() == null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                } else {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(CouponGenActivity.this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                if (null != bitmap) {
                    camera.setVisibility(View.GONE);
                    addImage.setVisibility(View.GONE);
                    restaurent_img.setImageBitmap(bitmap);
                    //   showChange();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_take_photo) {
            String[] PERMISSIONS = {
                    Manifest.permission.CAMERA,
            };
            ActivityCompat.requestPermissions(CouponGenActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);

            ll_camera_option.setVisibility(View.GONE);
            openCamera();
        } else if (id == R.id.ll_upload_photo) {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(CouponGenActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);

            //    bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            // iv_play_date_logo.setVisibility(View.VISIBLE);
            pickImage();
        } else if (id == R.id.AvailbilityDays) {
            showDateTimePicker();
        } else if (id == R.id.ll_image) {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(CouponGenActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);

            //    bottomNavigationView.setVisibility(View.VISIBLE);
            ll_camera_option.setVisibility(View.GONE);
            // iv_play_date_logo.setVisibility(View.VISIBLE);
            pickImage();
            //   ll_camera_option.setVisibility(View.VISIBLE);
        } else if (id == R.id.btnCreateCoupons) {
            createCoupon();
        } else if (id == R.id.iv_back_generator) {
            finish();
        }
    }

    private void createCoupon() {
        String couponTitlestr = couponTitle.getText().toString();
        String percentageOffStr = percentageOff.getText().toString();
        String amountOffStr = amountOff.getText().toString();
        String newPriceStr = NewPrice.getText().toString();
        String freeItemStr = freeItem.getText().toString();
        String pointsValueStr = pointsValue.getText().toString();
        String availbilityDaysStr = availabilityDays.getText().toString();
        if (couponTitlestr.matches("")) {
            couponTitle.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenActivity.this, "PlayDate", "Enter a couponTitle", "Ok");

        } else if (percentageOffStr.matches("")) {
            percentageOff.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenActivity.this, "PlayDate", "Enter a percentageOff", "Ok");

        } else if (availbilityDaysStr.matches("")) {
            availabilityDays.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenActivity.this, "PlayDate", "Enter a amount off", "Ok");

        } else if (amountOffStr.matches("")) {
            amountOff.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenActivity.this, "PlayDate", "Enter a amount off", "Ok");

        } else if (newPriceStr.matches("")) {
            NewPrice.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenActivity.this, "PlayDate", "Enter a new price", "Ok");

        } else if (pointsValueStr.matches("")) {
            pointsValue.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenActivity.this, "PlayDate", "Enter a points Vlaue", "Ok");

        } else {
            callCreateCouponApi(couponTitlestr, percentageOffStr, amountOffStr, newPriceStr, freeItemStr, pointsValueStr);
        }

    }
}