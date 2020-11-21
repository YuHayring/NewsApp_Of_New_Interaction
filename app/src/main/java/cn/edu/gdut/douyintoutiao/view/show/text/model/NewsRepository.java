package cn.edu.gdut.douyintoutiao.view.show.text.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
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
    private static final String FollowTag = "follow";

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

    public void insertTagsFollowByNewsIdUserId(String newsId,String userId){
        Map<String, String> newsIdUserId = new HashMap<>();
        newsIdUserId.put("newsId", newsId);
        newsIdUserId.put("userId",userId);
        Call<Result> call = api.insertTagsFollowByNewsIdUserId(newsIdUserId);
        call.enqueue(new Callback< Result >() {
            @Override
            public void onResponse(Call< Result > call, Response< Result > response) {
                Log.d(FollowTag, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());

            }

            @Override
            public void onFailure(Call< Result > call, Throwable t) {
                Log.d(FollowTag, "onFailure: insertTagsFollowByNewsIdUserId失败 ");

            }
        });
    }


}
