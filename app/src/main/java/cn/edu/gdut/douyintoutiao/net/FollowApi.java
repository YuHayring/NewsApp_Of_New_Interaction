package cn.edu.gdut.douyintoutiao.net;


import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.Result;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author dengJL
 * @date 2020年11月12日
 */
public interface FollowApi {

    @GET("find_user_followList")
    Call< Result< Follow > > getFollowList();

    /**
     * 单例内部类
     */
    class Singleton {
        static FollowApi followApi = RetrofitSingleton.getInstance().create(FollowApi.class);
    }

    static FollowApi getFollowApi() {
        return Singleton.followApi;
    }



}
