package cn.edu.gdut.douyintoutiao.view.show.search.model;

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
public class SearchRepository {

    private static final String TAG = "news";
    private final MutableLiveData<List<MyNews>> allNewsLive;
    private final NewsApi api;

    public SearchRepository(NewsApi api) {
        this.api = api;
        allNewsLive = new MutableLiveData<>();
    }


    public LiveData<List<MyNews>> getSearchNewsList(String key) {
        Call<Result<MyNews>> call = api.searchNewsList(key);
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


}
