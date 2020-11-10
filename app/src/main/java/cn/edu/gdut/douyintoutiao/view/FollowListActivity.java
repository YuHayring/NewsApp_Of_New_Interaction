package cn.edu.gdut.douyintoutiao.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.adapter.FollowListAdapter;

public class FollowListActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<String> tab_title_list = new ArrayList<>();//存放标签页标题
    private ArrayList<Fragment> fragment_list = new ArrayList<>();//存放ViewPager容器下的Fragment
    private Fragment firstFragment, secondFragment, thirdFragment;//三个fragment
    private FollowListAdapter adapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);

//        BottomNavigationView bottomNavigationView = findViewById(R.id.followBottomNavView);
//        NavController navController = Navigation.findNavController(this,R.id.followListFragment);
//        AppBarConfiguration configuration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()  ).build();
//        NavigationUI.setupActionBarWithNavController(this,navController,configuration);
//        NavigationUI.setupWithNavController(bottomNavigationView,navController);


            //找到tablayout  和   viewpager
            viewPager = findViewById(R.id.followList_viewPager);
            tabLayout = findViewById(R.id.followList_tabLayout);

            //标题
            tab_title_list.add("事件");
            tab_title_list.add("用户");


            //两个个fragment对象
            firstFragment = new FollowTagsListFragment();
            secondFragment = new FollowAuthorFragment();

            //往fragment列表添加内容
            fragment_list.add(firstFragment);
            fragment_list.add(secondFragment);
            // fragment_list.add(thirdFragment);

            adapter = new FollowListAdapter(getSupportFragmentManager(), tab_title_list, fragment_list);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);//让TabLayout和Viewpager 联系起来，能一起滑动

        }
    }

