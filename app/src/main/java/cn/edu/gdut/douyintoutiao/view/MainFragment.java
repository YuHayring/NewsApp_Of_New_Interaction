package cn.edu.gdut.douyintoutiao.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

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


    private int pages = 3;

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
        newsViewPager.setAdapter(pagerAdapter);

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



}
