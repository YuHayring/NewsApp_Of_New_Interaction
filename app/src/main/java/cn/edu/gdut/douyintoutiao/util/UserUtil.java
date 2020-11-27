package cn.edu.gdut.douyintoutiao.util;

import android.content.Context;
import android.content.SharedPreferences;

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
public class UserUtil {

    public static boolean isLogin(Context context){
        SharedPreferences shp = context.getSharedPreferences("LOGIN_USER", Context.MODE_PRIVATE);
        return shp.contains("userId");
    }
}
