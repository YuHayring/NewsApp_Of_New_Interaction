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
//        List<News> data = new ArrayList<>();
//        News news1 = new News();
//        news1.setNewsId("1");
//        news1.setNewsName("第一个");
//        news1.setNewsType(News.VIDEO);
//        news1.setNewsUrl("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");
//        News news2 = new News();
//        news2.setNewsId("2");
//        news2.setNewsName("第二个");
//        news2.setNewsType(News.VIDEO);
//        news2.setNewsUrl("http://v.ysbang.cn/data/video/2015/rkb/2015rkb01.mp4");
//        data.add(news1);
//        data.add(news2);


//
//        new Thread() {
//            @Override
//            public void run() {
//                Message message = new Message();
//                message.obj = data;
//                onVideoGotHandler.sendMessage(message);
//            }
//        }.start();
        NewsApi.getNewsApi().getVideoList().enqueue(videoGetCallBack);
    }

    /**
     * 模拟数据
     * @return
     */
    public void getMoreVideoNews(int index) {
        NewsApi.getNewsApi().getVideoList(index, 5).enqueue(videoGetCallBack);
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



}
