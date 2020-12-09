package cn.edu.gdut.douyintoutiao.view;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.edu.gdut.douyintoutiao.R;

public class FullScreenActivity extends AppCompatActivity {


    private int systemUiVisibility;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            //允许延伸到刘海区域
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        View decorView = getWindow().getDecorView();
        systemUiVisibility = decorView.getSystemUiVisibility();
        Log.d("systemUiVisibility pre",""+systemUiVisibility);
        int flags = //布局延伸到状态栏
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //隐藏状态栏
               |  View.SYSTEM_UI_FLAG_FULLSCREEN
                    //点击事件发生后后不显示状态栏
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //存储全屏显示 flags
        systemUiVisibility |= flags;
        Log.d("systemUiVisibility set",""+systemUiVisibility);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //每次启动或返回界面时设置全屏显示
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }
}