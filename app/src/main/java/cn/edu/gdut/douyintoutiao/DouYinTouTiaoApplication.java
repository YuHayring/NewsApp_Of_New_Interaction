package cn.edu.gdut.douyintoutiao;

import android.app.Application;

/**
 * @author hayring
 * @date 11/14/20 9:37 PM
 */
public class DouYinTouTiaoApplication extends Application {


    public static final String TOKEN = "tokenSp";

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
