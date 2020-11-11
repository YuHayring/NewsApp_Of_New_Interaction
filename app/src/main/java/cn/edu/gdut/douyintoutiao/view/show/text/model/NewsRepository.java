package cn.edu.gdut.douyintoutiao.view.show.text.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.News;
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

    private MutableLiveData<List<MyNews>> allNewsLive;
    private NewsApi api;
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
                List<MyNews> list = new ArrayList<>();
                for(int i = 0; i < response.body().getData().length; i++){
                    list.add(response.body().getData()[i]);
                }
                allNewsLive.postValue(list);
                Log.d(TAG, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<MyNews>> call, Throwable t) {

            }
        });
        return allNewsLive;
    }
}
