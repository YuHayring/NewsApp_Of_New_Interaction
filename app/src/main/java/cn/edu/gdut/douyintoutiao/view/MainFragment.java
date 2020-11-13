package cn.edu.gdut.douyintoutiao.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.tmp.ViewPagerTestFragment;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsListFragment;

/**
 * @author hayring
 * @date 2020/11/7 21:43
 */
public class MainFragment extends Fragment {

    Context context;


    ViewPager2 newsViewPager;


    public MainFragment(Context context) {
        this.context = context;
    }

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    private TabLayout newsNavigationTab;


    private int pages = 3;

    private TabLayoutMediator mediator;

    final String[] tabs = {"页面1","页面2","页面3"};

    /***
     * 生命周期加载方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        newsViewPager = view.findViewById(R.id.fragment_main_news_view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter((MainActivity)context);

        newsViewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        newsViewPager.setAdapter(pagerAdapter);

        newsNavigationTab = view.findViewById(R.id.news_navigation);


        mediator = new TabLayoutMediator(newsNavigationTab, newsViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //这里可以自定义TabView
                tab.setText(tabs[position]);
            }
        });
        //要执行这一句才是真正将两者绑定起来
        mediator.attach();

        return view;
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new NewsListFragment();
            } else if (position < 3) {
                return new ViewPagerTestFragment(position);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public int getItemCount() {
            return pages;
        }
    }

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            TabLayout.Tab tab = newsNavigationTab.getTabAt(position);
            newsNavigationTab.selectTab(tab);
        }
    };

    @Override
    public void onDestroy() {
        mediator.detach();
        newsViewPager.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }



}













