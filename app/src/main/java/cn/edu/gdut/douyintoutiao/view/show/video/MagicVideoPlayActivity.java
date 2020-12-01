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

/**
 * @author hayring
 * @date 2020.11.26 16:00
 */
public class MagicVideoPlayActivity extends VideoPlayActivity {


    public static final String UP = "up";

    public static final String LEFT = "left";

    public static final String RIGHT = "right";

    public static final String SHP_KEY = "CROSS_SCROLL_TAB";


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



    int lastScrollDirection = _DOWN;

    private static final int _DOWN = 0;
    private static final int _UP = 1;
    private static final int _LEFT = 2;
    private static final int _RIGHT = 3;

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
                fragment = new MagicVideoPlayEmptySpaceFragment();
                ((MagicVideoPlayEmptySpaceFragment)fragment).setIndex(position);
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
                    activity.lastScrollDirection = _UP;
                    //触发上滑操作
                    activity.scrollToDirection(_UP);
                } else {
                    activity.lastScrollDirection = _LEFT;
                    //触发左滑操作
                    activity.scrollToDirection(_LEFT);
                }

            } else if (prePosition == 2) {
                if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                    activity.lastScrollDirection = _DOWN;
                    //触发下滑操作
                    activity.scrollToDirection(_DOWN);
                } else {
                    activity.lastScrollDirection = _RIGHT;
                    //触发右滑操作
                    activity.scrollToDirection(_RIGHT);
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

    /**
     * 移除视频，并显示动画（举报，不感兴趣）
     */
    @Override
    public void removeVideo() {
        switch (lastScrollDirection) {
            case _UP:{
                ((VerticalTriplePageFragment)fragments[1]).getVerticalViewPager().setCurrentItem(0,true);
            }break;
            case _DOWN:{
                ((VerticalTriplePageFragment)fragments[1]).getVerticalViewPager().setCurrentItem(2,true);
            }break;
            case _LEFT:{
                horizontalViewPager.setCurrentItem(0,true);
            }break;
            case _RIGHT:{
                horizontalViewPager.setCurrentItem(2,true);
            }
        }

    }


    public MagicVideoViewModel getMagicVideoPlayViewModel() {
        return magicVideoPlayViewModel;
    }


    private void scrollToDirection(int direction) {
        viewBinding.videoViewPager.setCurrentItem(1,false);
        switch (direction) {
            case _UP:{
                setCurrentNews(getMagicVideoPlayViewModel().upNewses.pop());
                if (getMagicVideoPlayViewModel().upNewses.size() == 1) {
                    getMagicVideoPlayViewModel().getUpVideo();
                }
            }break;
            case _DOWN:{
                setCurrentNews(getMagicVideoPlayViewModel().downNewses.pop());
                if (getMagicVideoPlayViewModel().downNewses.size() == 1) {
                    getMagicVideoPlayViewModel().getDownVideo();
                }
            }break;
            case _LEFT:{
                setCurrentNews(getMagicVideoPlayViewModel().leftNewses.pop());
                if (getMagicVideoPlayViewModel().leftNewses.size() == 1) {
                    getMagicVideoPlayViewModel().getLeftVideo();
                }
            }break;
            case _RIGHT:{
                setCurrentNews(getMagicVideoPlayViewModel().rightNewses.pop());
                if (getMagicVideoPlayViewModel().rightNewses.size() == 1) {
                    getMagicVideoPlayViewModel().getRightVideo();
                }
            }
        }
    }
}

