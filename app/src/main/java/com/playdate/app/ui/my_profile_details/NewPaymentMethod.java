package com.playdate.app.ui.my_profile_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.util.common.CommonClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewPaymentMethod extends Fragment implements AdapterView.OnItemSelectedListener {
    List<String> yearlist = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_payment_method, container, false);
        Spinner spinner_month = view.findViewById(R.id.spinner_month);
        Spinner spinner_year = view.findViewById(R.id.spinner_year);
        RelativeLayout cl_page = view.findViewById(R.id.cl_page);

        int height = new CommonClass().getScreenHeight(getActivity());
        cl_page.getLayoutParams().height = height;

        spinner_month.setOnItemSelectedListener(this);
        spinner_year.setOnItemSelectedListener(this);

        List<String> list = new ArrayList<>();
        list.add("MM ");
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, fetchCountriesCode());
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(dataAdapter2);


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(getActivity(), "Month " + item, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public List<String> fetchCountriesCode() {
        yearlist.clear();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        yearlist.add(" YYYY ");
        for (int i = 0; i < 50; i++) {
            yearlist.add("" + year);
            year = year + 1;
        }
        return yearlist;
    }
}
