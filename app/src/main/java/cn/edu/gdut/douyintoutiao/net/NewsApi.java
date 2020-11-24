package cn.edu.gdut.douyintoutiao.net;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.FollowNews;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author cypang
 * @date 2020年11月11日17:15:47
 */
public interface NewsApi {

    @GET("newsList")
    Call<Result<MyNews>> getNewsList();

    /**
     * @DengJl
     * 关注事件功能
     * 11/21
     */
    @POST("insert_tags_follow_table")
    Call<Result> insertTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

    @POST("delete_tags_follow_byNewsIdUserId")
    Call<Result> deleteTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

    @POST("check_tags_follow")
    Call<Result<Boolean>> checkTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

    /**
     * 单例内部类
     */
    class Singleton {
        static NewsApi newsApi = RetrofitSingleton.getInstance().create(NewsApi.class);
    }

    static NewsApi getNewsApi() {
        return Singleton.newsApi;
    }
}
