package com.playdate.app.ui.coupons.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.FaqData;

import java.util.ArrayList;

public class FrequentlyQuestionAdapter extends RecyclerView.Adapter<FrequentlyQuestionAdapter.ViewHolder> {


    private final ArrayList<FaqData> list;
    private static int currentPosition = 0;

    public FrequentlyQuestionAdapter(ArrayList<FaqData> faq_list, Context context) {
        this.list = faq_list;
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
        holder.linearLayout.setVisibility(View.GONE);
        holder.textViewAnswer.setText(list.get(position).getAnswer());
        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {
            //creating an animation
            // Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            //toggling visibility
            holder.linearLayout.setVisibility(View.VISIBLE);
            //adding sliding effect
            //   holder.linearLayout.startAnimation(slideDown);
        }

        holder.tv_question.setOnClickListener(view -> {
            //getting the position of the item to expand it
            currentPosition = position;
            //reloading the list
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_question;
        private final TextView textViewAnswer;
        private final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            textViewAnswer = itemView.findViewById(R.id.textViewAnswer);
            tv_question = itemView.findViewById(R.id.tv_question);
        }
    }
}
