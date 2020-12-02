package cn.edu.gdut.douyintoutiao.view;

import java.util.HashMap;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.net.TabsApi;
import retrofit2.Callback;
import retrofit2.http.Field;

/**
 * @author hayring
 * @date 12/1/20 9:11 PM
 */
public class TabModel {


    void getTabs(Callback callback) {
        TabsApi.getTabsApi().getTabs().enqueue(callback);
    }

    void setTabFollow(int tabFollow, String userId, Callback<Void> callback) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("tabFollow",tabFollow);
        TabsApi.getTabsApi().setMyTabs(map).enqueue(callback);
    }





    static class Singleton {
        static TabModel instance = new TabModel();
    }


    private TabModel(){}

    public static TabModel getInstance() {
        return Singleton.instance;
    }
}
