package cn.edu.gdut.douyintoutiao;

import android.app.Application;

/**
 * @author hayring
 * @date 11/14/20 9:37 PM
 */
public class DouYinTouTiaoApplication extends Application {


    public static final String TOKEN = "tokenSp";

    /**
     * 当内部滑动为竖直，外部为水平时，触摸倾斜角的正切值达到多少才进行横向滑动
     */
    public static final int SCROLL_TAN = 2;

    private static DouYinTouTiaoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static DouYinTouTiaoApplication getInstance(){
        return instance;
    }

}
