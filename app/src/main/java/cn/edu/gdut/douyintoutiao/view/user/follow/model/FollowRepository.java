package cn.edu.gdut.douyintoutiao.view.user.follow.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.MyNews;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.FollowApi;
import cn.edu.gdut.douyintoutiao.net.NewsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowRepository {

    private MutableLiveData<List<Follow>> followListLiveData;
    private FollowApi followApi;
    private static final String TAG = "followList";
    private List<Follow> list;

    public FollowRepository(FollowApi followApi) {
        this.followApi = followApi;
        followListLiveData = new MutableLiveData<>();
    }



    public LiveData<List<Follow>> getFollowList() {
        Call<Result<Follow>> call = (Call<Result<Follow>>) followApi.getFollowList();
        call.enqueue(new Callback<Result<Follow>>() {
            @Override
            public void onResponse(Call<Result<Follow>> call, Response<Result<Follow>> response) {
                List<Follow> list = new ArrayList<>();
                for(int i = 0; i < response.body().getData().length; i++){
                    list.add(response.body().getData()[i]);
                   // System.out.println("list:"+list);
                }

                followListLiveData.postValue(list);
                //检查
                System.out.println("livedata:"+followListLiveData.getValue());

                Log.d(TAG, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<Follow>> call, Throwable t) {

            }
        });
        return followListLiveData;
    }


}
