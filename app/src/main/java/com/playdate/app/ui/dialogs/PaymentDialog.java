package com.playdate.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.playdate.app.R;
import com.playdate.app.ui.my_profile_details.NewPaymentMethod;

public class PaymentDialog extends Dialog implements View.OnClickListener {

    private final NewPaymentMethod newPaymentMethod;

    public PaymentDialog(Context context, int selected, NewPaymentMethod newPaymentMethod) {
        super(context, R.style.My_Dialog);
        this.newPaymentMethod = newPaymentMethod;
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
//        setTitle("Select Card");
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_get_card, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
//        ImageView iv_close=view.findViewById(R.id.iv_close);

        ImageView iv_visa = view.findViewById(R.id.iv_visa);
        ImageView iv_master = view.findViewById(R.id.iv_master);
        ImageView iv_pay = view.findViewById(R.id.iv_pay);
        ImageView iv_amex = view.findViewById(R.id.iv_amex);
        RelativeLayout rl_amex = view.findViewById(R.id.rl_amex);
        RelativeLayout rl_pay = view.findViewById(R.id.rl_pay);
        RelativeLayout rl_master = view.findViewById(R.id.rl_master);
        RelativeLayout rl_visa = view.findViewById(R.id.rl_visa);
        rl_amex.setOnClickListener(this);
        rl_pay.setOnClickListener(this);
        rl_master.setOnClickListener(this);
        rl_visa.setOnClickListener(this);

        switch (selected) {
            case 0:
                iv_visa.setVisibility(View.VISIBLE);
                break;
            case 1:
                iv_master.setVisibility(View.VISIBLE);
                break;
            case 2:
                iv_pay.setVisibility(View.VISIBLE);
                break;
            case 3:
                iv_amex.setVisibility(View.VISIBLE);
                break;
        }

//        iv_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_visa) {
            newPaymentMethod.ChangeCard(0);
        } else if (id == R.id.rl_master) {
            newPaymentMethod.ChangeCard(1);
        } else if (id == R.id.rl_pay) {
            newPaymentMethod.ChangeCard(2);
        } else if (id == R.id.rl_amex) {
            newPaymentMethod.ChangeCard(3);
        }
        dismiss();
    }

}