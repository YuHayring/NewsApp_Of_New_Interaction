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
public class HorizontalDoublePageFragment extends Fragment {


    ViewPager2 verticalViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_cycle_view_pager2, container, false);
        verticalViewPager = view.findViewById(R.id.vertical_view_pager);
        verticalViewPager.setAdapter(new HorizontalFragmentAdapter(activity));
        verticalViewPager.setCurrentItem(1);
        return view;
    }

    //竖直 viewpager 中的位置
    int position;



    private MagicVideoPlayActivity activity;

    private class HorizontalFragmentAdapter extends FragmentStateAdapter {

        public HorizontalFragmentAdapter(@NonNull FragmentActivity activity) {
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
        Log.i("HorizontalDoublePageFragment","position:" + position +" OnResume");
    }
}
