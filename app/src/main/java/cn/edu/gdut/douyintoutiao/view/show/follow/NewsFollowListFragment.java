package cn.edu.gdut.douyintoutiao.view.show.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import cn.edu.gdut.douyintoutiao.databinding.NewsFollowListFragmentBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.follow.adapter.FollowNewsAdapter;
import cn.edu.gdut.douyintoutiao.view.show.follow.viewmodel.NewsFollowListViewModel;

/**
 * @author cypang
 */
public class NewsFollowListFragment extends Fragment {

    private NewsFollowListViewModel mViewModel;
    private FollowNewsAdapter adapter;
    private NewsFollowListFragmentBinding binding;
    private String tag = "";

    public NewsFollowListFragment(String tag) {
        this.tag = tag;
    }

    public NewsFollowListFragment() {
    }

    public static NewsFollowListFragment newInstance() {
        return new NewsFollowListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NewsFollowListFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsFollowListViewModel.class);
        adapter = new FollowNewsAdapter(getActivity());
        binding.recyclerViewTagNews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewTagNews.setAdapter(adapter);
        mViewModel.getAllFollowLive(tag).observe(getViewLifecycleOwner(), new Observer<List<MyNews>>() {
            @Override
            public void onChanged(List<MyNews> news) {
                adapter.setNewsList(news);
                adapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.getAllFollowLive(tag);
            }
        });
    }

}