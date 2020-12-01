package cn.edu.gdut.douyintoutiao.net;

import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TabsApi {

    @GET("getTabs")
    Call<String[]> getTabs();



    /**
     * 单例内部类
     */
    class Singleton {
        static TabsApi tabsApi = RetrofitSingleton.getInstance().create(TabsApi.class);
    }

    static TabsApi getTabsApi() {
        return TabsApi.Singleton.tabsApi;
    }


}
