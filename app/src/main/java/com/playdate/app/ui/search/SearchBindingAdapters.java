package com.playdate.app.ui.search;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.model.SearchHistoryItem;
import com.playdate.app.model.SearchUserResult;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchBindingAdapters {

    @BindingAdapter({"resultItems", "viewModel"})
    public static void resultItems(RecyclerView recyclerView, List<SearchUserResult.Item> resultItems,
                                   SearchViewModel viewModel) {
        ResultAdapter resultAdapter = (ResultAdapter) recyclerView.getAdapter();

        if (resultAdapter == null) {
            resultAdapter = new ResultAdapter(viewModel);
            recyclerView.setAdapter(resultAdapter);
        }

        if (resultItems == null) {
            recyclerView.setVisibility(GONE);
        } else {
            recyclerView.setVisibility(VISIBLE);
            resultAdapter.setData(resultItems);
            resultAdapter.notifyDataSetChanged();
        }
    }

    @BindingAdapter({"historyItems", "viewModel"})
    public static void historyItems(RecyclerView recyclerView, List<SearchHistoryItem> historyItems,
                                    SearchViewModel viewModel) {
        HistoryAdapter historyAdapter = (HistoryAdapter) recyclerView.getAdapter();

        if (historyAdapter == null) {
            historyAdapter = new HistoryAdapter(viewModel);
            recyclerView.setAdapter(historyAdapter);
        }

        if (historyItems == null) {
            recyclerView.setVisibility(GONE);
        } else {
            recyclerView.setVisibility(VISIBLE);
            historyAdapter.setData(historyItems);
            historyAdapter.notifyDataSetChanged();
        }
    }
}
