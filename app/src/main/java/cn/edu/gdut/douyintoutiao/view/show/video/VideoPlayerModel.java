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
public class VideoPlayerModel {

    private Handler onVideoGotHandler;
    private static final String FollowTag = "follow";
    /**
     * 模拟数据
     * @return
     */
    public void getVideoNews() {
        NewsApi.getNewsApi().getVideoList().enqueue(videoGetCallBack);
    }


    /**
     * 模拟数据
     * @return
     */
    public void searchVideoNews(String key) {
        NewsApi.getNewsApi().searchVideoList(key).enqueue(videoGetCallBack);
    }

    /**
     * 模拟数据
     * @return
     */
    public void getMoreVideoNews(int index) {
        NewsApi.getNewsApi().getVideoList(index, 5).enqueue(videoGetCallBack);
    }

    /**
     * 模拟数据
     * @return
     */
    public void searchMoreVideoNews(int index, String key) {
        NewsApi.getNewsApi().searchMoreVideoList(index, 5, key).enqueue(videoGetCallBack);
    }


    public void setOnVideoGotHandler(Handler onVideoGotHandler) {
        this.onVideoGotHandler = onVideoGotHandler;
    }



    Callback<List<MyNews>> videoGetCallBack = new Callback<List<MyNews>>() {
        @Override
        public void onResponse(Call<List<MyNews>> call, Response<List<MyNews>> response) {
            Message message = new Message();
            if (response.code() == 200) {
                message.arg1 = 200;
                message.obj = response.body();
            } else {
                message.arg1 = response.code();
            }
            onVideoGotHandler.sendMessage(message);
        }

        @Override
        public void onFailure(Call<List<MyNews>> call, Throwable t) {

        }
    };


    private static class Singleton {
        static VideoPlayerModel videoPlayerModel = new VideoPlayerModel();
    }

    public static VideoPlayerModel getInstance() {
        return Singleton.videoPlayerModel;
    }

    private VideoPlayerModel(){}




    /**
     * 模拟数据
     * @return
     */
    public void getUpVideoNews(Handler handler) {
        NewsApi.getNewsApi().getVideoList().enqueue(upVideoCallBack.setHandler(handler));
    }

    private GotCallBack upVideoCallBack = new GotCallBack();


    private class GotCallBack implements Callback<List<MyNews>> {

        Handler handler;



        public GotCallBack setHandler(Handler handler) {
            this.handler = handler;
            return this;
        }

        @Override
        public void onResponse(Call<List<MyNews>> call, Response<List<MyNews>> response) {
            Message message = new Message();
            if (response.code() == 200) {
                message.arg1 = 200;
                message.obj = response.body();
            } else {
                message.arg1 = response.code();
            }
            handler.sendMessage(message);
        }

        @Override
        public void onFailure(Call<List<MyNews>> call, Throwable t) {

        }
    };

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
}
