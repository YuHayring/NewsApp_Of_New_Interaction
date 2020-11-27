package cn.edu.gdut.douyintoutiao.view.show.video;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;

import cn.edu.gdut.douyintoutiao.databinding.ActivityMagicVideoPlayBinding;
import cn.edu.gdut.douyintoutiao.tmp.ShowIndexFragment;
import cn.edu.gdut.douyintoutiao.view.FullScreenActivity;
import es.dmoral.toasty.Toasty;

/**
 * @author hayring
 * @date 2020.11.26 16:00
 */
public class MagicVideoPlayActivity extends FullScreenActivity {



    ActivityMagicVideoPlayBinding videoPlayBinding;




    ViewPager2 horizontalViewPager;
//
//    VideoPlayerFragment[] fragments = new VideoPlayerFragment[4];
    Fragment[] fragments = new Fragment[6];


    /**
     * 核心 Fragment
     */
    VideoPlayerFragment videoPlayerFragment;

    MagicVideoPlayViewModel magicVideoPlayViewModel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPlayBinding = ActivityMagicVideoPlayBinding.inflate(LayoutInflater.from(this));
        setContentView(videoPlayBinding.getRoot());
        horizontalViewPager = videoPlayBinding.horizontalVideoViewPager;


        horizontalViewPager.setAdapter(new VerticalFragmentAdapter(this));
        horizontalViewPager.setCurrentItem(1,false);

        videoPlayerFragment = new VideoPlayerFragment();
        videoPlayerFragment.setContext(this);


        magicVideoPlayViewModel = new MagicVideoPlayViewModel(videoPlayerFragment);

        magicVideoPlayViewModel.getVideoTest();

        ResetPositionCallBack horizontalCallBack = new ResetPositionCallBack(horizontalViewPager, magicVideoPlayViewModel);

        horizontalViewPager.registerOnPageChangeCallback(horizontalCallBack);


    }


    /**
     * 竖直 适配器
     */
    private class VerticalFragmentAdapter extends FragmentStateAdapter {

        public VerticalFragmentAdapter(@NonNull FragmentActivity activity) {
            super(activity);
        }



        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            if (position == 1) {
                fragment = new VerticalTriplePageFragment();
                ((VerticalTriplePageFragment)fragment).setActivity(MagicVideoPlayActivity.this);
                ((VerticalTriplePageFragment)fragment).setVideoPlayerFragment(videoPlayerFragment);
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

        public ResetPositionCallBack(ViewPager2 viewPager2, MagicVideoPlayViewModel magicVideoPlayViewModel) {
            this.viewPager2 = viewPager2;
            this.magicVideoPlayViewModel = magicVideoPlayViewModel;
        }

        MagicVideoPlayViewModel magicVideoPlayViewModel;




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
                    magicVideoPlayViewModel.videoPlayerFragment.setMyNews(magicVideoPlayViewModel.upNewses.pop());
                } else {
                    //触发左滑操作
                    magicVideoPlayViewModel.videoPlayerFragment.setMyNews(magicVideoPlayViewModel.leftNewses.pop());
                }

            } else if (prePosition == 2) {
                if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                    //触发下滑操作
                    magicVideoPlayViewModel.videoPlayerFragment.setMyNews(magicVideoPlayViewModel.downNewses.pop());
                } else {
                    //触发右滑操作
                    magicVideoPlayViewModel.videoPlayerFragment.setMyNews(magicVideoPlayViewModel.rightNewses.pop());
                }
            }
        }
    }


    Fragment[] getFragments() {
        return fragments;
    }
}

