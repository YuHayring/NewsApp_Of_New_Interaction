package cn.edu.gdut.douyintoutiao.net;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

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

    //@author hudp
    @POST("new_like")
    Call<Result<MyNews>> likeNews(@Body String newsID);

    @POST("new_unlike")
    Call<Result<MyNews>> nolikeNews();

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
