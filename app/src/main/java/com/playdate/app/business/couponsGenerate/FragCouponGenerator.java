package com.playdate.app.business.couponsGenerate;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getCacheDir;

public class FragCouponGenerator extends Fragment implements AdapterView.OnItemSelectedListener {
    private CommonClass clsCommon;
    Button btnCreateCoupons;
    ImageView iv_back_generator;
    RelativeLayout rl_body;
    LinearLayout ll_dropdown;
    TextView availbilityDays, tv_awarded;
    TextView tv_level;
    RelativeLayout rl_awarded;
    public final static int PICK_PHOTO_FOR_BUSINESS = 150;
    public final static int ALL_PERMISSIONS_RESULT = 107;
    public final static int TAKE_PHOTO_CODE = 0;
    ImageView iv_drop;
    EditText couponTitle, percentageOff, freeItem, pointsValue, amountOff, NewPrice;
    boolean isDropdownVisible = false;
    Spinner spinner1;
    TextView addImage;
    String awardedByStr;
    String[] awardedBy = {"Level", "Game Winner", "Game Loser"};
    //  private CouponGeneraterViewModel couponGeneraterViewModel;
    Calendar date;
    String formateDate;
    private int mYear, mMonth, mDay;
    LinearLayout ll_camera_option;
    FrameLayout llImage;
    ImageView camera;
    ImageView restaurent_img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_coupons_generater, container, false);

        llImage = view.findViewById(R.id.ll_image);
        camera = view.findViewById(R.id.camera);
        addImage = view.findViewById(R.id.addImage);
        restaurent_img = view.findViewById(R.id.restaurent_img);
        ll_camera_option = view.findViewById(R.id.ll_camera_option);

        couponTitle = view.findViewById(R.id.CouponTitle);
        percentageOff = view.findViewById(R.id.PercentageOff);
        freeItem = view.findViewById(R.id.FreeItem);
        pointsValue = view.findViewById(R.id.PointsValue);
        availbilityDays = view.findViewById(R.id.AvailbilityDays);
        amountOff = view.findViewById(R.id.AmountOff);
        NewPrice = view.findViewById(R.id.NewPrice);
        spinner1 = view.findViewById(R.id.spinner1);

        btnCreateCoupons = view.findViewById(R.id.btnCreateCoupons);
        clsCommon = CommonClass.getInstance();
        iv_back_generator = view.findViewById(R.id.iv_back_generator);
        rl_body = view.findViewById(R.id.rl_body);
        ll_dropdown = view.findViewById(R.id.ll_dropdown);
        tv_awarded = view.findViewById(R.id.tv_awarded);
        rl_awarded = view.findViewById(R.id.rl_awarded);
        tv_level = view.findViewById(R.id.tv_level);
        iv_drop = view.findViewById(R.id.iv_drop);
        LinearLayout ll_take_photo = view.findViewById(R.id.ll_take_photo);
        LinearLayout ll_upload_photo = view.findViewById(R.id.ll_upload_photo);
        ll_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] PERMISSIONS = {
                        Manifest.permission.CAMERA,
                };
                ActivityCompat.requestPermissions(getActivity(),
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);

                //   bottomNavigationView.setVisibility(View.VISIBLE);
                ll_camera_option.setVisibility(View.GONE);
                openCamera();
            }
        });
        ll_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMISSIONS = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(getActivity(),
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);

                //    bottomNavigationView.setVisibility(View.VISIBLE);
                ll_camera_option.setVisibility(View.GONE);
                // iv_play_date_logo.setVisibility(View.VISIBLE);
                pickImage();
            }
        });


        availbilityDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
        llImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMISSIONS = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(getActivity(),
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);

                //    bottomNavigationView.setVisibility(View.VISIBLE);
                ll_camera_option.setVisibility(View.GONE);
                // iv_play_date_logo.setVisibility(View.VISIBLE);
                pickImage();
                //   ll_camera_option.setVisibility(View.VISIBLE);
            }
        });


        btnCreateCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String couponTitlestr = couponTitle.getText().toString();
                String percentageOffStr = percentageOff.getText().toString();
                String amountOffStr = amountOff.getText().toString();
                String newPriceStr = NewPrice.getText().toString();
                String freeItemStr = freeItem.getText().toString();
                String pointsValueStr = pointsValue.getText().toString();
                String availbilityDaysStr = availbilityDays.getText().toString();
                if (couponTitlestr.matches("")) {
                    couponTitle.requestFocus();
                    new CommonClass().showDialogMsgfrag(getActivity(), "PlayDate", "Enter a couponTitle", "Ok");

                } else if (percentageOffStr.matches("")) {
                    percentageOff.requestFocus();
                    new CommonClass().showDialogMsgfrag(getActivity(), "PlayDate", "Enter a percentageOff", "Ok");

                } else if (availbilityDaysStr.matches("")) {
                    availbilityDays.requestFocus();
                    new CommonClass().showDialogMsgfrag(getActivity(), "PlayDate", "Enter a amount off", "Ok");

                } else if (amountOffStr.matches("")) {
                    amountOff.requestFocus();
                    new CommonClass().showDialogMsgfrag(getActivity(), "PlayDate", "Enter a amount off", "Ok");

                } else if (newPriceStr.matches("")) {
                    NewPrice.requestFocus();
                    new CommonClass().showDialogMsgfrag(getActivity(), "PlayDate", "Enter a new price", "Ok");

                } else if (freeItemStr.matches("")) {
                    freeItem.requestFocus();
                    new CommonClass().showDialogMsgfrag(getActivity(), "PlayDate", "Enter a free item", "Ok");

                } else if (pointsValueStr.matches("")) {
                    pointsValue.requestFocus();
                    new CommonClass().showDialogMsgfrag(getActivity(), "PlayDate", "Enter a points Vlaue", "Ok");

                } else {
                    callCreateCouponApi(couponTitlestr, percentageOffStr, amountOffStr, newPriceStr, freeItemStr, pointsValueStr);
                }

            }


        });

        iv_back_generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, awardedBy);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        return view;
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
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        availbilityDays.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        awardedByStr = awardedBy[position];
//        if (position==0){
//            new DialogLevelSelector(getActivity()).show();
//        }
          }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }

    Bitmap bitmap = null;

    private void callCreateCouponApi(String couponTitlestr, String percentageOffStr, String amountOffStr, String newPriceStr, String freeItemStr, String pointsValueStr) {
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
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

        SessionPref pref = SessionPref.getInstance(getContext());


        MultipartBody.Part filePart = MultipartBody.Part.createFormData("couponImage", f.getName(), RequestBody.create(MediaType.parse("image/png"), f));
        Log.e("filePart", "" + filePart);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("couponTitle", couponTitlestr);
        hashMap.put("couponValidTillDate", availbilityDays.getText().toString());
        hashMap.put("couponAmountOf", amountOffStr);
        hashMap.put("couponPercentageValue", percentageOffStr);
        hashMap.put("freeItem", freeItemStr);
        hashMap.put("newPrice", newPriceStr);
        hashMap.put("awardedBy", awardedByStr);
        hashMap.put("couponPurchasePoint", pointsValueStr);
        hashMap.put("awardlevelValue", "1");
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        Call<CommonModel> call = service.addBusinessCoupon("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), filePart, hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            new DialogCouponCreated(getActivity()).show();
                        } else {
                            clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
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
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
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
}