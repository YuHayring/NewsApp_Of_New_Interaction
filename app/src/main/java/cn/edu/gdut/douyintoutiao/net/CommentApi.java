package cn.edu.gdut.douyintoutiao.net;

import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Discuss;
import cn.edu.gdut.douyintoutiao.entity.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author cypang
 * @date 2020年11月13日10:59:53
 */
public interface CommentApi {

    static CommentApi getCommentApi() {
        return CommentApi.Singleton.commentApi;
    }

    @GET("getComments")
    Call<Result<Discuss>> getDiscussList(@Query("newsId") String newsId);

    @POST("postComment")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Call<Result<Void>> postComment(@Body Map<String, String> map);

    /**
     * 单例内部类
     */
    class Singleton {
        static CommentApi commentApi = RetrofitSingleton.getInstance().create(CommentApi.class);
    }
}
