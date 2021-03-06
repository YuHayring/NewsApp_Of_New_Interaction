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
    Call<Result<MyNews>> getNewsList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize);

    //@author hudp
    @POST("new_like")
    Call<Result<MyNews>> likeNews(@Body MyNews news);

    @POST("new_unlike")
    Call<Result<MyNews>> nolikeNews(@Body MyNews news);


    @GET("videoList")
    Call<List<MyNews>> getVideoList(@Query("index") int index, @Query("count") int count);

    @GET("getMixNews")
    Call<List<MyNews>> getMixList();

    @GET("getMixNews")
    Call<List<MyNews>> getMixList(@Query("index") int index, @Query("count") int count);

    @GET("videoList")
    Call<List<MyNews>> getVideoList();

    @GET("searchNews")
    Call<Result<MyNews>> searchNewsList(@Query("key") String key);

//    @GET("searchVideos")
//    Call<List<MyNews>> searchVideosList(@Query("key") String key);

    @GET("videoList")
    Call<List<MyNews>> searchVideoList(@Query("key") String key);

    @GET("videoList")
    Call<List<MyNews>> searchMoreVideoList(@Query("index") int index, @Query("count") int count, @Query("key") String key);

    @GET("getTagVideo")
    Call<List<MyNews>> getTagVideo(@Query("tag") String tag);


    @GET("getNewsWithOption")
    Call<Result<MyNews>> getFollowNewsList(@Query("tag") String tag);

    @GET("getAuthorNews")
    Call<Result<MyNews>> getAuthorNewsList(@Query("userId") String userId);

    @POST("insert_tags_follow_table")
    Call<Result< FollowNews >> insertTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

    @POST("delete_tags_follow_byNewsIdUserId")
    Call<Result> deleteTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

    @POST("check_tags_follow")
    Call<Result<Boolean>> checkTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);

//    @POST("check_tags_follow")
//    Observable<Result<Boolean>> checkTagsFollowByNewsIdUserId (@Body Map<String,String> newsIdUserId);
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
