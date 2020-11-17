package cn.edu.gdut.douyintoutiao.util;

import android.content.Context;

/**
 * @author hayring
 * @date 11/13/20 8:57 PM
 */
public class UIUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
