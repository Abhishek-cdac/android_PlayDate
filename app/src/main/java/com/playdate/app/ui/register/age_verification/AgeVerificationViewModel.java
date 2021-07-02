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

//    private MutableLiveData<Boolean> userMutableLiveData;
//    private MutableLiveData<Boolean> Register;

    public String getYearSelected() {
        return yearlist.get(YearSelectedPosition.getValue());
    }

    public int getMonthSelected() {
        return MonthSelectedPosition.getValue();
    }

    public String getDaySelected() {
        return DayList.get(DaySelectedPosition.getValue());
    }

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

    List<String> list = new ArrayList<>();

    public List<String> fetchMonths() {
        list.clear();
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

    List<String> DayList = new ArrayList<>();

    public MutableLiveData<List<String>> fetchDays() {
        if (daysList == null) {
            return new MutableLiveData<List<String>>();
        }
        DayList.clear();
        DayList.add("Day");
        DayList.add("1");
        DayList.add("2");
        DayList.add("3");
        DayList.add("4");
        DayList.add("5");
        DayList.add("6");
        DayList.add("7");
        DayList.add("8");
        DayList.add("9");
        DayList.add("10");
        DayList.add("11");
        DayList.add("12");
        DayList.add("13");
        DayList.add("14");
        DayList.add("15");
        DayList.add("16");
        DayList.add("17");
        DayList.add("18");
        DayList.add("19");
        DayList.add("20");
        DayList.add("21");
        DayList.add("22");
        DayList.add("23");
        DayList.add("24");
        DayList.add("25");
        DayList.add("26");
        DayList.add("27");
        DayList.add("28");
        if (!is28) {

            DayList.add("29");

            DayList.add("30");
            if (is31) {
                DayList.add("31");
            }
        } else {
            if (isLeapYear) {
                DayList.add("29");
            }
        }

        daysList.setValue(DayList);
        return daysList;
    }


    boolean isLeapYear(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }

    public void setYear(int position) {
        if (position != 0) {
            isLeapYear = isLeapYear(Integer.parseInt(yearlist.get(position)));
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

    public void setDates(String currentYYYY, String currentMM, String currentDDD) {
        for (int i = 0; i < yearlist.size(); i++) {
            if (yearlist.get(i).equals(currentYYYY)) {
                YearSelectedPosition.setValue(i);
                setYear(i);
                break;
            }
        }

        int currMonth = Integer.parseInt(currentMM);
        MonthSelectedPosition.setValue(currMonth);

        int currDay= Integer.parseInt(currentDDD);
        DaySelectedPosition.setValue(currDay);

    }
}