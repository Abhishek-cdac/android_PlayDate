package com.playdate.app.ui.date.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetCoupleProfileData;
import com.playdate.app.model.GetCoupleProfileModel;
import com.playdate.app.model.RestaurentData;
import com.playdate.app.model.RestaurentModel;
import com.playdate.app.service.GpsTracker;
import com.playdate.app.ui.date.adapter.RestaurantSelectionAdapter;
import com.playdate.app.ui.date.games.FragTimesUp2;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class FragRestaurantSelection extends Fragment implements restaurantSelecteListener {
    RecyclerView rv_restaurant;
    private CommonClass clsCommon;
    private GpsTracker gpsTracker;
    private ArrayList<RestaurentData> lst_getRestaurantsDetail;
    public FragRestaurantSelection() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_select_restaurant, container, false);
        clsCommon = CommonClass.getInstance();
        rv_restaurant = view.findViewById(R.id.rv_restaurant);

        try {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        getLocation(view);
        callgetRestaurantDetails();

        return view;
    }
    public void getLocation(View view){
        gpsTracker = new GpsTracker(getActivity());
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            Log.e("latlong",""+latitude +"  "+ longitude);
//            tvLatitude.setText(String.valueOf(latitude));
//            tvLongitude.setText(String.valueOf(longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void callgetRestaurantDetails() {

        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<RestaurentModel> call = service.getRestaurantDetails("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<RestaurentModel>() {
            @Override
            public void onResponse(Call<RestaurentModel> call, Response<RestaurentModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        lst_getRestaurantsDetail = (ArrayList<RestaurentData>) response.body().getData();
                        if (lst_getRestaurantsDetail == null) {
                            lst_getRestaurantsDetail = new ArrayList<>();
                        }
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        rv_restaurant.setLayoutManager(manager);

                        RestaurantSelectionAdapter adapter = new RestaurantSelectionAdapter(getActivity(), lst_getRestaurantsDetail);
                        rv_restaurant.setAdapter(adapter);


                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<RestaurentModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void restSelected() {
        OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
        frag.ReplaceFrag(new FragLocationConfirmation());
    }
}

interface restaurantSelecteListener {
    void restSelected();
}
