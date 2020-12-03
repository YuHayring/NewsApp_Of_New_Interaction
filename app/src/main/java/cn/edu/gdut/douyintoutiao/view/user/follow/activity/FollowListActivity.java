package cn.edu.gdut.douyintoutiao.view.user.follow.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.view.user.follow.FollowAuthorListFragment;
import cn.edu.gdut.douyintoutiao.view.user.follow.FollowTagsListFragment;
import cn.edu.gdut.douyintoutiao.view.user.follow.adapter.FollowListAdapter;
import cn.edu.gdut.douyintoutiao.view.user.follow.viewmodel.FollowAuthorViewModel;


/**
 * @author : DengJL
 * @description ： 用户页面关注按钮跳转的act
 */
public class FollowListActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<String> tab_title_list = new ArrayList<>();//存放标签页标题
    private ArrayList<Fragment> fragment_list = new ArrayList<>();//存放ViewPager容器下的Fragment
    private Fragment firstFragment, secondFragment;
    private FollowListAdapter adapter;//适配器
    FollowAuthorViewModel viewModel;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);


            //找到tablayout  和   viewpager
            viewPager = findViewById(R.id.followList_viewPager);
            tabLayout = findViewById(R.id.followList_tabLayout);

            //标题
            tab_title_list.add("事件");
           tab_title_list.add("用户");


            //两个fragment对象
            firstFragment = new FollowTagsListFragment();
            secondFragment = new FollowAuthorListFragment();
       // secondFragment = new Fragment_Follow_Author_Start();

            //往fragment列表添加内容
            fragment_list.add(firstFragment);
            fragment_list.add(secondFragment);

            getUserInfo();

            adapter = new FollowListAdapter(getSupportFragmentManager(), tab_title_list, fragment_list);
            viewPager.setAdapter(adapter);


            tabLayout.setupWithViewPager(viewPager);//让TabLayout和Viewpager 联系起来，能一起滑动


            viewModel = new ViewModelProvider(this).get(FollowAuthorViewModel.class);


        }

    //拿到userId
    private  void getUserInfo(){
        Intent in = getIntent();
        userId = in.getStringExtra("userId");
    }

    public String getUserId() {
        return userId;
    }

}

