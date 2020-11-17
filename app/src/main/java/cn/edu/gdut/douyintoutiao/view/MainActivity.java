package cn.edu.gdut.douyintoutiao.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.view.user.main.UserMainFragment;

/**
 * @author hayring
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 页数
     */
    private static final int NUM_PAGES = 2;


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     * 关键 view 组件
     */
    private ViewPager2 viewPager;
    /**
     * 导航栏点击监听器
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.main_page_switch) {
                //switchFragment(mainFragment);
                viewPager.setCurrentItem(0, true);
                return true;
            } else if (item.getItemId() == R.id.user_main_page_switch) {
                //switchFragment(userMainFragment);
                viewPager.setCurrentItem(1, true);
                return true;
            }
            return false;
        }
    };


    protected BottomNavigationView bottomNavigationView;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        viewPager = findViewById(R.id.main_view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        //关闭这个 viewpager 的左右滑动，让滑动手势留给子 viewpager
        viewPager.setUserInputEnabled(false);


        bottomNavigationView = findViewById(R.id.navigation);

//        userMainFragment = new UserMainFragment(this);
//        mainFragment = new MainFragment(this);//VideoPlayerFragment(this);
//
//
//
//
//        lastFragment = mainFragment;
        //显示 Fragment
        //getSupportFragmentManager().beginTransaction().replace(R.id.mainview, mainFragment).show(mainFragment).commitNow();


        //注册导航栏点击监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

//    /**
//     * Fragment 切换方法
//     * @param nextfragment
//     */
//    private void switchFragment(Fragment nextfragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.hide(lastFragment);//隐藏上个Fragment
//        //生命周期
//        lastFragment.onPause();
//        if (nextfragment.isAdded() == false) {
//            transaction.add(R.id.mainview, nextfragment);
//        }
//
//        transaction.show(nextfragment).commitAllowingStateLoss();
//        //生命周期
//        nextfragment.onResume();
//        lastFragment = nextfragment;
//    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new MainFragment(MainActivity.this);
            } else if (position == 1) {
                return new UserMainFragment(MainActivity.this);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}










