package cn.edu.gdut.douyintoutiao.net;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author cypang
 * @date 2020年11月11日17:15:47
 */
public interface NewsApi {

    @GET("newsList")
    Call<Result<MyNews>> getNewsList();


    @GET("videoList")
    Call<List<MyNews>> getVideoList(@Query("index") int index, @Query("count") int count);

    @GET("videoList")
    Call<List<MyNews>> getVideoList();

    @GET("searchNews")
    Call<Result<MyNews>> searchNewsList(@Query("key") String key);

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
