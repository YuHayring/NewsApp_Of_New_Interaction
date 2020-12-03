package cn.edu.gdut.douyintoutiao.view.user.follow.model;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.Follow;
import cn.edu.gdut.douyintoutiao.entity.FollowNews;
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
    private static final String followTAG = "followList";
    private static final String authorTag = "authorInfo";
    private static final String newsTag = "newsList";
    private final MutableLiveData<List< User >> authorData;
    private final MutableLiveData<List< FollowNews >>  newsData;

    public FollowRepository(FollowApi followApi) {
        this.followApi = followApi;
        this.data = new MutableLiveData<>();
        this.authorData = new MutableLiveData<>();
        this.newsData = new MutableLiveData<>();
    }



    public LiveData<List<Follow>> getFollowList(String userId) {
        Map<String, String> userIdMap = new HashMap<>();
        userIdMap.put("_id",userId );
        Call<Result<Follow>> call = (Call<Result<Follow>>) followApi.getFollowAuthorList(userIdMap);
        call.enqueue(new Callback<Result<Follow>>() {
            @Override
            public void onResponse(Call<Result<Follow>> call, Response<Result<Follow>> response) {
                data.postValue(response.body().getData());
                //检查
                System.out.println("0.response:"+response.body().getData());

                Log.d(followTAG, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<Follow>> call, Throwable t) {
                Log.d(followTAG, "onFailure: getFollowList请求失败 ");
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
                Log.d(followTAG, "deleteFollowResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<Result<Follow>> call, Throwable t) {
                Log.d(followTAG, "onFailure: deleteFollowListByFollowId请求失败 ");
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

     public LiveData<List<FollowNews>> getFollowTagsList(String userId) {
        Map<String,String> userIdMap = new HashMap<>();
        userIdMap.put("userId",userId);
        Call<Result<FollowNews>> call = followApi.getFollowTagsList(userIdMap);
        call .enqueue(new Callback< Result< FollowNews > >() {
            @Override
            public void onResponse(Call< Result< FollowNews > > call, Response< Result< FollowNews > > response) {
                newsData.postValue(response.body().getData());
                System.out.println("getFollowTagsListResponse:"+response.body().getData());
                Log.d(newsTag,"onResponse"+response.body().getCode()+""+response.body().getMsg());
            }

            @Override
            public void onFailure(Call< Result< FollowNews > > call, Throwable t) {

                Log.d(newsTag,"查询关注事件失败");
            }
        });

        return newsData;
     }


    public void deleteFollowTagsByFollowNewsId(String followNewsId){
        Map<String, String> followNewsIdMap = new HashMap<>();
        followNewsIdMap.put("_id", followNewsId);
        Call<Result<FollowNews>> call = (Call<Result<FollowNews>>) followApi.deleteFollowTagsByFollowNewsId(followNewsIdMap);
        call.enqueue(new Callback< Result< FollowNews > >() {
            @Override
            public void onResponse(Call< Result< FollowNews > > call, Response< Result< FollowNews > > response) {
                Log.d(newsTag, "deleteTagsResponse: " + response.body().getCode() + " " + response.body().getMsg());

            }

            @Override
            public void onFailure(Call< Result< FollowNews > > call, Throwable t) {
                Log.d(followTAG, "onFailure: deleteFollowTagsByFollowNewsId ");
            }
        });
    }

    public void insertUserFollowList(String followerId, String authorId){
        Map<String, String> followerIdAuthorId = new HashMap<>();
        followerIdAuthorId.put("followerId", followerId);
        followerIdAuthorId.put("authorId",authorId);
        Call<Result> call = (Call<Result>) followApi.insertUserFollowList(followerIdAuthorId);
        call.enqueue(new Callback< Result >() {
            @Override
            public void onResponse(Call< Result > call, Response< Result > response) {
                Log.d(newsTag, "insertUserFollowList: " + response.body().getCode() + " " + response.body().getMsg());

            }

            @Override
            public void onFailure(Call< Result > call, Throwable t) {
                Log.d(followTAG, "onFailure: insertUserFollowList ");

            }
        });

    }


}
