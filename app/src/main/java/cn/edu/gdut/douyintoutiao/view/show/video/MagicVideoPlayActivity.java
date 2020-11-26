package cn.edu.gdut.douyintoutiao.view.show.video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.databinding.ActivityMagicVideoPlayBinding;
import cn.edu.gdut.douyintoutiao.tmp.ShowIndexFragment;
import cn.edu.gdut.douyintoutiao.view.FullScreenActivity;

/**
 * @author hayring
 * @date 2020.11.26 16:00
 */
public class MagicVideoPlayActivity extends FullScreenActivity {



    ActivityMagicVideoPlayBinding videoPlayBinding;




    ViewPager2 verticalViewPager;
//
//    VideoPlayerFragment[] fragments = new VideoPlayerFragment[4];
    Fragment[] fragments = new Fragment[6];






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPlayBinding = ActivityMagicVideoPlayBinding.inflate(LayoutInflater.from(this));
        setContentView(videoPlayBinding.getRoot());
        verticalViewPager = videoPlayBinding.verticalVideoViewPager;


//        verticalViewPager.setAdapter(verticalAdapter);
        verticalViewPager.setAdapter(new VerticalFragmentAdapter(this));
        verticalViewPager.setCurrentItem(1,false);





    }

//    /**
//     * 竖直的 ViewPager Adapter
//     */
//    CyclePagerAdapter<VerticalViewHolder> verticalAdapter = new CyclePagerAdapter() {
//        @Override
//        public int getRealItemCount() {
//            return 2;
//        }
//
//        @Override
//        public void onBindRealViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        }
//        @NonNull
//        @Override
//        public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            if (parent.getChildCount() == 0) {
//                horizontalViewPager0 = (CycleViewPager2) LayoutInflater.from(MagicVideoPlayActivity.this).inflate(R.layout.view_cycle_view_pager2, parent, false);
//                horizontalViewPager0.setAdapter(new CycleDoublePagerFragmentAdapter(MagicVideoPlayActivity.this,0));
//                horizontalViewPager0.setOrientation(View.SCROLL_AXIS_HORIZONTAL);
//                return new VerticalViewHolder(horizontalViewPager0);
//            } else {
//                horizontalViewPager1 = (CycleViewPager2) LayoutInflater.from(MagicVideoPlayActivity.this).inflate(R.layout.view_cycle_view_pager2, parent, false);
//                horizontalViewPager1.setAdapter(new CycleDoublePagerFragmentAdapter(MagicVideoPlayActivity.this,1));
//                horizontalViewPager1.setOrientation(View.SCROLL_AXIS_HORIZONTAL);
//                return new VerticalViewHolder(horizontalViewPager1);
//            }
//        }
//    };
//
//    private static class VerticalViewHolder extends RecyclerView.ViewHolder {
//
//        public VerticalViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }



    private class VerticalFragmentAdapter extends FragmentStateAdapter {

        public VerticalFragmentAdapter(@NonNull FragmentActivity activity) {
            super(activity);
        }



        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            if (position == 1) {
                fragment = new HorizontalDoublePageFragment();
                ((HorizontalDoublePageFragment)fragment).setActivity(MagicVideoPlayActivity.this);
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


    Fragment[] getFragments() {
        return fragments;
    }
}

