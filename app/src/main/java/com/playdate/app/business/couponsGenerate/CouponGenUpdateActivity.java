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
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponGenUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private CommonClass clsCommon;
    private TextView availbilityDays;
    public final static int PICK_PHOTO_FOR_BUSINESS = 150;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int TAKE_PHOTO_CODE = 0;
    private EditText couponTitle, percentageOff, freeItem, pointsValue, amountOff, NewPrice;
    private final boolean isDropdownVisible = false;
    private TextView addImage;
    private String awardedByStr;
    private final String[] awardedBy = {"Level", "Game Winner", "Game Loser"};
    private LinearLayout ll_camera_option;
    private ImageView camera;
    private ImageView restaurent_img;
    String couponId, couponTitleUpdate, availbilityDaysUpdate, CouponImageUpdate, awardedByUpdate,
            couponAwardlevelUpdate, percentageOffUpdate, freeItemUpdate, pointsValueUpdate, amountOffUpdate, NewPriceUpdate;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_generater);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            couponId = extras.getString("couponId");
            couponTitleUpdate = extras.getString("couponTitle");
            percentageOffUpdate = extras.getString("couponPercentageOff");
            availbilityDaysUpdate = extras.getString("couponAvailableDays");
            NewPriceUpdate = extras.getString("couponNewPrice");
            freeItemUpdate = extras.getString("couponFreeItem");
            pointsValueUpdate = extras.getString("couponPointsValue");
            couponAwardlevelUpdate = extras.getString("couponAwardlevelValue");
            amountOffUpdate = extras.getString("couponAmountOff");
            CouponImageUpdate = extras.getString("CouponImage");
            awardedByUpdate = extras.getString("awardedByUpdate");

        }


        FrameLayout ll_image = findViewById(R.id.ll_image);
        camera = findViewById(R.id.camera);
        addImage = findViewById(R.id.addImage);
        restaurent_img = findViewById(R.id.restaurent_img);
        ll_camera_option = findViewById(R.id.ll_camera_option);

        couponTitle = findViewById(R.id.CouponTitle);
        percentageOff = findViewById(R.id.PercentageOff);
        freeItem = findViewById(R.id.FreeItem);
        pointsValue = findViewById(R.id.PointsValue);
        availbilityDays = findViewById(R.id.AvailbilityDays);
        amountOff = findViewById(R.id.AmountOff);
        NewPrice = findViewById(R.id.NewPrice);
        Spinner spinner1 = findViewById(R.id.spinner1);
        Button btnCreateCoupons = findViewById(R.id.btnCreateCoupons);
        btnCreateCoupons.setText("Update Coupon");

        couponTitle.setText(couponTitleUpdate);
        percentageOff.setText(percentageOffUpdate);
        freeItem.setText(freeItemUpdate);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = (Date) formatter.parse(availbilityDaysUpdate);
            String formateDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
            availbilityDays.setText(formateDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        amountOff.setText(amountOffUpdate);
        NewPrice.setText(NewPriceUpdate);
        pointsValue.setText(pointsValueUpdate);
        Picasso.get().load(CouponImageUpdate)
                .fit()
                .into(restaurent_img);
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
        availbilityDays.setOnClickListener(this);
        btnCreateCoupons.setOnClickListener(this);
        iv_back_generator.setOnClickListener(this);
        camera.setVisibility(View.GONE);
        addImage.setVisibility(View.GONE);
        ll_image.setOnClickListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, awardedBy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(awardedByUpdate);

//set the default according to value
        spinner1.setSelection(spinnerPosition);
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
                    String text = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    availbilityDays.setText(text);
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


    private void callUpdateBusinessCouponApi(String couponTitlestr, String percentageOffStr, String amountOffStr, String newPriceStr, String freeItemStr, String pointsValueStr) {
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(CouponGenUpdateActivity.this);
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
        hashMap.put("couponValidTillDate", availbilityDays.getText().toString());
        hashMap.put("couponAmountOf", amountOffStr);
        hashMap.put("couponPercentageValue", percentageOffStr);
        hashMap.put("freeItem", freeItemStr);
        hashMap.put("newPrice", newPriceStr);
        hashMap.put("awardedBy", awardedByStr);
        hashMap.put("couponPurchasePoint", pointsValueStr);
        hashMap.put("awardlevelValue", "1"); //Hardcode
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("couponId", couponId);

        String url = "";
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (url.isEmpty()) {
                url = key + "=" + value;
            } else {
                url = url + "&" + key + "=" + value;
            }
        }


        MultipartBody.Part filePart = MultipartBody.Part.createFormData("couponImage", f.getName(), RequestBody.create(MediaType.parse("image/png"), f));
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        url = "user/update-business-coupon?" + url;
        Call<CommonModel> call = service.updateBusinessCoupon("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart, hashMap, url);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {

                            clsCommon.showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                            finish();
                        } else {
                            clsCommon.showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(CouponGenUpdateActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(CouponGenUpdateActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                        bitmap = MediaStore.Images.Media.getBitmap(CouponGenUpdateActivity.this.getContentResolver(), data.getData());
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
                        bitmap = MediaStore.Images.Media.getBitmap(CouponGenUpdateActivity.this.getContentResolver(), data.getData());
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
            ActivityCompat.requestPermissions(CouponGenUpdateActivity.this,
                    PERMISSIONS,
                    ALL_PERMISSIONS_RESULT);

            ll_camera_option.setVisibility(View.GONE);
            openCamera();
        } else if (id == R.id.ll_upload_photo) {
            String[] PERMISSIONS = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(CouponGenUpdateActivity.this,
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
            ActivityCompat.requestPermissions(CouponGenUpdateActivity.this,
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
        String availbilityDaysStr = availbilityDays.getText().toString();
        if (couponTitlestr.matches("")) {
            couponTitle.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", "Enter a couponTitle", "Ok");

        } else if (percentageOffStr.matches("")) {
            percentageOff.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", "Enter a percentageOff", "Ok");

        } else if (availbilityDaysStr.matches("")) {
            availbilityDays.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", "Enter a amount off", "Ok");

        } else if (amountOffStr.matches("")) {
            amountOff.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", "Enter a amount off", "Ok");

        } else if (newPriceStr.matches("")) {
            NewPrice.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", "Enter a new price", "Ok");

        }
//        else if (freeItemStr.matches("")) {
//            freeItem.requestFocus();
//            new CommonClass().showDialogMsgfrag(CouponGenActivity.this, "PlayDate", "Enter a free item", "Ok");
//
//        }
        else if (pointsValueStr.matches("")) {
            pointsValue.requestFocus();
            new CommonClass().showDialogMsgFrag(CouponGenUpdateActivity.this, "PlayDate", "Enter a points Vlaue", "Ok");

        } else {
            callUpdateBusinessCouponApi(couponTitlestr, percentageOffStr, amountOffStr, newPriceStr, freeItemStr, pointsValueStr);
        }

    }
}