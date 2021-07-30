package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.coupons.DialogSelectedRestaurant;
import com.playdate.app.ui.dashboard.fragments.FragmentSearchRestaurant;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurentAdapter extends RecyclerView.Adapter<RestaurentAdapter.ViewHolder> implements Filterable {

    private final List<Restaurant> restaurant_list;
    private List<Restaurant> restaurantListFiltered;
    private final Onclick itemClick;
    private final FragmentSearchRestaurant userFrag;
    private final Picasso picasso;

    public RestaurentAdapter(ArrayList<Restaurant> lst_getUserSuggestions, Onclick itemClick, FragmentSearchRestaurant userFrag) {
        this.restaurant_list = lst_getUserSuggestions;
        this.restaurantListFiltered = lst_getUserSuggestions;
        this.itemClick = itemClick;
        this.userFrag = userFrag;
        picasso = Picasso.get();
    }

    Context mcontext;

    @NonNull
    @Override
    public RestaurentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_suggested, parent, false);
        mcontext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurentAdapter.ViewHolder holder, int position) {

        holder.request.setVisibility(View.GONE);
        holder.name.setText(restaurantListFiltered.get(position).getName());
        if (restaurantListFiltered.get(position).getImage() == null) {
            holder.image.setBackgroundColor(mcontext.getResources().getColor(R.color.color_grey_light));
        }

        picasso.load(restaurantListFiltered.get(position).getImage()).placeholder(R.drawable.ic_baseline_person_24)
                .fit()
                .centerCrop()
                .into(holder.image);

        holder.ll_parent.setOnClickListener(v -> new DialogSelectedRestaurant(mcontext, restaurantListFiltered.get(position).getName(),
                restaurantListFiltered.get(position).getImage()).show());


    }


    @Override
    public int getItemCount() {
        if (restaurantListFiltered == null)
            return 0;
        return restaurantListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    restaurantListFiltered = restaurant_list;
                } else {
                    List<Restaurant> filteredList = new ArrayList<>();
                    try {
                        for (Restaurant row : restaurant_list) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getName() != null) {
                                if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                                    filteredList.add(row);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    restaurantListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = restaurantListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                restaurantListFiltered = (ArrayList<Restaurant>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }

    public interface RestaurentAdapterListner {
        void onSuggestionSelected(Restaurant restaurant);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView image;
        private final ImageView request;
        private final LinearLayout ll_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_friend);
            image = itemView.findViewById(R.id.image_friend);
            request = itemView.findViewById(R.id.friend_request);
            ll_parent = itemView.findViewById(R.id.ll_parent);
//            ImageView diamond = itemView.findViewById(R.id.diamond);
//            CardView card = itemView.findViewById(R.id.card);


        }
    }
}
