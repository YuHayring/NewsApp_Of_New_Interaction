package cn.edu.gdut.douyintoutiao.view.show.video;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;

/**
 * @author hayring
 * @date 11/17/20 3:22 PM
 */
public class VideoStateAdapter extends FragmentStateAdapter {


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private List<Fragment> fragments;
    private List<MyNews> newses;
    private Context context;



    public VideoStateAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments, List<MyNews> newses) {
        super(fragmentActivity);
        context = fragmentActivity;
        this.fragments = fragments;
        this.newses = newses;
    }

    public VideoStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        try {
            fragmentIdSet.add(sdf.parse(newses.get(position).getCreatedAt()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return fragments.get(position);
    }

    /**
     * 存在集合
     */
    private HashSet<Long> fragmentIdSet = new HashSet<>();

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public void addAndNotify(MyNews news) {
        int start = newses.size();
        newses.add(news);

        VideoPlayerFragment fragment = new VideoPlayerFragment();
        fragment.setContext(context);
        fragment.setMyNews(news);
        fragments.add(fragment);
        notifyItemInserted(start);
    }

    public void addAllAndNotify(List<MyNews> newses) {
        int start = newses.size();
        this.newses.addAll(newses);
        for (MyNews news : newses) {
            VideoPlayerFragment fragment = new VideoPlayerFragment();
            fragment.setContext(context);
            fragment.setMyNews(news);
            fragments.add(fragment);
        }
        notifyItemInserted(start);
    }

    @Override
    public long getItemId(int position) {
        try {
            return sdf.parse(newses.get(position).getCreatedAt()).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean containsItem(long itemId) {
        return fragmentIdSet.contains(itemId);
    }



}