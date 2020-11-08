package cn.edu.gdut.douyintoutiao;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.edu.gdut.douyintoutiao.view.MainFragment;
import cn.edu.gdut.douyintoutiao.view.UserMainFragment;
import cn.edu.gdut.douyintoutiao.view.VideoPlayerFragment;

public class MainActivity extends AppCompatActivity {


    Fragment mainFragment;

    Fragment userMainFragment;

    /**
     * 用于测试，后期要删除
     */
    Fragment videoPlayerFragment;


    protected Fragment lastFragment;

    protected Button resign_enter;
    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);

        userMainFragment = new UserMainFragment(this);
        mainFragment = new VideoPlayerFragment(this);//MainFragment();




        lastFragment = mainFragment;
        //显示 Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview, mainFragment).show(mainFragment).commitNow();

        bottomNavigationView = findViewById(R.id.navigation);
        //注册导航栏点击监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //跳转到注册页的跳转监听

    }

    /**
     * 导航栏点击监听器
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.main_page_switch) {
                switchFragment(mainFragment);
                return true;
            } else if (item.getItemId() == R.id.user_main_page_switch) {
                switchFragment(userMainFragment);
                return true;
            }
            return false;
        }
    };

    /**
     * Fragment 切换方法
     * @param nextfragment
     */
    private void switchFragment(Fragment nextfragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(lastFragment);//隐藏上个Fragment
        //生命周期
        lastFragment.onPause();
        if (nextfragment.isAdded() == false) {
            transaction.add(R.id.mainview, nextfragment);
        }

        transaction.show(nextfragment).commitAllowingStateLoss();
        //生命周期
        nextfragment.onResume();
        lastFragment = nextfragment;
    }
}