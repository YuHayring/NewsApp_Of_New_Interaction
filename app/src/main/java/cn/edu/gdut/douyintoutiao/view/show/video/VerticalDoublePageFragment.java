package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.tmp.ShowIndexFragment;

/**
 * @author hayring
 * @date 11/26/20 6:54 PM
 */
public class VerticalDoublePageFragment extends Fragment {


    ViewPager2 verticalViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_cycle_view_pager2, container, false);
        verticalViewPager = view.findViewById(R.id.vertical_view_pager);
        verticalViewPager.setAdapter(new VerticalFragmentAdapter(activity));
        verticalViewPager.setCurrentItem(1);

        MagicVideoPlayActivity.ResetPositionCallBack horizontalCallBack = new MagicVideoPlayActivity.ResetPositionCallBack(verticalViewPager,false);
        horizontalCallBack.setActivity(activity);
        verticalViewPager.registerOnPageChangeCallback(horizontalCallBack);
        return view;
    }

    //竖直 viewpager 中的位置
    int position;



    private MagicVideoPlayActivity activity;

    private class VerticalFragmentAdapter extends FragmentStateAdapter {

        public VerticalFragmentAdapter(@NonNull FragmentActivity activity) {
            super(activity);
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            position += 3;
            ShowIndexFragment fragment = new ShowIndexFragment();
            fragment.setIndex(position);
            activity.getFragments()[position] = fragment;
            return fragment;
        }
    }



    public void setActivity(MagicVideoPlayActivity activity) {
        this.activity = activity;
    }


    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("VerticalDoublePageFragment","position:" + position +" OnResume");
    }
}
