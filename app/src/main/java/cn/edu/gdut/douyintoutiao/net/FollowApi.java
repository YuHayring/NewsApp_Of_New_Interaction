package cn.edu.gdut.douyintoutiao.net;


import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author dengJL
 * @date 2020年11月12日
 */
public interface FollowApi {

    @GET("find_user_followList")
    Call< Result< Follow > > getFollowList();

    @POST("delete_user_follow_list")
    Call< Result< Follow > >  deleteFollowListByFollowId(@Body String followId);

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
