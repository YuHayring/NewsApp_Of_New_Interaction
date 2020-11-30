package cn.edu.gdut.douyintoutiao.view.show.video;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;

import java.lang.reflect.Constructor;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.tmp.ShowIndexFragment;

/**
 * @author hayring
 * @date 2020.11.26 16:00
 */
public class MagicVideoPlayActivity extends VideoPlayActivity {


    ViewPager2 horizontalViewPager;
//
//    VideoPlayerFragment[] fragments = new VideoPlayerFragment[4];
    Fragment[] fragments = new Fragment[6];


    /**
     * 核心 Fragment
     */
    VideoPlayFragment videoPlayFragment;

    MagicVideoViewModel magicVideoPlayViewModel;

    /**
     * 滑动方向监听器
     */
    ResetPositionCallBack horizontalCallBack;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super 中已经设置了 setContentView
        super.onCreate(savedInstanceState);

        horizontalViewPager = viewBinding.videoViewPager;

        horizontalViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        //设置 各种参数
        horizontalViewPager.setAdapter(new HorizontalFragmentAdapter(this));
        horizontalViewPager.setCurrentItem(1,false);

        //核心视频播放 fragment
        videoPlayFragment = new VideoPlayFragment();
        videoPlayFragment.setContext(this);
        videoPlayFragment.setRecycleUse(true);


        magicVideoPlayViewModel = new ViewModelProvider(this,videoViewModelFactory).get(MagicVideoViewModel.class);





        horizontalCallBack = new ResetPositionCallBack(horizontalViewPager, this);


        //滑动方向监听器
        horizontalViewPager.registerOnPageChangeCallback(horizontalCallBack);



    }


    /**
     * 竖直 适配器
     */
    private class HorizontalFragmentAdapter extends FragmentStateAdapter {

        public HorizontalFragmentAdapter(@NonNull FragmentActivity activity) {
            super(activity);
        }



        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            if (position == 1) {
                fragment = new VerticalTriplePageFragment();
                ((VerticalTriplePageFragment)fragment).setActivity(MagicVideoPlayActivity.this);
                ((VerticalTriplePageFragment)fragment).setVideoPlayFragment(videoPlayFragment);
                ((VerticalTriplePageFragment)fragment).setMagicVideoPlayViewModel(magicVideoPlayViewModel);
            } else {
                fragment = new ShowIndexFragment();
                ((ShowIndexFragment)fragment).setIndex(position);
            }
            fragments[position] = fragment;
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }


    /**
     * 滑动重置中心 callback
     */
    static class ResetPositionCallBack extends ViewPager2.OnPageChangeCallback {

        int currentPosition = 1;

        ViewPager2 viewPager2;

        MagicVideoPlayActivity activity;

        public ResetPositionCallBack(ViewPager2 viewPager2, MagicVideoPlayActivity activity) {
            this.viewPager2 = viewPager2;
            this.activity = activity;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            //        若viewpager滑动未停止，直接返回
            if (state != ViewPager2.SCROLL_STATE_IDLE) return;

            int prePosition = currentPosition;

            viewPager2.setCurrentItem(1,false);
            //        若当前为第一张，设置页面为倒数第二张
            if (prePosition == 0) {
                if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                    //触发上滑操作
                    activity.setCurrentNews(activity.getMagicVideoPlayViewModel().upNewses.pop());
                    if (activity.getMagicVideoPlayViewModel().upNewses.size() == 1) {
                        activity.getMagicVideoPlayViewModel().getUpVideo();
                    }
                } else {
                    //触发左滑操作
                    activity.setCurrentNews(activity.getMagicVideoPlayViewModel().leftNewses.pop());
                    if (activity.getMagicVideoPlayViewModel().leftNewses.size() == 1) {
                        activity.getMagicVideoPlayViewModel().getLeftVideo();
                    }
                }

            } else if (prePosition == 2) {
                if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                    //触发下滑操作
                    activity.setCurrentNews(activity.getMagicVideoPlayViewModel().downNewses.pop());
                    if (activity.getMagicVideoPlayViewModel().downNewses.size() == 1) {
                        activity.getMagicVideoPlayViewModel().getDownVideo();
                    }
                } else {
                    //触发右滑操作
                    activity.setCurrentNews(activity.getMagicVideoPlayViewModel().rightNewses.pop());
                    if (activity.getMagicVideoPlayViewModel().rightNewses.size() == 1) {
                        activity.getMagicVideoPlayViewModel().getRightVideo();
                    }
                }
            }
        }
    }


    Fragment[] getFragments() {
        return fragments;
    }



    ViewModelProvider.Factory videoViewModelFactory = new ViewModelProvider.Factory() {

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                Constructor constructor = modelClass.getConstructor(Activity.class);
                return (T) constructor.newInstance(MagicVideoPlayActivity.this);
            } catch (Exception e) {
                IllegalArgumentException ile = new IllegalArgumentException("" + modelClass + "is not" + VideoViewModel.class);
                ile.initCause(e);
                throw ile;
            }

        }
    };


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //数据初始化
        magicVideoPlayViewModel.getUpVideo();
        magicVideoPlayViewModel.getDownVideo();
        magicVideoPlayViewModel.getLeftVideo();
        magicVideoPlayViewModel.getRightVideo();
    }

//    /**
//     * 当前资讯更新时
//     */
//    protected Observer<MyNews> newsInfoObserver = new Observer<MyNews>() {
//        @Override
//        public void onChanged(MyNews news) {
//            currentNews = news;
//            viewBinding.videoTitleTextView.setText(news.getNewsName());
//            viewBinding.videoDescriptionTextView.setText(news.getNewsAbstract());
//        }
//    };


    @Override
    public void setCurrentNews(MyNews currentNews) {
        super.setCurrentNews(currentNews);
        videoPlayFragment.setMyNews(currentNews);
    }


    public MagicVideoViewModel getMagicVideoPlayViewModel() {
        return magicVideoPlayViewModel;
    }
}

