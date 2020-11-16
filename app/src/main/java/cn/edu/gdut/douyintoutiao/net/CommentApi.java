package cn.edu.gdut.douyintoutiao.net;

import cn.edu.gdut.douyintoutiao.entity.Discuss;
import cn.edu.gdut.douyintoutiao.entity.Result;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author cypang
 * @date 2020年11月13日10:59:53
 */
public interface CommentApi {

    static CommentApi getCommentApi() {
        return CommentApi.Singleton.commentApi;
    }

    @GET("getComments")
    Call<Result<Discuss>> getDiscussList();

    /**
     * 单例内部类
     */
    class Singleton {
        static CommentApi commentApi = RetrofitSingleton.getInstance().create(CommentApi.class);
    }
}
