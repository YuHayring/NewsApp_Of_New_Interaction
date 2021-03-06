package cn.edu.gdut.douyintoutiao.view.show.text;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;

import cn.edu.gdut.douyintoutiao.databinding.FragmentNewsListBinding;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.text.adapter.NewsAdapter;
import cn.edu.gdut.douyintoutiao.view.show.text.dataSource.NewsDataSource;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsViewModel;

/**
 * @author cypang
 * @date 2020年11月12日19:16:07
 */
public class NewsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NewsViewModel viewModel;
    private NewsAdapter adapter;
    private FragmentNewsListBinding binding;

    public NewsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsListFragment.
     */
    public static NewsListFragment newInstance(String param1, String param2) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        binding.recyclerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(requireContext(), viewModel);
        adapter.showEmptyView(true);
        binding.recyclerViewNews.setAdapter(adapter);
        viewModel.newsPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<MyNews>>() {
            @Override
            public void onChanged(PagedList<MyNews> myNews) {
                adapter.submitList(myNews);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        viewModel.netWorkStatus.observe(getViewLifecycleOwner(), new Observer<NewsDataSource.NetWorkStatus>() {
            @Override
            public void onChanged(NewsDataSource.NetWorkStatus netWorkStatus) {
                Log.d("news", "onChanged: " + netWorkStatus);
                adapter.setNetWorkStatus(netWorkStatus);
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            //清除视频下标
            adapter.cleanVideoIndex();
            viewModel.reset();
        });
    }

}