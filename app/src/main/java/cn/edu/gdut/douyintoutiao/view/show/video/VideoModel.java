package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Handler;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.FollowNews;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.util.CommonDataGotCallBack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 11/17/20 3:10 PM
 */
public class VideoModel {

    private Handler onVideoGotHandler;
    private static final String FollowTag = "follow";
    /**
     * 模拟数据
     * @return
     */
    public void getVideoNews(CommonDataGotCallBack<MyNews> commonVideoGotCallBack) {
        NewsApi.getNewsApi().getVideoList().enqueue(commonVideoGotCallBack);
    }


    /**
     * 模拟数据
     * @return
     */
    public void searchVideoNews(String key, CommonDataGotCallBack<MyNews> commonVideoGotCallBack) {
        NewsApi.getNewsApi().searchVideoList(key).enqueue(commonVideoGotCallBack);
    }

    /**
     * 模拟数据
     * @return
     */
    public void getMoreVideoNews(int index, CommonDataGotCallBack<MyNews> commonVideoGotCallBack) {
        NewsApi.getNewsApi().getVideoList(index, 5).enqueue(commonVideoGotCallBack);
    }


    /**
     * 模拟数据
     * @return
     */
    public void getMoreVideoNews(int index, int pageCount, CommonDataGotCallBack<MyNews> commonVideoGotCallBack) {
        NewsApi.getNewsApi().getVideoList(index, pageCount).enqueue(commonVideoGotCallBack);
    }

    /**
     * 模拟数据
     * @return
     */
    public void searchMoreVideoNews(int index, String key, CommonDataGotCallBack<MyNews> commonVideoGotCallBack) {
        NewsApi.getNewsApi().searchMoreVideoList(index, 5, key).enqueue(commonVideoGotCallBack);
    }


    public void setOnVideoGotHandler(Handler onVideoGotHandler) {
        this.onVideoGotHandler = onVideoGotHandler;
    }



    public void insertFollowByNewsIdUserId(String newsId , String userId){
        Map<String, String> newsIdUserId = new HashMap<>();
        newsIdUserId.put("newsId", newsId);
        newsIdUserId.put("userId",userId);
        NewsApi.getNewsApi().insertTagsFollowByNewsIdUserId(newsIdUserId);
    }



    /**
     * 模拟数据
     * @return
     */
    public void getTagVideoNews(String tag, CommonDataGotCallBack<MyNews> callBack) {
        NewsApi.getNewsApi().getTagVideo(tag).enqueue(callBack);
    }








    //添加关注
    public void insertTagsFollowByNewsIdUserId(String newsId,String userId){
        Map<String, String> newsIdUserId = new HashMap<>();
        newsIdUserId.put("newsId", newsId);
        newsIdUserId.put("userId",userId);
        Call< Result< FollowNews > > call = NewsApi.getNewsApi().insertTagsFollowByNewsIdUserId(newsIdUserId);
        call.enqueue(new Callback< Result< FollowNews > >() {
            @Override
            public void onResponse(Call< Result< FollowNews > > call, Response< Result< FollowNews > > response) {
                Log.d(FollowTag, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call< Result< FollowNews > > call, Throwable t) {
                Log.d(FollowTag, "onFailure: insertTagsFollowByNewsIdUserId失败 ");
            }
        });
    }
    //取消关注
    public void deleteTagsFollowByNewsIdUserId(String newsId,String userId){
        Map<String, String> newsIdUserId = new HashMap<>();
        newsIdUserId.put("newsId", newsId);
        newsIdUserId.put("userId",userId);
        Call< Result > call = NewsApi.getNewsApi().deleteTagsFollowByNewsIdUserId(newsIdUserId);
        call.enqueue(new Callback< Result >() {
            @Override
            public void onResponse(Call< Result> call, Response< Result > response) {
                Log.d(FollowTag, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call< Result > call, Throwable t) {
                Log.d(FollowTag, "onFailure: deleteTagsFollowByNewsIdUserId失败 ");
            }
        });
    }





    private static class Singleton {
        static VideoModel videoModel = new VideoModel();
    }

    public static VideoModel getInstance() {
        return Singleton.videoModel;
    }

    private VideoModel(){}
}
