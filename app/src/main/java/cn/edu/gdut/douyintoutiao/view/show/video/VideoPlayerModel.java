package cn.edu.gdut.douyintoutiao.view.show.video;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
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



}
