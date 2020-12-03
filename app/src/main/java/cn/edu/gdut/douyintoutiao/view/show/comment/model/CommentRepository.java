package cn.edu.gdut.douyintoutiao.view.show.comment.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.gdut.douyintoutiao.base.ObserverManager;
import cn.edu.gdut.douyintoutiao.entity.Discuss;
import cn.edu.gdut.douyintoutiao.entity.Result;
import cn.edu.gdut.douyintoutiao.entity.User;
import cn.edu.gdut.douyintoutiao.net.CommentApi;
import cn.edu.gdut.douyintoutiao.view.show.comment.CommentCallBack;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
    private static final String TAG = "comment";
    private final CommentApi api;

    private CommentCallBack callBack;

    public void setCallBack(CommentCallBack callBack) {
        this.callBack = callBack;
    }

    public CommentRepository(CommentApi api) {
        this.allDiscussData = new MutableLiveData<>();
        this.api = api;
    }

    public LiveData<List<Discuss>> getAllDiscussLive(String newsId) {
        Call<Result<Discuss>> discussList = api.getDiscussList(newsId);
        discussList.enqueue(new Callback<Result<Discuss>>() {
            @Override
            public void onResponse(Call<Result<Discuss>> call, Response<Result<Discuss>> response) {
                Log.d(TAG, "onResponse: " + response.body().getData().toString());
                allDiscussData.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<Result<Discuss>> call, Throwable t) {
                Log.d(TAG, "onFailure: 请求失败 ");
            }
        });
        return allDiscussData;
    }

    public void postComment(String newsID, String userID, String content) {
        Log.d(TAG, "postComment: " + newsID + userID + content);
        Map<String, String> map = new HashMap<>();
        map.put("newsId", newsID);
        map.put("userId", userID);
        map.put("content", content);
        Observable<Result<Void>> resultCall = api.postComment(map);
        resultCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverManager<Result<Void>>() {
                    @Override
                    public void onSuccess(Result<Void> userResult) {
                        Log.d(TAG, "onSuccess: " + userResult.toString());
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                    }

                    @Override
                    public void onFinish() {
                        Log.d(TAG, "onFinish: 请求完成");
                        callBack.updateComment();
                    }

                    @Override
                    public void onDisposable(Disposable disposable) {

                    }
                });
    }


}
