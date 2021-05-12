package com.playdate.app.ui.coupons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Questions;

import java.util.ArrayList;

public class FrequentlyQuestionAdapter extends RecyclerView.Adapter<FrequentlyQuestionAdapter.ViewHolder> {

    ArrayList<Questions> list = new ArrayList<>();

    public FrequentlyQuestionAdapter() {
        list.add(new Questions("How do i earn more points?"));
        list.add(new Questions("Can i share my Coupon Codes?"));
        list.add(new Questions("What if the Coupon Code doesn't work?"));
        list.add(new Questions("Can my Coupon Code be used multiple times?"));
        list.add(new Questions("What if the Coupon Code doesn't work?"));
        list.add(new Questions("Can my Coupon Code be used multiple times?"));
        list.add(new Questions("What if the Coupon Code doesn't work?"));
        list.add(new Questions("Can my Coupon Code be used multiple times?"));
    }

    @NonNull
    @Override
    public FrequentlyQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrequentlyQuestionAdapter.ViewHolder holder, int position) {
        holder.tv_question.setText(list.get(position).getQuestion());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_question;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
        }
    }
}
