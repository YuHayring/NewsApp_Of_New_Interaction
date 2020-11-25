package cn.edu.gdut.douyintoutiao.view.show.text.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
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
public class NewsRepository {

    private final MutableLiveData<List<MyNews>> allNewsLive;
    private final NewsApi api;
    private static final String TAG = "news";

    public NewsRepository(NewsApi api) {
        this.api = api;
        allNewsLive = new MutableLiveData<>();
    }


    public LiveData<List<MyNews>> getAllNewsLive() {
        Call<Result<MyNews>> call = api.getNewsList();
        call.enqueue(new Callback<Result<MyNews>>() {
            @Override
            public void onResponse(Call<Result<MyNews>> call, Response<Result<MyNews>> response) {
                allNewsLive.postValue(response.body().getData());
                Log.d(TAG, "onResponse: " + response.body().getData().toString());
            }

            @Override
            public void onFailure(Call<Result<MyNews>> call, Throwable t) {

            }
        });
        return allNewsLive;
    }

    //@author : hudp
    //点赞
    public MutableLiveData<Result<MyNews>> newsLike(String newsID) {
        Call<Result<MyNews>> newslike = api.likeNews(newsID);
        MutableLiveData<Result<MyNews>> mutableLiveData = new MutableLiveData<Result<MyNews>>();
        Response<Result<MyNews>> response = null;
        try {
            response = newslike.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutableLiveData.setValue(response.body());
        return mutableLiveData;
    }


}
