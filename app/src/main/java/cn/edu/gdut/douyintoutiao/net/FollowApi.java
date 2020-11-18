package cn.edu.gdut.douyintoutiao.net;


import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author dengJL
 * @date 2020年11月12日
 */
public interface FollowApi {

    @GET("find_user_followList")
    Call< Result< Follow > > getFollowList();

    @POST("delete_user_follow_list")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Call< Result< Follow > >  deleteFollowListByFollowId(@Body Map<String, String> followIdMap);

    @POST("query_user_by_userId")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Call< Result< User > > queryUserByUserId(@Body Map<String, String> userIdIdMap);

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
