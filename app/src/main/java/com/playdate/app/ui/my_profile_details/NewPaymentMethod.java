package com.playdate.app.ui.my_profile_details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.ui.dialogs.PaymentDialog;
import com.playdate.app.util.common.CommonClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.card.payment.CardIOActivity;

public class NewPaymentMethod extends Fragment implements AdapterView.OnItemSelectedListener {
    private final List<String> yearlist = new ArrayList<>();

    public NewPaymentMethod() {
    }

    private EditText et_cad_no;
    private EditText et_card_name;
    private EditText et_card_cvv;
    private EditText edt_year;
    private EditText edt_month;
    private ImageView iv_card;
    private int CardSelected = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_payment_method, container, false);
        edt_year = view.findViewById(R.id.edt_year);
        iv_card = view.findViewById(R.id.iv_card);
        edt_month = view.findViewById(R.id.edt_month);
        et_cad_no = view.findViewById(R.id.et_cad_no);
        et_card_cvv = view.findViewById(R.id.et_card_cvv);
        et_card_name = view.findViewById(R.id.et_card_name);
        RelativeLayout cl_page = view.findViewById(R.id.cl_page);
        RelativeLayout rl_visa = view.findViewById(R.id.rl_visa);
        RelativeLayout rl_check = view.findViewById(R.id.rl_check);
        ImageView iv_checkbox = view.findViewById(R.id.iv_checkbox);
        Button btn_scan_card = view.findViewById(R.id.btn_scan_card);

        cl_page.getLayoutParams().height = new CommonClass().getScreenHeight(getActivity());

        btn_scan_card.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CardIOActivity.class)
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                    .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true)
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                    .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false)
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
                    .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "en")
                    .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                    .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, false);
            getActivity().startActivityForResult(intent, 857);
        });

        rl_check.setOnClickListener(v -> {
            iv_checkbox.setVisibility(View.INVISIBLE);
        });

        rl_visa.setOnClickListener(v -> {
            PaymentDialog dialog = new PaymentDialog(getActivity(), CardSelected, NewPaymentMethod.this);
            dialog.show();
        });

//        List<String> list = new ArrayList<>();
//        list.add("MM ");
//        list.add("Jan");
//        list.add("Feb");
//        list.add("Mar");
//        list.add("Apr");
//        list.add("May");
//        list.add("Jun");
//        list.add("Jul");
//        list.add("Aug");
//        list.add("Sep");
//        list.add("Oct");
//        list.add("Nov");
//        list.add("Dec");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_month.setAdapter(dataAdapter);
//
//        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, fetchCountriesCode());
//        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_year.setAdapter(dataAdapter2);


        return view;
    }

    public void ChangeCard(int index) {
        CardSelected = index;
        switch (CardSelected) {
            case 0:
                iv_card.setImageResource(R.drawable.visa);
                break;
            case 1:
                iv_card.setImageResource(R.drawable.master_card);
                break;
            case 2:
                iv_card.setImageResource(R.drawable.paypal);
                break;
            case 3:
                iv_card.setImageResource(R.drawable.amex);
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
//        Toast.makeText(getActivity(), "Month " + item, Toast.LENGTH_SHORT).show();

    }

    public void setData(String Name, String CardNo, String CVV, int month, int Year) {
        et_cad_no.setText(CardNo);
        et_card_name.setText(Name);
        et_card_cvv.setText(CVV);
        edt_month.setText("" + month);
        edt_year.setText("" + Year);
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
