package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.FollowNews;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 11/17/20 3:10 PM
 */
public class VideoPlayModel {

    private Handler onVideoGotHandler;
    private static final String FollowTag = "follow";
    /**
     * 模拟数据
     * @return
     */
    public void getVideoNews(VideoPlayViewModel.CommonVideoGotCallBack commonVideoGotCallBack) {
        NewsApi.getNewsApi().getVideoList().enqueue(commonVideoGotCallBack);
    }


    /**
     * 模拟数据
     * @return
     */
    public void searchVideoNews(String key, VideoPlayViewModel.CommonVideoGotCallBack commonVideoGotCallBack) {
        NewsApi.getNewsApi().searchVideoList(key).enqueue(commonVideoGotCallBack);
    }

    /**
     * 模拟数据
     * @return
     */
    public void getMoreVideoNews(int index, VideoPlayViewModel.CommonVideoGotCallBack commonVideoGotCallBack) {
        NewsApi.getNewsApi().getVideoList(index, 5).enqueue(commonVideoGotCallBack);
    }

    /**
     * 模拟数据
     * @return
     */
    public void searchMoreVideoNews(int index, String key, VideoPlayViewModel.CommonVideoGotCallBack commonVideoGotCallBack) {
        NewsApi.getNewsApi().searchMoreVideoList(index, 5, key).enqueue(commonVideoGotCallBack);
    }


    public void setOnVideoGotHandler(Handler onVideoGotHandler) {
        this.onVideoGotHandler = onVideoGotHandler;
    }








    /**
     * 模拟数据
     * @return
     */
    public void getTagVideoNews(String tag, VideoPlayViewModel.CommonVideoGotCallBack callBack) {
        NewsApi.getNewsApi().getTagVideo(tag).enqueue(callBack);
    }








    //关注
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






    private static class Singleton {
        static VideoPlayModel videoPlayModel = new VideoPlayModel();
    }

    public static VideoPlayModel getInstance() {
        return Singleton.videoPlayModel;
    }

    private VideoPlayModel(){}
}
