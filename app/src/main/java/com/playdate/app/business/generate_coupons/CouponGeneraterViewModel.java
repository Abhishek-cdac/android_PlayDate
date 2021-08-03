//package com.playdate.app.business.couponsGenerate;
//
//import android.view.View;
//
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//public class CouponGeneraterViewModel extends ViewModel {
//
//    public CouponGeneraterViewModel() {
//    }
//
//    public MutableLiveData<String> CouponTitle = new MutableLiveData<>();
//    public MutableLiveData<String> PercentageOff = new MutableLiveData<>();
//    public MutableLiveData<String> AvailbilityDays = new MutableLiveData<>();
//    public MutableLiveData<String> AmountOff = new MutableLiveData<>();
//    public MutableLiveData<String> NewPrice = new MutableLiveData<>();
//    public MutableLiveData<String> FreeItem = new MutableLiveData<>();
//    public MutableLiveData<String> PointsValue = new MutableLiveData<>();
//    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
//
//
////    private CallbackManager callbackManager;
////    private LoginManager loginManager;
//
//    private MutableLiveData<CouponGenerator> userMutableLiveData;
//
//
//
//    public MutableLiveData<CouponGenerator> getUser() {
//
//        if (userMutableLiveData == null) {
//            userMutableLiveData = new MutableLiveData<>();
//        }
//        return userMutableLiveData;
//
//    }
//
//
//
//    public void onClickGenerate(View view) {
//
//        CouponGenerator couponGenerator = new CouponGenerator(
//                CouponTitle.getValue(),
//                PercentageOff.getValue(),
//                AvailbilityDays.getValue(),
//                AmountOff.getValue(),
//                NewPrice.getValue(),
//                FreeItem.getValue(),
//                PointsValue.getValue()
//                );
//
//        userMutableLiveData.setValue(couponGenerator);
//    }
//
//
//
//
//
//}
