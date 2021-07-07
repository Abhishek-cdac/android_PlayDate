package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.RestMain;
import com.playdate.app.model.RestaurentData;
import com.playdate.app.model.RestaurentModel;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragLocationTracing extends Fragment implements OnMapReadyCallback {

    SpinKitView spin_kit_location_trace;
    SpinKitView spin_kit_dots1;
    SpinKitView spin_kit_dots2;

    RelativeLayout rl_other;
    RelativeLayout rl_mine;
    TextView tv_location;

    ImageView iv_my_image;
    ImageView iv_partner_image;
    ImageView iv_pin_restaurent;
    ImageView iv_check_mine;
    ImageView iv_check_other;
    ImageView iv_check_rest;
    public static double lattitude;
    public static double longitude;
    private GoogleMap mMap;
    ArrayList<Restaurant> rest_list;
    CommonClass clsCommon;

    public FragLocationTracing() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_location_tracing, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.d("MapFragment", "onCreateView: ");
        }

        clsCommon = CommonClass.getInstance();
        spin_kit_location_trace = view.findViewById(R.id.spin_kit_location_trace);
        spin_kit_dots1 = view.findViewById(R.id.spin_kit_dots1);
        spin_kit_dots2 = view.findViewById(R.id.spin_kit_dots2);

        rl_other = view.findViewById(R.id.rl_other);
        rl_mine = view.findViewById(R.id.rl_mine);
        tv_location = view.findViewById(R.id.tv_location);

        iv_my_image = view.findViewById(R.id.iv_my_image);
        iv_partner_image = view.findViewById(R.id.iv_partner_image);
        iv_pin_restaurent = view.findViewById(R.id.iv_pin_restaurent);
        iv_check_mine = view.findViewById(R.id.iv_check_mine);
        iv_check_other = view.findViewById(R.id.iv_check_other);
        iv_check_rest = view.findViewById(R.id.iv_check_rest);

        Toast.makeText(getActivity(), "" + lattitude + " , " + longitude, Toast.LENGTH_SHORT).show();
        animationFirst();
        getRest();

        return view;
    }

    private void animationFirst() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rl_mine.setVisibility(View.GONE);
                iv_check_mine.setVisibility(View.VISIBLE);
                spin_kit_dots1.setVisibility(View.GONE);
                spin_kit_dots2.setVisibility(View.VISIBLE);

                tv_location.setText("Use a nice cologne...");
                spin_kit_location_trace.getLayoutParams().height = 450;
                spin_kit_location_trace.getLayoutParams().width = 450;
                animationSecond();
            }
        }, 2000);

    }

    private void animationSecond() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_other.setVisibility(View.GONE);
                iv_check_other.setVisibility(View.VISIBLE);
                tv_location.setText("Please be patient...");
                spin_kit_location_trace.getLayoutParams().height = 350;
                spin_kit_location_trace.getLayoutParams().width = 350;
                animationThird();
            }
        }, 2000);
    }

    private void animationThird() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_pin_restaurent.setVisibility(View.GONE);
                spin_kit_dots2.setVisibility(View.GONE);
                iv_check_rest.setVisibility(View.VISIBLE);
                tv_location.setText("We found you...");
                spin_kit_location_trace.getLayoutParams().height = 200;
                spin_kit_location_trace.getLayoutParams().width = 200;

                reDirect();
            }
        }, 3000);
    }

    private void getRest() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");// format 1990-08-12
        hashMap.put("pageNo", "1");// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<RestMain> call = service.restaurants("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<RestMain>() {
            @Override
            public void onResponse(Call<RestMain> call, Response<RestMain> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        rest_list = response.body().getLst();
                        latLongsOfRestaurant();
                        if (rest_list == null) {
                            rest_list = new ArrayList<>();
                        }
                    } else {

                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<RestMain> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void latLongsOfRestaurant() {
        ArrayList<Double> difflist = new ArrayList<>();
        String newLatti = null;
        String newLongi = null;

        for (int i = 0; i < rest_list.size(); i++) {

            double difference = lattitude - Double.parseDouble(rest_list.get(i).getLat());

            for (int j = i + 1; j < rest_list.size(); j++) {
                double diffnew = lattitude - Double.parseDouble(rest_list.get(j).getLat());

                if (difference < diffnew) {
                    newLatti = rest_list.get(i).getLat();
                    newLongi = rest_list.get(i).getLongi();
                }
            }

            Double differenceNew = lattitude - Double.parseDouble(rest_list.get(i).getLat());
            difflist.add(differenceNew);

            Log.d("DiffList", "latLongsOfRestaurantDiff: " + difflist.get(i));
        }
        getRestaurantDetail(newLatti, newLongi);
        Log.d("NewLog", "latLongsOfRestaurantnew: " + newLatti + " , " + newLongi);

    }

    private ArrayList<RestaurentData> lst_getRestaurantsDetail;

    private void getRestaurantDetail(String newLatti, String newLongi) {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lat", newLatti);
        hashMap.put("long", newLongi);

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
                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
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
            public void onFailure(Call<RestaurentModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void reDirect() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragLocationConfirmation(lst_getRestaurantsDetail.get(0).getName(), lst_getRestaurantsDetail.get(0).getImage(), lst_getRestaurantsDetail.get(0).getAddress()));
            }
        }, 1500);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng loc = new LatLng(lattitude, longitude);

        mMap.setMinZoomPreference(12);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }
}
