package cn.edu.gdut.douyintoutiao.view.show.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import cn.edu.gdut.douyintoutiao.databinding.NewsFollowListFragmentBinding;
import cn.edu.gdut.douyintoutiao.view.show.text.adapter.OtherNewsAdapter;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsViewModel;

/**
 * @author cypang
 */
public class NewsFollowListFragment extends Fragment {

    private NewsViewModel mViewModel;
    private OtherNewsAdapter adapter;
    private NewsFollowListFragmentBinding binding;
    private String tag = "";


    public NewsFollowListFragment() {
    }

    public static NewsFollowListFragment newInstance(String param1) {
        NewsFollowListFragment fragment = new NewsFollowListFragment();
        Bundle args = new Bundle();
        args.putString("tag", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static NewsFollowListFragment newInstance() {
        return new NewsFollowListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NewsFollowListFragmentBinding.inflate(inflater);
        assert getArguments() != null;
        tag = getArguments().getString("tag");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        adapter = new OtherNewsAdapter(requireContext());
        adapter.showEmptyView(true);
        binding.recyclerViewTagNews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewTagNews.setAdapter(adapter);
        SwipeRefreshLayout.OnRefreshListener listener = () -> mViewModel.getAllFollowLive(tag);
        binding.swipeRefreshLayout.post(() -> binding.swipeRefreshLayout.setRefreshing(true));
        listener.onRefresh();
        mViewModel.getAllFollowLive(tag).observe(getViewLifecycleOwner(), news -> {
            adapter.setNewsList(news);
            adapter.notifyDataSetChanged();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
        binding.swipeRefreshLayout.setOnRefreshListener(listener);
    }

}