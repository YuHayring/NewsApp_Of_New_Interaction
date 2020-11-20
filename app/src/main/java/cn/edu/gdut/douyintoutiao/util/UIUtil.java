package cn.edu.gdut.douyintoutiao.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * @author hayring
 * @date 11/19/20 9:37 PM
 */
public class UIUtil {

    /**
     * dip 转 pixel
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
     * @param context
     * @return pixel
     */
    public static int getScreenWidth(@NonNull Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}