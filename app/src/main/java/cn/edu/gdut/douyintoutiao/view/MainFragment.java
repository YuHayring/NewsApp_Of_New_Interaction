package cn.edu.gdut.douyintoutiao.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.FragmentMainBinding;
import cn.edu.gdut.douyintoutiao.view.show.follow.AuthorNewsFragment;
import cn.edu.gdut.douyintoutiao.view.show.follow.NewsFollowListFragment;
import cn.edu.gdut.douyintoutiao.view.show.search.SearchMainActivity;
import cn.edu.gdut.douyintoutiao.view.show.text.NewsListFragment;
import cn.edu.gdut.douyintoutiao.view.show.video.MagicVideoPlayActivity;

/**
 * @author hayring
 * @date 2020/11/7 21:43
 */
public class MainFragment extends Fragment {

    Context context;


    ViewPager2 newsViewPager;

    /**
     * 所有的 Tab
     */
    String[] tabs;

    /**
     * 用户关注的 tab
     */
    List<String> followTabs;

    NewsFollowListFragment tabFragment;


    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    private TabLayout newsNavigationTab;

    private TabLayout.TabView followTabView;

    private MaterialDialog tabSelectDialog;

    private TabViewModel mainViewModel;

    private final int pages = 3;
    private final ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            TabLayout.Tab tab = newsNavigationTab.getTabAt(position);
            newsNavigationTab.selectTab(tab);
        }
    };

    private TabLayoutMediator mediator;

    final String[] tabTitle = {"推荐", "关注", "足球"};
    private FragmentMainBinding binding;


    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return NewsListFragment.newInstance("","");
            } else if (position == 1) {
                return AuthorNewsFragment.newInstance("","");
            } else if (position == 2) {
                return (tabFragment = NewsFollowListFragment.newInstance(tabTitle[position]));
            }
            throw new IllegalArgumentException("Illegal Position "+ position);
        }

        @Override
        public int getItemCount() {
            return pages;
        }
    }

    /***
     * 生命周期加载方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);
        newsViewPager = binding.getRoot().findViewById(R.id.fragment_main_news_view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter((MainActivity) context);

        newsViewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        newsViewPager.setAdapter(pagerAdapter);

        newsNavigationTab = binding.getRoot().findViewById(R.id.news_navigation);

        mainViewModel = new ViewModelProvider(this).get(TabViewModel.class);
        mainViewModel.getTabs().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                tabs = strings;
                followTabs.clear();
                SharedPreferences shp = requireActivity().getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
                int followTabsInt = shp.getInt("followTabs", 0);
                int mark = 1;
                for (int i = 0; i < strings.length; i++) {
                    if ((mark & followTabsInt) == mark) {
                        followTabs.add(tabs[i]);
                    }
                    mark = mark << 1;
                }
                tabSelectDialog = new MaterialDialog.Builder(context).title(getString(R.string.main_fragment_select_your_tab))
                        .items(followTabs).itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {//0 表示第一个选中 -1 不选
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                tabTitle[2] = followTabs.get(which);
                                newsNavigationTab.getTabAt(2).setText(tabTitle[2]);
                                tabFragment.changeTag(tabTitle[2]);
                                return true;
                            }
                        }).build();
            }
        });


        followTabs = new ArrayList<>();







        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchMainActivity.class);
                startActivity(intent);
            }
        });


        binding.exchangeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MagicVideoPlayActivity.class);
            startActivity(intent);
        });

        mediator = new TabLayoutMediator(newsNavigationTab, newsViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //这里可以自定义TabView
                tab.setText(tabTitle[position]);
            }
        });
        //要执行这一句才是真正将两者绑定起来
        mediator.attach();

        followTabView = newsNavigationTab.getTabAt(2).view;
        followTabView.setOnLongClickListener(v -> {
            tabSelectDialog.show();
            //不触发 onclicklistener
            return true;
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        mediator.detach();
        newsViewPager.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        mainViewModel.getTabsFromNet();
    }
}













