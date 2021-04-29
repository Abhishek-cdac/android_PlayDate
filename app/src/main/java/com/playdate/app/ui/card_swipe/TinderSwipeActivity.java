package com.playdate.app.ui.card_swipe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.playdate.app.R;
import com.playdate.app.model.TinderSwipe;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class TinderSwipeActivity extends AppCompatActivity {
    private CardStackLayoutManager manager;
    private TinderSwipeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tinder_swipe);

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d("OnCardDragging: ", "onCardDragging: d=" + direction.name() + " ratio=" + ratio);

            }

            @Override
            public void onCardSwiped(Direction direction) {



                Log.d("OnCardSwiped: ", "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);

                if (direction == Direction.Right) {
                    Toast.makeText(TinderSwipeActivity.this, "Direction Right", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Top) {
                    Toast.makeText(TinderSwipeActivity.this, "Direction Top", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left) {
                    Toast.makeText(TinderSwipeActivity.this, "Direction Left", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Bottom) {
                    Toast.makeText(TinderSwipeActivity.this, "Direction Bottom", Toast.LENGTH_SHORT).show();
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
    }

    private void paginate() {
        List<TinderSwipe> oldList = adapter.getList();
        List<TinderSwipe> newList = new ArrayList<>(adapter.tinder_list);
        TinderSwipeCallback callback = new TinderSwipeCallback(oldList, newList);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setList(newList);
        hasil.dispatchUpdatesTo(adapter);

    }

//    private List<TinderSwipe> addList() {
//        List<TinderSwipe> tinder_list = new ArrayList<>();
//
//        tinder_list.add(new TinderSwipe("https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", "adreena helen", "Dancing", 23));
//        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Singing", 27));
//        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "jonn den", "Travelling", 29));
//        tinder_list.add(new TinderSwipe("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "Ramsphy k", "Blogging", 18));
//        tinder_list.add(new TinderSwipe("https://s29588.pcdn.co/wp-content/uploads/sites/2/2018/08/Claire-Abbott-1.jpg.optimal.jpg", "adreena helen", "Dancing and Blogging", 20));
//        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Travelling & Blogging", 24));
//        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Travelling & Blogging", 24));
//        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Travelling & Blogging", 24));
//        tinder_list.add(new TinderSwipe("https://images.saymedia-content.com/.image/t_share/MTc1MDE0NzI4MTg2OTk2NTIz/5-instagram-models-you-should-be-following.png", "gomes helen", "Travelling & Blogging", 24));
//
//
//        return tinder_list;
//    }
}
