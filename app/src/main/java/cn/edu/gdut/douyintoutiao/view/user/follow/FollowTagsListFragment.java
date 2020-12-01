package cn.edu.gdut.douyintoutiao.view.user.follow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentFollowTagsListBinding;
import cn.edu.gdut.douyintoutiao.entity.FollowNews;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsActivity;
import cn.edu.gdut.douyintoutiao.view.show.video.singleplayer.SingleVideoPlayActivity;
import cn.edu.gdut.douyintoutiao.view.user.follow.activity.FollowListActivity;
import cn.edu.gdut.douyintoutiao.view.user.follow.adapter.FollowTagsListAdapter;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowTagsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowTagsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowTagsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;//定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;//定义RecyclerView

    private String userId;
    //自定义recyclerveiw的适配器
    private FollowTagsListAdapter followTagsListAdapter;
    //VW
    private FollowTagsViewModel followTagsViewModel;
    private FragmentFollowTagsListBinding fragmentFollowTagsListBinding;
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FollowTagsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowTagsListFragment newInstance(String param1, String param2) {
        FollowTagsListFragment fragment = new FollowTagsListFragment();
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
        fragmentFollowTagsListBinding =  FragmentFollowTagsListBinding.inflate(inflater);
        return fragmentFollowTagsListBinding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        //定义adapter VW Rv
        followTagsListAdapter = new FollowTagsListAdapter(getActivity());
        followTagsViewModel = new ViewModelProvider(this).get(FollowTagsViewModel.class);
        fragmentFollowTagsListBinding.followTagsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentFollowTagsListBinding.followTagsListRecyclerView.setAdapter(followTagsListAdapter);
        
        //LD 观察，刷新data
        followTagsViewModel.getFollowTagsList(userId).observe(getViewLifecycleOwner(), new Observer< List< FollowNews > >() {
            @Override
            public void onChanged(List< FollowNews > followNewsList) {
                followTagsListAdapter.setDataList(followNewsList);
                followTagsListAdapter.notifyDataSetChanged();

                //下拉刷新控件SwipeRefreshLayout
                fragmentFollowTagsListBinding.FollowTagsListRefresh.setRefreshing(false);
            }
        });

        //下拉刷新控件SwipeRefreshLayout
        fragmentFollowTagsListBinding.FollowTagsListRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                followTagsViewModel.getFollowTagsList(userId);
            }
        });

        /**
         * 描述：item点击事件
         */
        followTagsListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onUnFollowButtonClick(int position) {

                //补充取消关注警告窗口
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("取消关注?")
                        .setMessage("确定要取消关注"+followTagsListAdapter.getDataList().get(position).getFollowNews().get(0).getNewsName()+"吗")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"取消关注事件"+followTagsListAdapter.getDataList().get(position).getFollowNews().get(0).getNewsName(),Toast.LENGTH_SHORT).show();
                                followTagsViewModel.deleteFollowTagsByFollowNewsId(followTagsListAdapter.getDataList().get(position).getFollowNewsId());
                                followTagsViewModel.getFollowTagsList(userId);
                                followTagsListAdapter.notifyDataSetChanged();
                            }
                        })
                        .create().show();
//
//                Toast.makeText(getContext(),"取消关注事件"+followTagsListAdapter.getDataList().get(position).getFollowNews().get(0).getNewsName(),Toast.LENGTH_SHORT).show();
//                followTagsViewModel.deleteFollowTagsByFollowNewsId(followTagsListAdapter.getDataList().get(position).getFollowNewsId());
            }

            @Override
            public void onItemViewClick(int position) {
             //   Toast.makeText(getContext(),"点击了"+followTagsListAdapter.getDataList().get(position).getFollowNews().get(0).getNewsName(),Toast.LENGTH_SHORT).show();
                FollowNews thisFollowNews = followTagsListAdapter.getDataList().get(position);
                if(thisFollowNews.getFollowNews().get(0).getType() == 0){
                    //跳转文字资讯
                    startFollowTagsDetailsActivityToTextFragment(thisFollowNews);}
                else if(thisFollowNews.getFollowNews().get(0).getType() == 1){
                    //跳转视频资讯
                    startFollowTagsDetailsActivityToSingleVideoPlayActivity(thisFollowNews);
                }
            }

        });


    }
        //文字资讯
    private void startFollowTagsDetailsActivityToTextFragment (FollowNews data){
        Intent intent = new Intent(getActivity(), NewsActivity.class);
        intent.putExtra("uri", data.getFollowNews().get(0).getNewsDetailUrl());
        intent.putExtra("newsId", data.getFollowNews().get(0).get_id());
        intent.putExtra("tag", data.getFollowNews().get(0).getTag());
        intent.putExtra("authorId",data.getFollowNews().get(0).getAuthor().get(0).getUserId());
        getActivity().startActivity(intent);
    }
    //视频资讯
    private void startFollowTagsDetailsActivityToSingleVideoPlayActivity(FollowNews data){
        Intent intent = new Intent(getContext(), SingleVideoPlayActivity.class);
        intent.putExtra("news", data.getFollowNews().get(0));
        ((Activity)getContext()).startActivityForResult(intent, 1);
    }


    /**
     * 定义RecyclerView选项单击事件的回调接口
     */
    public interface OnItemClickListener{

        //取消关注
        void onUnFollowButtonClick(int position);
        //跳转被关注资讯详细信息页面
        void onItemViewClick(int position);
        // void onItemLongClick(View view);
        //

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userId = ((FollowListActivity)context).getUserId();
//        System.out.println("onAttach:"+((ActivityFollowAuthorDetails)context).getUserId());
    }



}