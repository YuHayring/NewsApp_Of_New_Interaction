package cn.edu.gdut.douyintoutiao.view.show.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.R;
import cn.edu.gdut.douyintoutiao.entity.News;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * @author hayring
 * @date 2020.11.9 15:00
 */
public class FullscreenActivity extends AppCompatActivity {


//    Fragment videoPlayerFragment;

    ViewPager2 videoViewPager;

    private VideoStateAdapter adapter;

    List<Fragment> fragments = new ArrayList<>();

    List<News> newses = new ArrayList<>();

    VideoPlayerModel videoPlayerModel = new VideoPlayerModel();

    VideoPlayerViewModel videoPlayerViewModel = new VideoPlayerViewModel(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        Log.d("systemUiVisibility pre",""+systemUiVisibility);
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_FULLSCREEN

                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        systemUiVisibility |= flags;
        decorView.setSystemUiVisibility(systemUiVisibility);
        Log.d("systemUiVisibility set",""+systemUiVisibility);

        setContentView(R.layout.activity_fullscreen);

//        controlView();
//        videoPlayerFragment = new VideoPlayerFragment(this);//MainFragment(this);
//
//
//        //显示 Fragment
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, videoPlayerFragment).show(videoPlayerFragment).commitAllowingStateLoss();

        videoPlayerViewModel.setVideoPlayerModel(videoPlayerModel);


        adapter = new VideoStateAdapter(this, fragments, newses);
        videoViewPager = findViewById(R.id.video_view_pager);
        videoViewPager.setAdapter(adapter);



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        videoPlayerModel.getVideoNews();

    }

//    public void controlView(){
//        View decorView = getWindow().getDecorView();
//        if(decorView != null){
////            Log.d("hwj", "**controlView**" + android.os.Build.VERSION.SDK_INT);
////            Log.d("hwj", "**controlView**" + android.os.Build.VERSION_CODES.P);
//            WindowInsets windowInsets = decorView.getRootWindowInsets();
//            if(windowInsets != null){
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                    DisplayCutout displayCutout = windowInsets.getDisplayCutout();
////                    //getBoundingRects返回List<Rect>,没一个list表示一个不可显示的区域，即刘海屏，可以遍历这个list中的Rect,
////                    //即可以获得每一个刘海屏的坐标位置,当然你也可以用类似getSafeInsetBottom的api
////                    Log.d("hwj", "**controlView**" + displayCutout.getBoundingRects());
////                    Log.d("hwj", "**controlView**" + displayCutout.getSafeInsetBottom());
////                    Log.d("hwj", "**controlView**" + displayCutout.getSafeInsetLeft());
////                    Log.d("hwj", "**controlView**" + displayCutout.getSafeInsetRight());
////                    Log.d("hwj", "**controlView**" + displayCutout.getSafeInsetTop());
//                }
//            }
//        }
//    }


    public VideoStateAdapter getAdapter() {
        return adapter;
    }
}