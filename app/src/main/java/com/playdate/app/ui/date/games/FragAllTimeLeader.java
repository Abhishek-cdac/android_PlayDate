package com.playdate.app.ui.date.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.PartnerImage;
import com.playdate.app.ui.date.adapter.LeaderAllTimeAdapter;

import java.util.ArrayList;

public class FragAllTimeLeader extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_all_time_leaders, container, false);
        RecyclerView rv_leader = view.findViewById(R.id.rv_leader);
        ArrayList<PartnerImage> list = new ArrayList<>();

        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "kaylajenner12", "1200", "4", true));
        list.add(new PartnerImage("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "malaika2124", "1200", "5", false));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1400", "6", false));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "myronevan122", "1200", "7", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "8", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "9", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "10", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "11", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "8", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "8", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "8", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "8", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "8", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "8", true));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200", "8", true));


        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_leader.setLayoutManager(manager);

        LeaderAllTimeAdapter adapter = new LeaderAllTimeAdapter(list);
        rv_leader.setAdapter(adapter);


        return view;


    }
}
