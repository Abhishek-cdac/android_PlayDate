package com.playdate.app.ui.date.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.playdate.app.R;
import com.playdate.app.model.CreateDateGetPartnerData;
import com.playdate.app.model.CreateDateGetPartnerModel;
import com.playdate.app.model.PartnerImage;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.date.fragments.FragSelectPartner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Callback;


public class PartnerViewPagerAdapter extends PagerAdapter {
    //    List<PartnerImage> image_list = new ArrayList<>();
    Onclick itemClick;
    private Context mcontext;
    private LayoutInflater mlayoutInflator;
    // private ArrayList<PartnerImage> list;
    private ArrayList<CreateDateGetPartnerData> list;
    private FragSelectPartner frag;

 /*   public PartnerViewPagerAdapter(Context mcontext, ArrayList<CreateDateGetPartnerData> list, FragSelectPartner frag) {
        mlayoutInflator = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mcontext = mcontext;
        this.list = list;
        this.frag = frag;
    }*/

    public PartnerViewPagerAdapter(Context mcontext, ArrayList<CreateDateGetPartnerData> lst_createDateGetPartner, Onclick itemClick) {
        mlayoutInflator = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mcontext = mcontext;
        this.list = lst_createDateGetPartner;
        this.itemClick = itemClick;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

       /* mlayoutInflator = LayoutInflater.from(mcontext);
        View view = mlayoutInflator.inflate(R.layout.row_date_partner, container, false);
*/
        View view = mlayoutInflator.inflate(R.layout.row_date_partner, container, false);

        ImageView partner_image = view.findViewById(R.id.partner_image);
        TextView tv_partner_username = view.findViewById(R.id.tv_partner_username);
        TextView tv_partner_points = view.findViewById(R.id.tv_partner_points);

        tv_partner_points.setText(list.get(position).getTotalPoints());
        tv_partner_username.setText(list.get(position).getUsername());

        Picasso.get().load(list.get(position).getProfilePicPath()).placeholder(R.drawable.cupertino_activity_indicator).into(partner_image);

        container.addView(view, 0);


        partner_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  frag.OnPortnerSelect(position);
//                Log.d("Url ProfileImage", list.get(position).getImage());
                itemClick.onItemClicks(v, position, 20, list.get(position).getUsername(),list.get(position).getTotalPoints(),list.get(position).getId(), list.get(position).getProfilePicPath() );

            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
