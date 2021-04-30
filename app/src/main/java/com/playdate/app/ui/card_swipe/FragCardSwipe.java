package com.playdate.app.ui.card_swipe;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.playdate.app.R;
import com.playdate.app.model.TinderSwipe;
import com.playdate.app.util.common.CommonClass;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class FragCardSwipe extends Fragment {

    private CardStackLayoutManager manager;
    private TinderSwipeAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tinder_swipe, container, false);
        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        ConstraintLayout cl_page = view.findViewById(R.id.cl_page);

        int height = new CommonClass().getScreenHeight(getActivity());

        int m1 = (int) getResources().getDimension(R.dimen._15sdp);
        int m2 = (int) getResources().getDimension(R.dimen._10sdp);
        int m3 = (int) getResources().getDimension(R.dimen._20sdp);
        int m4 = (int) getResources().getDimension(R.dimen._20sdp);
        int m5 = (int) getResources().getDimension(R.dimen._60sdp);
        int m6 = (int) getResources().getDimension(R.dimen._75sdp);

        cl_page.getLayoutParams().height = height - (m1 + m2 + m3 + m4 + m5 + m6);


        manager = new CardStackLayoutManager(getActivity(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d("OnCardDragging: ", "onCardDragging: d=" + direction.name() + " ratio=" + ratio);

            }

            @Override
            public void onCardSwiped(Direction direction) {


                Log.d("OnCardSwiped: ", "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);

                if (direction == Direction.Right) {
                    //
                }
                if (direction == Direction.Top) {

                }
                if (direction == Direction.Left) {
                }
                if (direction == Direction.Bottom) {

                }
                if (manager.getTopPosition() == adapter.getItemCount() - 5) {
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d("OnCardRewoundTAG", "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d("onCardCanceledTAG", "onCardRewound: " + manager.getTopPosition());

            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView name = view.findViewById(R.id.item_name);
                Log.d("onCardAppearedTAG", "onCardAppeared: " + position + ", nama: " + name.getText());


            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView name = view.findViewById(R.id.item_name);
                Log.d("onCardDisappearedTAG", "onCardAppeared: " + position + ", nama: " + name.getText());

            }
        });


        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new TinderSwipeAdapter();
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    private void paginate() {
        List<TinderSwipe> oldList = adapter.getList();
        List<TinderSwipe> newList = new ArrayList<>(adapter.tinder_list);
        TinderSwipeCallback callback = new TinderSwipeCallback(oldList, newList);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setList(newList);
        hasil.dispatchUpdatesTo(adapter);
    }
}
