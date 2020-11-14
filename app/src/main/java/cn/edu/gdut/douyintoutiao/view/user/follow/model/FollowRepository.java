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

    private MutableLiveData<List<Follow>> followListData;
    private List<Follow> data;
    private FollowApi followApi;
    private static final String TAG = "followList";

    public FollowRepository(FollowApi followApi) {
        this.followApi = followApi;
        followListData = new MutableLiveData<>();
        data = new ArrayList<Follow>();

    }


    public LiveData<List<Follow>> getFollowList() {
        Call<Result<Follow>> call = (Call<Result<Follow>>) followApi.getFollowList();
        call.enqueue(new Callback<Result<Follow>>() {
            @Override
            public void onResponse(Call<Result<Follow>> call, Response<Result<Follow>> response) {
                List<Follow> list = new ArrayList<>();
                for(int i = 0; i < response.body().getData().length; i++){
                    data.add(response.body().getData()[i]);
                    System.out.println("list:"+data);
                    System.out.println("list:"+data);
                }

                followListData.postValue(list);
                //检查
                System.out.println("data:"+followListData.toString());

                Log.d(TAG, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<Follow>> call, Throwable t) {

            }
        });

        System.out.println("getData:"+followListData);
        return followListData;
    }

    public List< Follow > getData() {
        Call<Result<Follow>> call = (Call<Result<Follow>>) followApi.getFollowList();
        call.enqueue(new Callback<Result<Follow>>() {

            @Override
            public void onResponse(Call<Result<Follow>> call, Response<Result<Follow>> response) {
                for(int i = 0; i < response.body().getData().length; i++){
                    data.add(response.body().getData()[i]);
                    System.out.println("data:"+data);
                }
                Log.d(TAG, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<Follow>> call, Throwable t) {
                System.out.println("失败");
            }

        });
        System.out.println("getData:"+data);

        return data;
    }
}
