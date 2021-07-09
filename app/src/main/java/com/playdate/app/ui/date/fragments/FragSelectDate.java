package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gtomato.android.ui.transformer.FlatMerryGoRoundTransformer;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CreateDateGetPartnerData;
import com.playdate.app.model.CreateDateGetPartnerModel;
import com.playdate.app.service.GpsTracker;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSelectDate extends Fragment {

    private double lattitude;
    private double longitude;
    boolean fromChat = false;
    OnInnerFragmentClicks frag;
    public static String userIdTo;
    private ArrayList<CreateDateGetPartnerData> lst_CreateDateGetPartner;
    private CommonClass clsCommon;
    String name;
    String points;
    String id;
    String image;


    public FragSelectDate(boolean fromChat) {
        this.fromChat = fromChat;
        Log.d("FragSelectDate", "FragSelectDate: " + fromChat);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_select_date, container, false);
        RelativeLayout rl_inperson = view.findViewById(R.id.in_person);
        RelativeLayout rl_virtual = view.findViewById(R.id.virtual);
        frag = (OnInnerFragmentClicks) getActivity();
        clsCommon = CommonClass.getInstance();


        if (fromChat) {
            callCreateDateGetPartnerListAPI();
        }

        rl_inperson.setOnClickListener(v -> {
            locationFetch();

        });



        rl_virtual.setOnClickListener(v -> {
            Log.d("FromChat", "onCreateView virtual: " + fromChat);
            if (fromChat) {

                checkforChat();

                Fragment fragment = new FragPartnerSelected("virtual");
                Bundle bundle = new Bundle();
                bundle.putString("profile_name", name);
                bundle.putString("profile_points", points);
                bundle.putString("profile_userId", id);
                bundle.putString("profile_image", image);
                Log.e("profile_toUserID..", "" + id);
                fragment.setArguments(bundle);
                assert frag != null;
                frag.ReplaceFrag(fragment);

//                Objects.requireNonNull(frag).ReplaceFrag(new FragPartnerSelected("virtual"));

            } else {
                Objects.requireNonNull(frag).ReplaceFrag(new FragSelectPartner("virtual"));
            }
        });

        return view;
    }

    private void checkforChat() {
        for (int i = 0; i < lst_CreateDateGetPartner.size(); i++) {
            if (lst_CreateDateGetPartner.get(i).getId().equals(userIdTo)) {
                name = lst_CreateDateGetPartner.get(i).getUsername();
                points = lst_CreateDateGetPartner.get(i).getTotalPoints();
                id = lst_CreateDateGetPartner.get(i).getId();
                image = lst_CreateDateGetPartner.get(i).getProfilePicPath();
            }
        }
    }

    private void locationFetch() {
        GpsTracker gpsTracker = new GpsTracker(getActivity());
        if (gpsTracker.canGetLocation()) {
            this.lattitude = gpsTracker.getLatitude();
            this.longitude = gpsTracker.getLongitude();
            Log.d("latlong", "" + lattitude + "  " + longitude);
            if (String.valueOf(lattitude).equals("0.0") || String.valueOf(longitude).equals("0.0")) {
                Toast.makeText(getActivity(), "Fetching your location", Toast.LENGTH_SHORT).show();
//                locationFetch();

            } else {
                Log.d("Current Location", "locationFetch: " + lattitude + " , " + longitude);

                Toast.makeText(getActivity(), "" + lattitude + " , " + longitude, Toast.LENGTH_SHORT).show();
                FragLocationTracing.lattitude = lattitude;
                FragLocationTracing.longitude = longitude;

                Log.d("FromChat", "onCreateView inperson: " + fromChat);

                if (fromChat) {

                    checkforChat();

                    Fragment fragment = new FragPartnerSelected("in Person");
                    Bundle bundle = new Bundle();
                    bundle.putString("profile_name", name);
                    bundle.putString("profile_points", points);
                    bundle.putString("profile_userId", id);
                    bundle.putString("profile_image", image);
                    Log.e("profile_toUserID..", "" + id);
                    fragment.setArguments(bundle);
                    assert frag != null;
                    frag.ReplaceFrag(fragment);

//                    Objects.requireNonNull(frag).ReplaceFrag(new FragPartnerSelected("in Person"));
                } else {
                    Objects.requireNonNull(frag).ReplaceFrag(new FragSelectPartner("in Person"));
                }
            }


        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void callCreateDateGetPartnerListAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<CreateDateGetPartnerModel> call = service.createDateGetPartnerList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CreateDateGetPartnerModel>() {
            @Override
            public void onResponse(Call<CreateDateGetPartnerModel> call, Response<CreateDateGetPartnerModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        lst_CreateDateGetPartner = (ArrayList<CreateDateGetPartnerData>) response.body().getData();
                        if (lst_CreateDateGetPartner == null) {
                            lst_CreateDateGetPartner = new ArrayList<>();
                        }
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateDateGetPartnerModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
