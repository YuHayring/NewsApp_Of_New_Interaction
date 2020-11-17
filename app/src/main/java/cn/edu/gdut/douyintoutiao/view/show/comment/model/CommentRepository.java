package cn.edu.gdut.douyintoutiao.view.show.comment.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Discuss;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.CommentApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 11/13/20 10:57
 */
public class CommentRepository {
    private final MutableLiveData<List<Discuss>> allDiscussData;
    private static final String TAG = "comment";
    private final CommentApi api;

    public CommentRepository(CommentApi api) {
        this.allDiscussData = new MutableLiveData<>();
        this.api = api;
    }

    public LiveData<List<Discuss>> getAllDiscussLive(String newsId) {
        Call<Result<Discuss>> discussList = api.getDiscussList(newsId);
        discussList.enqueue(new Callback<Result<Discuss>>() {
            @Override
            public void onResponse(Call<Result<Discuss>> call, Response<Result<Discuss>> response) {
                Log.d(TAG, "onResponse: " + response.body().getData().toString());
                allDiscussData.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<Result<Discuss>> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求失败 ");
            }
        });
        return allDiscussData;
    }

    public void postComment(String newsID, String userID, String content) {
        Log.d(TAG, "postComment: " + newsID + userID + content);
        Map<String, String> map = new HashMap<>();
        map.put("newsId", newsID);
        map.put("userId", userID);
        map.put("content", content);
        Call<Result<Void>> resultCall = api.postComment(map);
        resultCall.enqueue(new Callback<Result<Void>>() {
            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Log.d(TAG, "onResponse: 请求成功" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求失败");
            }
        });
    }
}
