package cn.edu.gdut.douyintoutiao.view.show.search.model;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/11 17:10
 */
public class SearchVideosRepository {

    private Handler onVideoGotHandler;
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

    /**
     * 模拟数据
     *
     * @return
     */
//    public void getVideoNews(String key) {
//        NewsApi.getNewsApi().searchVideoList(key).enqueue(videoGetCallBack);
//    }

    public void setOnVideoGotHandler(Handler onVideoGotHandler) {
        this.onVideoGotHandler = onVideoGotHandler;
    }
}
