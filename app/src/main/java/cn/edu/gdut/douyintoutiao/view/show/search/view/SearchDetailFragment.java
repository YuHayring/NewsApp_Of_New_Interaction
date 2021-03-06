package cn.edu.gdut.douyintoutiao.view.show.search.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import cn.edu.gdut.douyintoutiao.databinding.SearchDetailFragmentBinding;
import cn.edu.gdut.douyintoutiao.view.show.search.viewmodel.SearchDetailViewModel;
import cn.edu.gdut.douyintoutiao.view.show.text.adapter.OtherNewsAdapter;

public class SearchDetailFragment extends Fragment {

    private SearchDetailViewModel mViewModel;
    private SearchDetailFragmentBinding binding;
    private OtherNewsAdapter adapter;

    public static SearchDetailFragment newInstance() {
        return new SearchDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SearchDetailFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchDetailViewModel.class);
        adapter = new OtherNewsAdapter(requireContext());
        adapter.showEmptyView(true);
        binding.searcbRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searcbRecyclerView.setAdapter(adapter);
        assert getArguments() != null;
        String key = getArguments().getString("key");
        mViewModel.getAllSearchNewsLive(key).observe(getViewLifecycleOwner(), myNews -> {
            adapter.setNewsList(myNews);
            adapter.notifyDataSetChanged();
            binding.searchSwipeRefreshLayout.setRefreshing(false);
        });
        binding.searchSwipeRefreshLayout.setOnRefreshListener(() -> mViewModel.getAllSearchNewsLive(key));

    }


}