package cn.edu.gdut.douyintoutiao.view.user.follow;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.News;

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
    //定义以News实体类为对象的数据集合
    private List<News> tagsList = new ArrayList<News>();
    //自定义recyclerveiw的适配器
    private FollowTagsListAdapter mRecyclerAdapter;

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

        //获取fragment的layout
        view = inflater.inflate(R.layout.fragment_follow_tags_list, container, false);
        //对recycleview进行配置
        initRecyclerView();
        //模拟数据
        initData();
        return view;
        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_follow_tags_list, container, false);
    }

    private void initData() {
        for (int i=1;i<=20;i++){
            News goodsEntity=new News();
            goodsEntity.setNewsName("模拟事件"+i);

            tagsList.add(goodsEntity);
        }
    }

    /**
     * TODO 对recycleview进行配置
     */
    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView = (RecyclerView) view.findViewById(R.id.follow_tags_list_recycler_view);
        //创建adapter
        mRecyclerAdapter = new FollowTagsListAdapter(tagsList);
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(mRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
//        mCollectRecyclerAdapter.setOnItemClickListener(new FollowTagsListAdapter.{
//            @Override
//            public void OnItemClick(View view, GoodsEntity data) {
//                //此处进行监听事件的业务处理
//                Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}