package com.playdate.app.ui.register.age_verification;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AgeVerificationViewModel extends ViewModel {
    public MutableLiveData<Boolean> RegisterClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> iv_backClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> NextClick = new MutableLiveData<>();
    public MutableLiveData<Integer> DaySelectedPosition = new MutableLiveData<>();
    public MutableLiveData<Integer> MonthSelectedPosition = new MutableLiveData<>();
    public MutableLiveData<Integer> YearSelectedPosition = new MutableLiveData<>();
    public MutableLiveData<List<String>> daysList = new MutableLiveData<>();

    private MutableLiveData<Boolean> userMutableLiveData;
    private MutableLiveData<Boolean> Register;

    public MutableLiveData<Boolean> onRegisterUser() {

        if (RegisterClick == null) {
            RegisterClick = new MutableLiveData<>();
        }
        return RegisterClick;

    }

    public MutableLiveData<Integer> DaySelectedPosition() {
        return DaySelectedPosition;

    }

    public MutableLiveData<Integer> MonthSelectedPosition() {
        return MonthSelectedPosition;

    }

    public MutableLiveData<Boolean> onNextClick() {
        return NextClick;

    }

    public MutableLiveData<Integer> YearSelectedPosition() {
        return YearSelectedPosition;

    }


    public void onBackClick(View view) {
        iv_backClick.setValue(true);
    }

    List<String> yearlist = new ArrayList<>();

    public List<String> fetchCountriesCode() {
        yearlist.clear();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        year = year - 18;
        yearlist.add("Year");
        for (int i = 0; i < 50; i++) {
            yearlist.add("" + year);
            year = year - 1;
        }
        return yearlist;
    }

    public List<String> fetchMonths() {
        List<String> list = new ArrayList<>();
        list.add("Month");
        list.add("Jan");
        list.add("Feb");
        list.add("Mar");
        list.add("Apr");
        list.add("May");
        list.add("Jun");
        list.add("Jul");
        list.add("Aug");
        list.add("Sep");
        list.add("Oct");
        list.add("Nov");
        list.add("Dec");
        return list;
    }
    public void onNext(View view) {
        NextClick.setValue(true);
    }
    public boolean is28 = false;
    public boolean is31 = false;
    public boolean isLeapYear = false;

    public MutableLiveData<List<String>> fetchDays() {
        if (daysList == null) {
            return new MutableLiveData<List<String>>();
        }
        List<String> list = new ArrayList<>();
        list.add("Day");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        list.add("21");
        list.add("22");
        list.add("23");
        list.add("24");
        list.add("25");
        list.add("26");
        list.add("27");
        list.add("28");
        if (!is28) {

            list.add("29");

            list.add("30");
            if (is31) {
                list.add("31");
            }
        } else {
            if (isLeapYear) {
                list.add("29");
            }
        }

        daysList.setValue(list);
        return daysList;
    }


    boolean isLeapYear(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }

    public void setYear(int position) {
        if (position != 0) {
            if (isLeapYear(Integer.parseInt(yearlist.get(position)))) {
                isLeapYear = true;
            } else {
                isLeapYear = false;
            }
            fetchDays();

        }

    }

    public void setDays(int position) {
        switch (position) {
            case 0:
            case 1://jan
            case 3://march
            case 5://may
            case 7://july
            case 8://aug
            case 10://oct
            case 12:
                is28 = false;
                is31 = true;
                break;
            case 2://feb
                is28 = true;
                is31 = false;
                break;
            case 4://apr
            case 6://jun
            case 9://set
            case 11://nov
                is28 = false;
                is31 = false;
                break;


        }


        fetchDays();
    }


    public void onMontheSelected(View parent, View view, int pos, int id) {
        // log selected
        Log.d("ddd", "" + pos);


    }

}