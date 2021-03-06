package cn.edu.gdut.douyintoutiao.view.show.follow;

import android.content.Context;
import android.content.SharedPreferences;
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

import cn.edu.gdut.douyintoutiao.databinding.FragmentAuthorNewsBinding;
import cn.edu.gdut.douyintoutiao.view.show.text.adapter.OtherNewsAdapter;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsViewModel;

/**
 * @author cypang
 * @date 2020年11月28日 10:15:01
 */
public class AuthorNewsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private NewsViewModel mViewModel;
    private OtherNewsAdapter adapter;
    private FragmentAuthorNewsBinding binding;
    private String userId;

    private String mParam1;
    private String mParam2;

    public AuthorNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthorNewsFragment.
     */
    public static AuthorNewsFragment newInstance(String param1, String param2) {
        AuthorNewsFragment fragment = new AuthorNewsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAuthorNewsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences shp = requireActivity().getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        String userId = shp.getString("userId", "noContent");
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        adapter = new OtherNewsAdapter(requireContext());
        adapter.showEmptyView(true);
        binding.authorNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.authorNewsRecyclerView.setAdapter(adapter);
        SwipeRefreshLayout.OnRefreshListener listener = () -> mViewModel.getAllAuthorNews(userId);
        binding.swipeRefreshLayout.post(() -> binding.swipeRefreshLayout.setRefreshing(true));
        listener.onRefresh();
        mViewModel.getAllAuthorNews(userId).observe(getViewLifecycleOwner(), news -> {
            adapter.setNewsList(news);
            adapter.notifyDataSetChanged();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
        binding.swipeRefreshLayout.setOnRefreshListener(listener);
    }
}