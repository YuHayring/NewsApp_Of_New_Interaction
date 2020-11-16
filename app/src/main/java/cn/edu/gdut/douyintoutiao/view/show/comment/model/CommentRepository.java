package cn.edu.gdut.douyintoutiao.view.show.comment.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

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

    private final CommentApi api;

    public CommentRepository(CommentApi api) {
        this.allDiscussData = new MutableLiveData<>();
        this.api = api;
    }

    public LiveData<List<Discuss>> getAllDiscussLive() {
        Call<Result<Discuss>> discussList = api.getDiscussList();
        discussList.enqueue(new Callback<Result<Discuss>>() {
            @Override
            public void onResponse(Call<Result<Discuss>> call, Response<Result<Discuss>> response) {
                List<Discuss> list = new ArrayList<>();
                for (int i = 0; i < response.body().getData().length; i++) {
                    list.add(response.body().getData()[i]);
                }
                allDiscussData.postValue(list);
            }

            @Override
            public void onFailure(Call<Result<Discuss>> call, Throwable t) {

            }
        });
        return allDiscussData;
    }
}
