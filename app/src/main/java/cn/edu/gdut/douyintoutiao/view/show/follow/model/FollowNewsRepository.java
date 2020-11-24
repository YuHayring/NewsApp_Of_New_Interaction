package cn.edu.gdut.douyintoutiao.view.show.follow.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

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
public class FollowNewsRepository {
    private static final String TAG = "news";
    private final MutableLiveData<List<MyNews>> allFollowNewsLive;
    private final NewsApi api;

    public FollowNewsRepository(NewsApi api) {
        this.api = api;
        allFollowNewsLive = new MutableLiveData<>();
    }

    public LiveData<List<MyNews>> getAllFollowLive(String tag) {
        Call<Result<MyNews>> call = api.getFollowNewsList(tag);
        call.enqueue(new Callback<Result<MyNews>>() {
            @Override
            public void onResponse(Call<Result<MyNews>> call, Response<Result<MyNews>> response) {
                allFollowNewsLive.postValue(response.body().getData());
                Log.d(TAG, "onResponse: " + response.body().getData().toString());
            }

            @Override
            public void onFailure(Call<Result<MyNews>> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求失败");
            }
        });
        return allFollowNewsLive;
    }


}
