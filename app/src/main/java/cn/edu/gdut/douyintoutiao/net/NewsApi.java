package cn.edu.gdut.douyintoutiao.net;

import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.FollowNews;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


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


    @GET("videoList")
    Call<List<MyNews>> getVideoList(@Query("index") int index, @Query("count") int count);

    @GET("videoList")
    Call<List<MyNews>> getVideoList();

    @GET("searchNews")
    Call<Result<MyNews>> searchNewsList(@Query("key") String key);

    @GET("searchVideos")
    Call<List<MyNews>> searchVideosList(@Query("key") String key);

    //TODO 解决后台嵌套查询的问题
    @GET("getNewsWithOption")
    Call<Result<MyNews>> getFollowNewsList(@Query("tag") String tag);


    @POST("insert_tags_follow_table")
    Call<Result< FollowNews >> insertTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

    @POST("delete_tags_follow_byNewsIdUserId")
    Call<Result> deleteTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

    @POST("check_tags_follow")
    Call<Result> checkTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

    @POST("check_tags_follow")
    Observable<Result> checkTagsFollowByNewsIdUserId1 (@Body Map<String,String> newsIdUserId);
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
