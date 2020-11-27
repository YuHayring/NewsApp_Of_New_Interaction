package cn.edu.gdut.douyintoutiao.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * @author hayring
 * @date 11/19/20 9:37 PM
 */
public class UIUtil {

    //记录上次按下的时间
    private static long lastClickTime;
    //容许用户重复点击按钮的时间间隔
    private static final int MIN_CLICK_DELAY_TIME = 5000;

    /**
     * dip 转 pixel
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * pixel 转 dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return pixel
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    public static int getScreenWidth(@NonNull Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 防止按钮在短时间内被重复按下
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < MIN_CLICK_DELAY_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;

    }
}