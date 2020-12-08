package cn.edu.gdut.douyintoutiao.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @ProjectName: DouYinTouTiao
 * @Package: cn.edu.gdut.douyintoutiao.util
 * @ClassName: UserUtil
 * @Description: java类作用描述
 * @Author: cypang
 * @CreateDate: 2020/11/27/0027 20:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/27/0027 20:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MyUtil {

    public static boolean isLogin(Context context){
        SharedPreferences shp = context.getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        return shp.contains("userId");
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
