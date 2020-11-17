package cn.edu.gdut.douyintoutiao.view.show.video;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.News;

/**
 * @author hayring
 * @date 11/17/20 3:22 PM
 */
public class VideoStateAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;
    private List<News> newses;
    private Context context;



    public VideoStateAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments, List<News> newses) {
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
        fragmentIdSet.add(Long.valueOf(newses.get(position).getNewsId()));
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

    public void addAndNotify(News news) {
        int start = newses.size();
        newses.add(news);
        fragments.add(new VideoPlayerFragment(context,news));
        notifyDataSetChanged();
    }

    public void addAllAndNotify(List<News> newses) {
        int start = newses.size();
        this.newses.addAll(newses);
        for (News news : newses) {
            fragments.add(new VideoPlayerFragment(context,news));
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(newses.get(position).getNewsId());
    }


    @Override
    public boolean containsItem(long itemId) {
        return fragmentIdSet.contains(itemId);
    }

}