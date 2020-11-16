package cn.edu.gdut.douyintoutiao.view.user.follow.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cn.edu.gdut.douyintoutiao.R;
//import cn.edu.gdut.douyintoutiao.databinding.ActivityFirstBinding;
//import cn.edu.gdut.douyintoutiao.databinding.ActivityFollowAuthorBinding;
//import cn.edu.gdut.douyintoutiao.databinding.ActivityNewsBinding;
import cn.edu.gdut.douyintoutiao.databinding.ActivityFollowAuthorBinding;
import cn.edu.gdut.douyintoutiao.view.MainActivity;
import cn.edu.gdut.douyintoutiao.view.MainFragment;
import cn.edu.gdut.douyintoutiao.view.user.follow.FollowAuthorFragment;
import cn.edu.gdut.douyintoutiao.view.user.follow.FollowTagsListFragment;
import cn.edu.gdut.douyintoutiao.view.user.follow.adapter.FollowViewPagerAdapter;
import cn.edu.gdut.douyintoutiao.view.user.main.UserMainFragment;

/**
 * @author : DengJL
 * @description ：测试用，别理
 */
//测试用
public class Activity_Follow_Author extends AppCompatActivity {


    ActivityFollowAuthorBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__follow__author);

        binding.followListViewpager2.setAdapter(new FollowViewPagerAdapter(this));

        new TabLayoutMediator(binding.followTestTablayout, binding.followListViewpager2, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                if(position == 0){
//                    tab.setText("事件");
//                }
//                else tab.setText("用户");
//            }
                tab.setText("hello"+position);}
        }).attach();
    }


}

