package cn.edu.gdut.douyintoutiao.view;

import cn.edu.gdut.douyintoutiao.net.TabsApi;
import cn.edu.gdut.douyintoutiao.util.CommonDataGotCallBack;
import retrofit2.Callback;

/**
 * @author hayring
 * @date 12/1/20 9:11 PM
 */
public class MainModel {


    void getTabs(Callback callback) {
        TabsApi.getTabsApi().getTabs().enqueue(callback);
    }





    static class Singleton {
        static MainModel instance = new MainModel();
    }


    private MainModel(){}

    public static MainModel getInstance() {
        return Singleton.instance;
    }
}
