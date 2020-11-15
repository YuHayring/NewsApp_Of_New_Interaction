package cn.edu.gdut.douyintoutiao.view.user.follow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
//import cn.edu.gdut.douyintoutiao.databinding.FragmentFollowAuthorListBinding;
import cn.edu.gdut.douyintoutiao.databinding.FragmentFollowAuthorListBinding;
import cn.edu.gdut.douyintoutiao.databinding.FragmentNewsListBinding;
import cn.edu.gdut.douyintoutiao.entity.Follow;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.view.show.text.adapter.NewsSAdapter;
import cn.edu.gdut.douyintoutiao.view.show.text.viewmodel.NewsViewModel;
import cn.edu.gdut.douyintoutiao.view.user.follow.adapter.FollowAuthorListAdapter;
import cn.edu.gdut.douyintoutiao.view.user.follow.adapter.FollowListAdapter;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorViewModel;


public class FollowAuthorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    private View view;//定义view用来设置fragment的layout
//    public RecyclerView mCollectRecyclerView;//定义RecyclerView
//    //定义以Follow实体类为对象的数据集合
//    private List<Follow> data = new ArrayList<Follow>();
//    //自定义recyclerveiw的适配器
//    private FollowAuthorListAdapter mRecyclerAdapter;
//    FollowAuthorViewModel authorViewModel;

    private FollowAuthorViewModel followAuthorViewModel;
    private FollowAuthorListAdapter followListAdapter;
    private FragmentFollowAuthorListBinding fragmentFollowAuthorListBinding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FollowAuthorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowListFragment.        mRecyclerAdapter = new FollowAuthorListAdapter()ListAdapter();
     */
    // TODO: Rename and change types and number of parameters
    public static FollowAuthorFragment newInstance(String param1, String param2) {
        FollowAuthorFragment fragment = new FollowAuthorFragment();
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
    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentFollowAuthorListBinding = FragmentFollowAuthorListBinding.inflate(inflater);
        return fragmentFollowAuthorListBinding.getRoot();

    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        followListAdapter = new FollowAuthorListAdapter(getActivity());
        followAuthorViewModel = new ViewModelProvider(this).get(FollowAuthorViewModel.class);
        fragmentFollowAuthorListBinding.followAuthorListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentFollowAuthorListBinding.followAuthorListRecyclerView.setAdapter(followListAdapter);
        followAuthorViewModel.getFollowList().observe(getViewLifecycleOwner(), new Observer<List< Follow >>() {
            @Override
            public void onChanged(List<Follow> lists    ) {
                followListAdapter.setFollows(lists);
                followListAdapter.notifyDataSetChanged();
                fragmentFollowAuthorListBinding.FollowListRefresh.setRefreshing(false);

            }
        });
        fragmentFollowAuthorListBinding.FollowListRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                followAuthorViewModel.getFollowList();
            }
        });
    }

    
}