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






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPlayBinding = ActivityMagicVideoPlayBinding.inflate(LayoutInflater.from(this));
        setContentView(videoPlayBinding.getRoot());
        horizontalViewPager = videoPlayBinding.horizontalVideoViewPager;


        horizontalViewPager.setAdapter(new VerticalFragmentAdapter(this));
        horizontalViewPager.setCurrentItem(1,false);
        ResetPositionCallBack horizontalCallBack = new ResetPositionCallBack(horizontalViewPager,false);
        horizontalCallBack.setActivity(this);
        horizontalViewPager.registerOnPageChangeCallback(horizontalCallBack);





    }





    private class VerticalFragmentAdapter extends FragmentStateAdapter {

        public VerticalFragmentAdapter(@NonNull FragmentActivity activity) {
            super(activity);
        }



        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            if (position == 1) {
                fragment = new VerticalDoublePageFragment();
                ((VerticalDoublePageFragment)fragment).setActivity(MagicVideoPlayActivity.this);
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




    static class ResetPositionCallBack extends ViewPager2.OnPageChangeCallback {

        int currentPosition = 1;

        ViewPager2 viewPager2;

        boolean vertical;

        public ResetPositionCallBack(ViewPager2 viewPager2, boolean vertical) {
            this.viewPager2 = viewPager2;
            this.vertical = vertical;
        }

        private MagicVideoPlayActivity activity;

        public void setActivity(MagicVideoPlayActivity activity) {
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
            //        若当前为第一张，设置页面为倒数第二张
            if (currentPosition == 0) {
                if (vertical) {
                    //触发上滑操作
                    //TODO
                    Toasty.info(activity,"刚才上滑了");
                } else {
                    //触发左滑操作
                    //TODO
                    Toasty.info(activity,"刚才左滑了");
                }

            } else if (currentPosition == 2) {
                if (vertical) {
                    //触发下滑操作
                    //TODO
                    Toasty.info(activity,"刚才下滑了");
                } else {
                    //触发右滑操作
                    //TODO
                    Toasty.info(activity,"刚才右滑了");
                }
            }
            viewPager2.setCurrentItem(1,false);
        }
    }


    Fragment[] getFragments() {
        return fragments;
    }
}

