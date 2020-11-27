package cn.edu.gdut.douyintoutiao.view.show.text.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.entity.FollowNews;
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
public class NewsRepository {

    private static volatile NewsRepository instance;
    private final MutableLiveData<List<MyNews>> allNewsLive;
    private final NewsApi api;
    private static final String TAG = "news";
    private static final String FollowTag = "follow";
    private final MutableLiveData<List<Boolean>> checkFollowFlag;

    public NewsRepository(NewsApi api) {
        this.api = api;
        allNewsLive = new MutableLiveData<>();
        checkFollowFlag = new MutableLiveData<>();
    }


    public LiveData<List<MyNews>> getAllNewsLive() {
        Call<Result<MyNews>> call = api.getNewsList();
        call.enqueue(new Callback<Result<MyNews>>() {
            @Override
            public void onResponse(Call<Result<MyNews>> call, Response<Result<MyNews>> response) {
                allNewsLive.postValue(response.body().getData());
                Log.d(TAG, "onResponse: " + response.body().getData().toString());
            }

            @Override
            public void onFailure(Call<Result<MyNews>> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求失败");
            }
        });
        return allNewsLive;
    }


    /**
     *
     * @DengJl
     */
    public void insertTagsFollowByNewsIdUserId(String newsId,String userId){
        Map<String, String> newsIdUserId = new HashMap<>();
        newsIdUserId.put("newsId", newsId);
        newsIdUserId.put("userId",userId);
        Call< Result< FollowNews > > call = api.insertTagsFollowByNewsIdUserId(newsIdUserId);
        call.enqueue(new Callback< Result< FollowNews > >() {
            @Override
            public void onResponse(Call< Result< FollowNews > > call, Response< Result< FollowNews > > response) {
               Log.d(FollowTag, "onResponse: " + response.body().getCode() + " " + response.body().getMsg());
            }

            @Override
            public void onFailure(Call< Result< FollowNews > > call, Throwable t) {
                Log.d(FollowTag, "onFailure: insertTagsFollowByNewsIdUserId失败 ");
            }
        });
    }

    public void deleteTagsFollowByNewsIdUserId(String newsId , String userId){
        Map<String, String> newsIdUserId = new HashMap<>();
        newsIdUserId.put("newsId", newsId);
        newsIdUserId.put("userId",userId);
        Call<Result> call = api.deleteTagsFollowByNewsIdUserId(newsIdUserId);
        call.enqueue(new Callback< Result >() {
            @Override
            public void onResponse(Call< Result > call, Response< Result > response) {
                Log.d(FollowTag, "取消关注Response: " + response.body().getCode() + " " + response.body().getMsg());

            }

            @Override
            public void onFailure(Call< Result > call, Throwable t) {
                Log.d(FollowTag, "onFailure: 取消关注失败 ");

            }
        });
    }


    public LiveData<List<Boolean>> checkTagsFollowByNewsIdUserId (String newsId , String userId) {
        Map< String, String > newsIdUserId = new HashMap<>();
        newsIdUserId.put("newsId", newsId);
        newsIdUserId.put("userId", userId);

        Call< Result<Boolean> > call = api.checkTagsFollowByNewsIdUserId(newsIdUserId);
        call.enqueue(new Callback< Result<Boolean>>() {
            @Override
            public void onResponse(Call< Result<Boolean>> call, Response< Result<Boolean> > response) {
                Log.d(FollowTag, "检查Response:" + response.body().getCode() + " " + response.body().getMsg()+response.body().getData());
                checkFollowFlag.postValue(response.body().getData());
            }
            @Override
            public void onFailure(Call< Result<Boolean> > call, Throwable t) {
             //   latch.countDown();
                System.out.println("检查失败");
            }
        });
        return checkFollowFlag;
    }

//    public Observable<Result<Boolean>> checkTagsFollowByNewsIdUserId(String newsId, String userId){
//        Map<String, String> newsIdUserId = new HashMap<>();
//        newsIdUserId.put("newsId", newsId);
//        newsIdUserId.put("userId",userId);
//       return api.checkTagsFollowByNewsIdUserId(newsIdUserId);
//    }


//    public static NewsRepository getInstance(){
//        if(instance == null){
//            synchronized (LoginUserModel.class) {
//                if(instance == null) {
//                    instance = new NewsRepository(NewsApi.getNewsApi());
//                }
//            }
//        }
//        return instance;
//    }




}
