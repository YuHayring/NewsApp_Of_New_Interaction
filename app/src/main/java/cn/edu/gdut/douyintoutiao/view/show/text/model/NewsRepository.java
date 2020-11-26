package cn.edu.gdut.douyintoutiao.view.show.text.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.io.IOException;
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

    public NewsRepository(NewsApi api) {
        this.api = api;
        allNewsLive = new MutableLiveData<>();
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

    //@author : hudp
    //点赞
    public MutableLiveData<Result<MyNews>> newsLike(String newsID) {
        Call<Result<MyNews>> newslike = api.likeNews(newsID);
        MutableLiveData<Result<MyNews>> mutableLiveData = new MutableLiveData<Result<MyNews>>();
        Response<Result<MyNews>> response = null;
        try {
            response = newslike.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutableLiveData.setValue(response.body());
        return mutableLiveData;
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


//    public boolean checkTagsFollowByNewsIdUserId (String newsId , String userId) {
//        Map< String, String > newsIdUserId = new HashMap<>();
//        newsIdUserId.put("newsId", newsId);
//        newsIdUserId.put("userId", userId);
//
//        final CountDownLatch latch = new CountDownLatch(1);
//        final Boolean[] followFlag = new Boolean[1];
//        followFlag[0]=false;
//      //  Boolean followFlag = false;
//        Call< Result > call = api.checkTagsFollowByNewsIdUserId(newsIdUserId);
//        call.enqueue(new Callback< Result>() {
//            @Override
//            public void onResponse(Call< Result> call, Response< Result > response) {
//                Log.d(FollowTag, "检查Response:" + response.body().getCode() + " " + response.body().getMsg()+response.body().getData());
////          //     latch.countDown();
////                if(response.body().getCode()=="200"){
////                        followFlag[0] = true;
////                        }
//            }
//            @Override
//            public void onFailure(Call< Result > call, Throwable t) {
//             //   latch.countDown();
//                System.out.println("检查失败");
//            }
//        });

//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("检查关注："+followFlag);
//
//        return followFlag[0];
//    }
//
//    public Observable<Result> checkTagsFollowByNewsIdUserId1(String newsId, String userId){
//        Map<String, String> newsIdUserId = new HashMap<>();
//        newsIdUserId.put("newsId", newsId);
//        newsIdUserId.put("userId",userId);
//       return api.checkTagsFollowByNewsIdUserId1(newsIdUserId);
//
//    }




}
