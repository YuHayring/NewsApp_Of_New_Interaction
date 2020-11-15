package cn.edu.gdut.douyintoutiao.view.user.follow.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.net.FollowApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowRepository {

    private MutableLiveData<List<Follow>> data;
    private FollowApi followApi;
    private static final String TAG = "followList";

    public FollowRepository(FollowApi followApi) {
        this.followApi = followApi;
        data = new MutableLiveData<>();


    }


    public LiveData<List<Follow>> getFollowList() {
        Call<Result<Follow>> call = (Call<Result<Follow>>) followApi.getFollowList();
        call.enqueue(new Callback<Result<Follow>>() {
            @Override
            public void onResponse(Call<Result<Follow>> call, Response<Result<Follow>> response) {

                data.postValue(response.body().getData());
                 System.out.println("1.data:"+data);
                Log.d(TAG, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<Follow>> call, Throwable t) {

            }
        });

        System.out.println("3. getData:"+data);
        return data;
    }

}
