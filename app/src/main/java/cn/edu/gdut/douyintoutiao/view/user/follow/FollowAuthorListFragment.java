package cn.edu.gdut.douyintoutiao.view.user.follow;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

//import cn.edu.gdut.douyintoutiao.databinding.FragmentFollowAuthorListBinding;
import cn.edu.gdut.douyintoutiao.databinding.FragmentFollowAuthorListBinding;
import cn.edu.gdut.douyintoutiao.entity.Follow;

import cn.edu.gdut.douyintoutiao.view.user.follow.activity.Activity_Follow_Author;
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.ActivityFollowAuthorDetails;
import cn.edu.gdut.douyintoutiao.view.user.follow.adapter.FollowAuthorListAdapter;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorViewModel;


public class FollowAuthorListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private FollowAuthorViewModel followAuthorViewModel;
    private FollowAuthorListAdapter followListAdapter;
    private FragmentFollowAuthorListBinding fragmentFollowAuthorListBinding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FollowAuthorListFragment() {
        // Required empty public constructor
    }

    public FollowAuthorListFragment(Activity_Follow_Author activity_follow_author) {
    }

    public static FollowAuthorListFragment newInstance(String param1, String param2) {
        FollowAuthorListFragment fragment = new FollowAuthorListFragment();
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


        //定义binding
        fragmentFollowAuthorListBinding = FragmentFollowAuthorListBinding.inflate(inflater);
        return fragmentFollowAuthorListBinding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //定义adapater,VM,RV
        followListAdapter = new FollowAuthorListAdapter(getActivity());
        followAuthorViewModel = new ViewModelProvider(this).get(FollowAuthorViewModel.class);
        fragmentFollowAuthorListBinding.followAuthorListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentFollowAuthorListBinding.followAuthorListRecyclerView.setAdapter(followListAdapter);

        //LD 观察,刷新data
        followAuthorViewModel.getFollowList().observe(getViewLifecycleOwner(), new Observer<List< Follow >>() {
            @Override
            public void onChanged(List<Follow> lists    ) {
                followListAdapter.setFollows(lists);
                followListAdapter.notifyDataSetChanged();
                //下拉刷新控件SwipeRefreshLayout
                fragmentFollowAuthorListBinding.FollowListRefresh.setRefreshing(false);

            }
        });
        //下拉刷新控件SwipeRefreshLayout
        fragmentFollowAuthorListBinding.FollowListRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                followAuthorViewModel.getFollowList();
            }
        });

        /**
         * 描述：item点击事件
         */
        followListAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onUnFollowButtonClick(int position) {
                //取消关注警告窗口待补充
                Toast.makeText(getContext(),"取消关注"+followListAdapter.getFollows().get(position).getAuthor().get(0).getUserName(), Toast.LENGTH_SHORT).show();
                 followAuthorViewModel.deleteFollowListByFollowId(followListAdapter.getFollows().get(position).getFollowId());
            }

            @Override
            public void onItemViewClick(int position) {
                //启动被关注者activity
                startActivityAuthorDetails(followListAdapter.getFollows().get(position).getAuthor().get(0).getUserId());
            }

            private void startActivityAuthorDetails(String id){
                Intent startIntent = new Intent(getActivity(),
                        ActivityFollowAuthorDetails.class);
                //传递当前item的数据信息
                startIntent.putExtra("userId",id);
                startActivity(startIntent);
            }
        });

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 定义RecyclerView选项单击事件的回调接口
     */
    public interface OnItemClickListener{

        //取消关注
        void onUnFollowButtonClick(int position);
        //跳转被关注者详细信息页面
        void onItemViewClick(int position);
        // void onItemLongClick(View view);
        //

    }
}