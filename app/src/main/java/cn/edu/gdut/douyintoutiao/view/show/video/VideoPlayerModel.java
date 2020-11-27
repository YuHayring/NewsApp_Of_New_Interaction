package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.News;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import cn.edu.gdut.douyintoutiao.net.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hayring
 * @date 11/17/20 3:10 PM
 */
public class VideoPlayerModel {

    private Handler onVideoGotHandler;

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


}
