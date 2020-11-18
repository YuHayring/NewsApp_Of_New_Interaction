package cn.edu.gdut.douyintoutiao.view.user.follow.model;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.FollowApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author : DengJL
 * @description ： Fragment_follow_author_list 的 资源库
 */
public class FollowRepository {

    private final MutableLiveData<List<Follow>> data;
    private final FollowApi followApi;
    private static final String FollowTAG = "followList";
    private static final String authorTag = "authorInfo";
    private final MutableLiveData<List< User >> authorData;

    public FollowRepository(FollowApi followApi) {
        this.followApi = followApi;
        this.data = new MutableLiveData<>();
        this.authorData = new MutableLiveData<>();
    }


    public LiveData<List<Follow>> getFollowList() {
        Call<Result<Follow>> call = (Call<Result<Follow>>) followApi.getFollowList();
        call.enqueue(new Callback<Result<Follow>>() {
            @Override
            public void onResponse(Call<Result<Follow>> call, Response<Result<Follow>> response) {
                data.postValue(response.body().getData());
                //检查
                System.out.println("0.response:"+response.body().getData());

                Log.d(FollowTAG, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<Follow>> call, Throwable t) {
                Log.d(FollowTAG, "onFailure: getFollowList请求失败 ");
            }
        });

        return data;
    }

    public void deleteFollowListByFollowId(String followId){
        Map<String, String> followIdMap = new HashMap<>();
        followIdMap.put("_id", followId);
        Call<Result<Follow>> call = (Call<Result<Follow>>) followApi.deleteFollowListByFollowId(followIdMap);
        call.enqueue(new Callback<Result<Follow>>() {
            @Override
            public void onResponse(Call<Result<Follow>> call, Response<Result<Follow>> response) {
                Log.d(FollowTAG, "deleteFollowResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<Follow>> call, Throwable t) {
                Log.d(FollowTAG, "onFailure: deleteFollowListByFollowId请求失败 ");
            }
        });
    }

    public LiveData<List<User>> queryUserByUserId(String userId) {
        Map<String, String> userIdMap = new HashMap<>();
        userIdMap.put("_id",userId );
        Call<Result<User>> call = followApi.queryUserByUserId(userIdMap);
        call.enqueue(new Callback<Result<User>>() {
            @Override
            public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                authorData.postValue(response.body().getData());
                //检查
                System.out.println("response:"+response.body().getData());

                Log.d(authorTag, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<User>> call, Throwable t) {
                Log.d(authorTag, "onFailure: queryUserByUserId请求失败 ");
            }
        });

        return authorData;
    }

}
