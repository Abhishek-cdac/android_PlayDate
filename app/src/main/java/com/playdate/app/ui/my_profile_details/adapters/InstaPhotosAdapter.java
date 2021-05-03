package com.playdate.app.ui.my_profile_details.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InstaPhotosAdapter extends RecyclerView.Adapter<InstaPhotosAdapter.ViewHolder> {

    ArrayList<String> lst = new ArrayList();

    public InstaPhotosAdapter() {
        lst.add("https://i1.sndcdn.com/artworks-mZMWFyprEwuWmaHG-vMwonQ-t500x500.jpg");
        lst.add("https://i.pinimg.com/originals/c6/5a/e9/c65ae905eb4e390818effae29093ec2e.jpg");
        lst.add("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png");
        lst.add("https://i.pinimg.com/originals/a6/96/ec/a696ecdd0809c0313b857052c583a4bb.jpg");
        lst.add("https://i.pinimg.com/originals/de/37/66/de37665e7947cc1edce63f70bd0e02f9.png");
        lst.add("https://www.stylevore.com/wp-content/uploads/2020/02/82507950_183459019718766_4702592670515235395_n.jpg");
        lst.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/gettyimages-990349008-1538405275.jpg");
        lst.add("https://cdn.ebaumsworld.com/2019/07/11/022903/86013647/3.jpg");
        lst.add("https://ar.justinfeed.com/img/images_13/15-insta-models-who-are-celeb-doppelgngers_9.jpg");
        lst.add("https://i1.sndcdn.com/artworks-mZMWFyprEwuWmaHG-vMwonQ-t500x500.jpg");
        lst.add("https://i.pinimg.com/originals/c6/5a/e9/c65ae905eb4e390818effae29093ec2e.jpg");
        lst.add("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png");
        lst.add("https://i.pinimg.com/originals/a6/96/ec/a696ecdd0809c0313b857052c583a4bb.jpg");
        lst.add("https://i.pinimg.com/originals/de/37/66/de37665e7947cc1edce63f70bd0e02f9.png");
        lst.add("https://www.stylevore.com/wp-content/uploads/2020/02/82507950_183459019718766_4702592670515235395_n.jpg");
        lst.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/gettyimages-990349008-1538405275.jpg");
        lst.add("https://cdn.ebaumsworld.com/2019/07/11/022903/86013647/3.jpg");
        lst.add("https://ar.justinfeed.com/img/images_13/15-insta-models-who-are-celeb-doppelgngers_9.jpg");

    }

    @NonNull
    @Override
    public InstaPhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photos, null);
        return new InstaPhotosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstaPhotosAdapter.ViewHolder holder, int position) {
        Picasso.get().load(lst.get(position))
                .into(holder.iv_payment);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_payment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_payment = itemView.findViewById(R.id.iv_payment);
        }
    }
}
