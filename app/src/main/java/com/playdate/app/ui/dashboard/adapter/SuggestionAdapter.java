package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.playdate.app.R;
import com.playdate.app.ui.dashboard.fragments.FragSuggestion;
import com.playdate.app.ui.onboarding.OnBoardingImageFragment;

import java.util.ArrayList;

public class SuggestionAdapter extends PagerAdapter {

    private Context context;

    public SuggestionAdapter(Context mContext) {
        this.context = mContext;
    }
//    public SuggestionAdapter(final Context context, final FragmentManager fm) {
//        super(fm);
//        mContext = context.getApplicationContext();
//    }

//    @Override
//    public Fragment getItem(int position) {
//        return new FragSuggestion();
//    }
//
//    @Override
//    public float getPageWidth(int position) {
//        return 0.85f;
//    }
//




    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
    private LayoutInflater layoutInflater;
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.frag_suggestions, container, false);

        ImageView imageView;
        TextView title, desc;

//        imageView = view.findViewById(R.id.image);
//        title = view.findViewById(R.id.title);
//        desc = view.findViewById(R.id.desc);
//
//        imageView.setImageResource(models.get(position).getImage());
//        title.setText(models.get(position).getTitle());
//        desc.setText(models.get(position).getDesc());
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("param", models.get(position).getTitle());
//                context.startActivity(intent);
//                // finish();
//            }
//        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


}